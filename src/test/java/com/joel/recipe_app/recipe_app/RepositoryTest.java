package com.joel.recipe_app.recipe_app;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.joel.recipe_app.recipe_app.domain.Category;
import com.joel.recipe_app.recipe_app.domain.CategoryRepository;
import com.joel.recipe_app.recipe_app.domain.Ingredient;
import com.joel.recipe_app.recipe_app.domain.IngredientRepository;
import com.joel.recipe_app.recipe_app.domain.Recipe;
import com.joel.recipe_app.recipe_app.domain.RecipeRepository;
import com.joel.recipe_app.recipe_app.domain.User;
import com.joel.recipe_app.recipe_app.domain.UserRepository;

@DataJpaTest
public class RepositoryTest {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    IngredientRepository ingredientRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void addRecipeToDatabase() {
        Category category = categoryRepository.findByName("Texmex");
        Recipe recipe = new Recipe("test recipe", "tester", "recipetest", "", category);
        recipeRepository.save(recipe);
        assertThat(recipe.getId()).isNotNull();
    }

    @Test
    public void findRecipeById_ShouldReturnRecipe() {
        Optional<Recipe> recipe = recipeRepository.findById((long) 1);
        assertThat(recipe).isNotEmpty();
    };

    @Test
    public void findRecipeByName_ShouldReturnListOfRecipes() {
        String name = "Piirakka";
        List<Recipe> listOfRecipes = recipeRepository
                .findByNameContainingIgnoreCaseOrCategoryNameContainingIgnoreCase(name, name);
        assertThat(listOfRecipes).isNotNull();

        Recipe recipeAtFirstIndex = listOfRecipes.get(0);
        assertThat(recipeAtFirstIndex.getName()).containsIgnoringCase(name);
    }

    @Test
    public void deleteRecipeFromDatabase() {
        Category category = categoryRepository.findByName("Texmex");
        Recipe recipe = new Recipe("test recipe", "tester", "recipetest", "", category);
        recipeRepository.save(recipe);
        Long recipeId = recipe.getId();

        recipeRepository.deleteById(recipeId);
        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);
        assertThat(optionalRecipe).isEmpty();
    }

    @Test
    public void addCategoryToDatabase() {
        Category category = new Category("new category");
        categoryRepository.save(category);
        assertThat(category.getCategoryId()).isNotNull();
    }

    @Test
    public void findCategoryById_ShouldReturnCategory() {
        Optional<Category> optionalCategory = categoryRepository.findById((long) 1);
        assertThat(optionalCategory).isNotEmpty();
    }

    @Test
    public void findCategoryByName_ShouldReturnCategory() {
        String name = "Texmex";
        Category category = categoryRepository.findByName(name);
        assertThat(category.getName()).containsIgnoringCase(name);
    }

    @Test
    public void deleteCategoryById() {
        Category category = new Category("test");
        categoryRepository.save(category);
        Long id = category.getCategoryId();

        categoryRepository.deleteById(id);
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        assertThat(optionalCategory).isEmpty();
    }

    @Test
    public void addIngredientToDatabase() {
        Ingredient ingredient = new Ingredient("New Ingredient");
        ingredientRepository.save(ingredient);
        assertThat(ingredient.getId()).isNotNull();
    }

    @Test
    public void findIngredientById_ShouldReturnIngredient() {
        Optional<Ingredient> optionalIngredient = ingredientRepository.findById((long) 1);
        assertThat(optionalIngredient).isNotEmpty();
    }

    @Test
    public void findIngredientByName_ShouldReturnIngredient() {
        String name = "Kananmuna";
        Ingredient ingredient = ingredientRepository.findByName(name);
        assertThat(ingredient.getName()).containsIgnoringCase(name);
    }

    @Test
    public void deleteIngredientById() {
        Ingredient ingredient = new Ingredient("Test Ingredient");
        ingredientRepository.save(ingredient);
        Long id = ingredient.getId();

        ingredientRepository.deleteById(id);
        Optional<Ingredient> optionalIngredient = ingredientRepository.findById(id);
        assertThat(optionalIngredient).isEmpty();
    }

    @Test
    public void addUserToDatabase() {
        User user = new User("testUser", "hash", "user");
        userRepository.save(user);
        assertThat(user.getId()).isNotNull();
    }

    @Test
    public void findUserByUserId_ShouldReturnUser() {
        Optional<User> user = userRepository.findById((long) 1);
        assertThat(user).isNotEmpty();
    }

    @Test
    public void findUserByUsername_ShouldReturnUser() {
        String username = "user";
        User user = userRepository.findByUsername(username);
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo(username);
    }

    @Test
    public void deleteUserFromDatabase() {
        User user = new User("testUser", "hash", "user");
        userRepository.save(user);
        Long userId = user.getId();
        userRepository.deleteById(userId);

        Optional<User> deletedUser = userRepository.findById(userId);
        assertThat(deletedUser).isEmpty();
    }
}
