package com.joel.recipe_app.recipe_app.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, Long> {
    Ingredient findByNameIgnoreCase(String name);

    List<Ingredient> findByNameContainingIgnoreCase(String query);

    Ingredient findByName(String name);
}
