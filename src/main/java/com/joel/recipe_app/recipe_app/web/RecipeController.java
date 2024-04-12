package com.joel.recipe_app.recipe_app.web;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.joel.recipe_app.recipe_app.domain.CategoryRepository;
import com.joel.recipe_app.recipe_app.domain.Ingredient;
import com.joel.recipe_app.recipe_app.domain.IngredientRepository;
import com.joel.recipe_app.recipe_app.domain.Recipe;
import com.joel.recipe_app.recipe_app.domain.RecipeForm;
import com.joel.recipe_app.recipe_app.domain.RecipeIngredient;
import com.joel.recipe_app.recipe_app.domain.RecipeRepository;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RecipeController {
    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    IngredientRepository ingredientRepository;

    @GetMapping(value = { "*", "/recipelist" })
    public String getRecipeList(Model model) {
        List<Recipe> recipeList = (List<Recipe>) recipeRepository.findAll();
        model.addAttribute("recipes", recipeList);
        return "recipelist";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/recipelist/{id}")
    public String getRecipeById(@PathVariable("id") Long id, Model model) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);
        if (optionalRecipe.isPresent()) {
            Recipe recipe = optionalRecipe.get();
            model.addAttribute("recipe", recipe);
            model.addAttribute("instructions", recipe.getInstructions().split("\n"));
            return "recipepage";
        } else
            return "redirect:/recipelist"; // TODO: Korjaa tämä
    }

    @GetMapping("/search")
    public String getRecipesBySearch(@RequestParam("query") String query, Model model) {
        List<Recipe> searchResults = recipeRepository
                .findByNameContainingIgnoreCaseOrCategoryNameContainingIgnoreCase(query, query);
        List<Ingredient> ingredients = ingredientRepository.findByNameContainingIgnoreCase(query);
        for (Ingredient ingredient : ingredients) {
            for (RecipeIngredient recipeIngredient : ingredient.getRecipes()) {
                Recipe recipe = recipeIngredient.getRecipe();
                if (!searchResults.contains(recipe)) {
                    searchResults.add(recipe);
                }
            }
        }
        Set<Recipe> uniqueRecipes = new LinkedHashSet<>(searchResults);
        searchResults.clear();
        searchResults.addAll(uniqueRecipes);
        model.addAttribute("recipes", searchResults);

        return "recipelist";
    }

    @GetMapping("/addrecipe")
    public String getAddRecipeForm(Model model) {
        model.addAttribute("recipe", new Recipe());
        model.addAttribute("categories", categoryRepository.findAll());
        return "addrecipeform";
    }

    @PostMapping("/saverecipe")
    public String postSaveRecipe(@Valid @ModelAttribute RecipeForm recipeForm) {
        Recipe newRecipe = new Recipe();
        newRecipe.setName(recipeForm.getName());
        newRecipe.setDescription(recipeForm.getDescription());
        newRecipe.setCreator(recipeForm.getCreator());
        newRecipe.setCategory(categoryRepository.findByName(recipeForm.getCategory()));
        newRecipe.setInstructions(recipeForm.getInstructions());

        List<RecipeIngredient> recipeIngredients = new ArrayList<>();
        String[] listOfIngredientStrings = recipeForm.getIngredients().split(",");
        String[] listOfIngredientAmountStrings = recipeForm.getAmountOfIngredients().split(",");
        for (int i = 0; i < listOfIngredientStrings.length; i++) {
            String ingredientName = listOfIngredientStrings[i];
            String ingredientAmount = listOfIngredientAmountStrings[i];
            Ingredient ingredient = ingredientRepository.findByNameIgnoreCase(ingredientName);
            if (ingredient == null) {
                ingredient = new Ingredient(ingredientName);
                ingredientRepository.save(ingredient);
            }

            RecipeIngredient recipeIngredient = new RecipeIngredient(ingredientAmount);
            recipeIngredient.setIngredient(ingredient);
            recipeIngredient.setRecipe(newRecipe);
            recipeIngredients.add(recipeIngredient);
        }
        newRecipe.setIngredients(recipeIngredients);
        recipeRepository.save(newRecipe);
        return "redirect:/recipelist";
    }

    @GetMapping("/deleterecipe/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or @recipeRepository.findById(#id).orElse(null)?.creator == authentication.principal.username")
    public String getDeleteRecipe(@PathVariable Long id) {
        recipeRepository.deleteById(id);
        return "redirect:/recipelist";
    }

    @PreAuthorize("hasAuthority('ADMIN') or @recipeRepository.findById(#id).orElse(null)?.creator == authentication.principal.username")
    @GetMapping("/editrecipe/{id}")
    public String getEditRecipe(@PathVariable Long id, Model model) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);
        if (recipeOptional.isPresent()) {
            model.addAttribute("recipe", recipeOptional.get());
            model.addAttribute("categories", categoryRepository.findAll());
            return "editrecipeform";
        } else {
            throw new Error("Recipe not found"); // TODO: KORJAAA
        }
    }

    @PostMapping("/saverecipeedit")
    public String postSaveRecipeEdit(@Valid @ModelAttribute RecipeForm recipeForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "editrecipeform";
        }
        Long recipeFormId = recipeForm.getId();
        if (recipeFormId != null) {
            Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeForm.getId());
            if (!optionalRecipe.isPresent()) {
                return "redirect:/recipelist";
            }

            Recipe existingRecipe = optionalRecipe.get();
            existingRecipe.setName(recipeForm.getName());
            existingRecipe.setDescription(recipeForm.getDescription());
            existingRecipe.setCreator(recipeForm.getCreator());
            existingRecipe.setCategory(categoryRepository.findByName(recipeForm.getCategory()));
            existingRecipe.setInstructions(recipeForm.getInstructions());

            List<RecipeIngredient> recipeIngredients = new ArrayList<>();
            String recipeFormIngredients = recipeForm.getIngredients();
            String recipeFormIngredientAmount = recipeForm.getAmountOfIngredients();
            if (recipeFormIngredients != null || recipeFormIngredientAmount != null) {
                String[] listOfIngredientStrings = recipeFormIngredients.split(",");
                String[] listOfIngredientAmountStrings = recipeFormIngredientAmount.split(",");
                for (int i = 0; i < listOfIngredientStrings.length; i++) {
                    String ingredientName = listOfIngredientStrings[i];
                    String ingredientAmount = listOfIngredientAmountStrings[i];
                    Ingredient ingredient = ingredientRepository.findByNameIgnoreCase(ingredientName);
                    if (ingredient == null) {
                        ingredient = new Ingredient(ingredientName);
                        ingredientRepository.save(ingredient);
                    }

                    RecipeIngredient recipeIngredient = new RecipeIngredient(ingredientAmount);
                    recipeIngredient.setIngredient(ingredient);
                    recipeIngredient.setRecipe(existingRecipe);
                    recipeIngredients.add(recipeIngredient);
                }
            }
            existingRecipe.setIngredients(recipeIngredients);
            recipeRepository.save(existingRecipe);
        }
        return "redirect:/recipelist/" + recipeFormId;
    }
}
