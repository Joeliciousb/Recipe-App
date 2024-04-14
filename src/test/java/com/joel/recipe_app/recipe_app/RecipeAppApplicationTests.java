package com.joel.recipe_app.recipe_app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

import com.joel.recipe_app.recipe_app.web.CategoryController;
import com.joel.recipe_app.recipe_app.web.IngredientController;
import com.joel.recipe_app.recipe_app.web.RecipeController;
import com.joel.recipe_app.recipe_app.web.RecipeControllerRest;
import com.joel.recipe_app.recipe_app.web.UserController;

@SpringBootTest
class RecipeAppApplicationTests {

	@Autowired
	private RecipeController recipeController;

	@Autowired
	private CategoryController categoryController;

	@Autowired
	private IngredientController ingredientController;

	@Autowired
	private UserController userController;

	@Autowired
	private RecipeControllerRest recipeControllerRest;

	@Test
	void contextLoads() {
		assertThat(recipeController).isNotNull();
		assertThat(categoryController).isNotNull();
		assertThat(ingredientController).isNotNull();
		assertThat(userController).isNotNull();
		assertThat(recipeControllerRest).isNotNull();
	}

}
