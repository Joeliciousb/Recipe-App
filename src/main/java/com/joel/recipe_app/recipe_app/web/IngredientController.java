package com.joel.recipe_app.recipe_app.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.joel.recipe_app.recipe_app.domain.Ingredient;
import com.joel.recipe_app.recipe_app.domain.IngredientRepository;

@Controller
public class IngredientController {

    @Autowired
    private IngredientRepository ingredientRepository;

    @GetMapping("/ingredientlist")
    public String getIngredientList(Model model) {
        List<Ingredient> listOfIngredients = (List<Ingredient>) ingredientRepository.findAll();
        model.addAttribute("ingredients", listOfIngredients);
        return "ingredientlist";
    }

    @GetMapping("/ingredient/{id}")
    public String getMethodName(@PathVariable("id") Long id, Model model) {
        Optional<Ingredient> optionalIngredient = ingredientRepository.findById(id);
        if (!optionalIngredient.isPresent()) {
            throw new Error("Ingredient not found");
        }
        Ingredient ingredient = optionalIngredient.get();
        model.addAttribute("ingredients", ingredient);
        return "ingredientlist";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/addingredient")
    public String getAddCategoryForm(Model model) {
        model.addAttribute("ingredient", new Ingredient());
        return "addingredientform";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/saveingredient")
    public String postIngredient(Ingredient ingredient) {
        ingredientRepository.save(ingredient);
        return "redirect:/ingredientlist";
    }

    @GetMapping("/deleteingredient/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getDeleteIngredient(@PathVariable Long id) {
        ingredientRepository.deleteById(id);
        return "redirect:/ingredientlist";
    }
}
