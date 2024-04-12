package com.joel.recipe_app.recipe_app.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.joel.recipe_app.recipe_app.domain.Category;
import com.joel.recipe_app.recipe_app.domain.CategoryRepository;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/categorylist")
    public String getListOfCategories(Model model) {
        List<Category> listOfCategories = (List<Category>) categoryRepository.findAll();
        model.addAttribute("categories", listOfCategories);
        return "categorylist";
    };

    @GetMapping("/category/{id}")
    public String getMethodName(@PathVariable("id") Long id, Model model) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (!optionalCategory.isPresent()) {
            throw new Error("Category not found");
        }
        Category category = optionalCategory.get();
        model.addAttribute("categories", category);
        return "categorylist";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/addcategory")
    public String getAddCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "addcategoryform";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/savecategory")
    public String postCategory(Category category) {
        categoryRepository.save(category);
        return "redirect:/categorylist";
    }

    @GetMapping("/deletecategory/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getDeleteCategory(@PathVariable Long id) {
        categoryRepository.deleteById(id);
        return "redirect:/categorylist";
    }

    @GetMapping("/editcategory/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getEditCategory(@PathVariable Long id, Model model) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (!optionalCategory.isPresent()) {
            throw new Error("Category not found");
        }
        Category category = optionalCategory.get();
        model.addAttribute("category", category);
        return "editcategoryform";
    }

}
