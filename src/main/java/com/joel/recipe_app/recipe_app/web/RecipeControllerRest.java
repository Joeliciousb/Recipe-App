package com.joel.recipe_app.recipe_app.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.joel.recipe_app.recipe_app.domain.Recipe;
import com.joel.recipe_app.recipe_app.domain.RecipeRepository;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
@CrossOrigin
public class RecipeControllerRest {
    @Autowired
    private RecipeRepository recipeRepository;

    @GetMapping("/recipes")
    public @ResponseBody List<Recipe> getRecipes() {
        return (List<Recipe>) recipeRepository.findAll();
    }

    @GetMapping("/recipe/{id}")
    public @ResponseBody Optional<Recipe> getRecipeById(@PathVariable("id") Long id) {
        return recipeRepository.findById(id);
    }

    @PostMapping("/recipes")
    public @ResponseBody Recipe postRecipe(@Valid @RequestBody Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    @PutMapping("recipe/{id}")
    public @ResponseBody Recipe putRecipe(@PathVariable("id") Long id, @RequestBody Recipe recipe) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);
        if (!optionalRecipe.isPresent()) {
            throw new Error("Recipe not found");
        }
        Recipe existingRecipe = optionalRecipe.get();
        existingRecipe.setName(recipe.getName());
        existingRecipe.setDescription(recipe.getDescription());
        existingRecipe.setCategory(recipe.getCategory());
        existingRecipe.setCreator(recipe.getCreator());
        existingRecipe.setIngredients(recipe.getIngredients());
        existingRecipe.setInstructions(recipe.getInstructions());
        Recipe updatedRecipe = recipeRepository.save(existingRecipe);
        return updatedRecipe;
    }

}
