package com.joel.recipe_app.recipe_app.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
    List<Recipe> findByName(String name);

    List<Recipe> findByNameContainingIgnoreCaseOrCategoryNameContainingIgnoreCase(String query, String query2);

}
