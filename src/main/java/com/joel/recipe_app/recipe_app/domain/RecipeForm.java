package com.joel.recipe_app.recipe_app.domain;

import jakarta.validation.constraints.NotEmpty;

public class RecipeForm {
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String creator;
    @NotEmpty
    private String description;
    @NotEmpty
    private String ingredients;
    @NotEmpty
    private String amountOfIngredients;

    @NotEmpty
    private String category;

    @NotEmpty
    private String instructions;

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getCategory() {
        return category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmountOfIngredients() {
        return amountOfIngredients;
    }

    public void setAmountOfIngredients(String amountOfIngredients) {
        this.amountOfIngredients = amountOfIngredients;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

}
