package com.joel.recipe_app.recipe_app;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.joel.recipe_app.recipe_app.domain.Category;
import com.joel.recipe_app.recipe_app.domain.CategoryRepository;
import com.joel.recipe_app.recipe_app.domain.Ingredient;
import com.joel.recipe_app.recipe_app.domain.IngredientRepository;
import com.joel.recipe_app.recipe_app.domain.Recipe;
import com.joel.recipe_app.recipe_app.domain.RecipeIngredient;
import com.joel.recipe_app.recipe_app.domain.RecipeRepository;
import com.joel.recipe_app.recipe_app.domain.User;
import com.joel.recipe_app.recipe_app.domain.UserRepository;

@SpringBootApplication
public class RecipeAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecipeAppApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(RecipeRepository recipeRepository, IngredientRepository ingredientRepository,
			CategoryRepository categoryRepository, UserRepository userRepository) {
		return (args) -> {
			Category category1 = new Category("Jälkiruoka");
			Category category2 = new Category("Aamupala");
			Category category3 = new Category("Texmex");
			Category category4 = new Category("Salaatti");
			Category category5 = new Category("Leivos");
			Category category6 = new Category("Pääruoka");
			Category category7 = new Category("Välipala");
			Category category8 = new Category("Keitto");
			Category category9 = new Category("Grilli");
			Category category10 = new Category("Kasvisruoka");
			Category category11 = new Category("Sushi");
			Category category12 = new Category("Pasta");

			categoryRepository.save(category1);
			categoryRepository.save(category2);
			categoryRepository.save(category3);
			categoryRepository.save(category4);
			categoryRepository.save(category5);
			categoryRepository.save(category6);
			categoryRepository.save(category7);
			categoryRepository.save(category8);
			categoryRepository.save(category9);
			categoryRepository.save(category10);
			categoryRepository.save(category11);
			categoryRepository.save(category12);

			Ingredient ingredient1 = new Ingredient("Vehnäjauho");
			Ingredient ingredient2 = new Ingredient("Kananmuna");
			Ingredient ingredient3 = new Ingredient("Suola");
			Ingredient ingredient4 = new Ingredient("Mustapippuri");
			Ingredient ingredient5 = new Ingredient("Rypsiöljy");
			Ingredient ingredient6 = new Ingredient("Vesi");
			Ingredient ingredient7 = new Ingredient("Valkosipulinkynsi");
			Ingredient ingredient8 = new Ingredient("Maito");
			Ingredient ingredient9 = new Ingredient("Voi");

			ingredientRepository.save(ingredient1);
			ingredientRepository.save(ingredient2);
			ingredientRepository.save(ingredient3);
			ingredientRepository.save(ingredient4);
			ingredientRepository.save(ingredient5);
			ingredientRepository.save(ingredient6);
			ingredientRepository.save(ingredient7);
			ingredientRepository.save(ingredient8);
			ingredientRepository.save(ingredient9);

			Recipe recipe1 = new Recipe("Kinkkupiirakka", "Joel",
					"Maistuva ja helppo suolainen piirakka. Tämä resepti on kulkeutunut suvussani jo vuosikymmeniä.",
					"1. Sekoita rasva jauhojen ja suolan joukkoon. Lisää vesi ja sekoita tasaiseksi taikinaksi. \n2. Voitele piirakkavuoka ja painele taikina tasaisesti vuuan pohjalle.\n3. Esipaista pohjaa 200 asteessa uunin alatasolla noin 10 minuuttia.\n4. Kuutioi kinkku pieniksi kuutioiksi. Leikkaa halkaistu ja huuhdottu purjo ohuiksi suikaleiksi. Kuutioi paprika ja hienonna valkosipulinkynnet. Levitä kinkku ja kasvikset tasaisesti puolikypsälle pohjalle. \n5. Vatkaa kananmunien rakenne rikki ja sekoita joukkoon ranskankerma ja maito. Lisää seokseen juustoraaste ja mausteet.\n6. Kaada seos tasaisesti vuokaan ja kypsennä kinkkupiirakkaa vielä 200 asteessa noin 30 minuuttia. ",
					category5);
			Recipe recipe2 = new Recipe("Paistettu muna", "Mikko", "Keksin tän ite mut muksut ainakin tykkäs",
					"1. Ihan peruskauraa eli pistä pannu keskikuumaksi \n2. Kun pannu on sopivan lämmin niin voi sinne sulamaan. \n3. Sit rikot sinne pari munaa ja maustat suolalla ja pippurilla. \n4. Anna paistua noin 2 min per puoli",
					category2);

			RecipeIngredient recipeIngredient1 = new RecipeIngredient("100g");
			recipeIngredient1.setRecipe(recipe1);
			recipeIngredient1.setIngredient(ingredient9);

			RecipeIngredient recipeIngredient2 = new RecipeIngredient("1 1/2 dl");
			recipeIngredient2.setRecipe(recipe1);
			recipeIngredient2.setIngredient(ingredient1);

			RecipeIngredient recipeIngredient3 = new RecipeIngredient("1/2 tl");
			recipeIngredient3.setRecipe(recipe1);
			recipeIngredient3.setIngredient(ingredient3);

			RecipeIngredient recipeIngredient4 = new RecipeIngredient("25g");
			recipeIngredient4.setRecipe(recipe2);
			recipeIngredient4.setIngredient(ingredient9);

			RecipeIngredient recipeIngredient5 = new RecipeIngredient("3 kpl");
			recipeIngredient5.setRecipe(recipe2);
			recipeIngredient5.setIngredient(ingredient2);

			RecipeIngredient recipeIngredient6 = new RecipeIngredient("1/2 tl");
			recipeIngredient6.setRecipe(recipe2);
			recipeIngredient6.setIngredient(ingredient3);

			RecipeIngredient recipeIngredient7 = new RecipeIngredient("1/2 tl");
			recipeIngredient7.setRecipe(recipe2);
			recipeIngredient7.setIngredient(ingredient4);

			List<RecipeIngredient> listOfIngredients1 = new ArrayList<>();
			listOfIngredients1.add(recipeIngredient1);
			listOfIngredients1.add(recipeIngredient2);
			listOfIngredients1.add(recipeIngredient3);

			List<RecipeIngredient> listOfIngredients2 = new ArrayList<>();
			listOfIngredients2.add(recipeIngredient4);
			listOfIngredients2.add(recipeIngredient5);
			listOfIngredients2.add(recipeIngredient6);
			listOfIngredients2.add(recipeIngredient7);

			recipe1.setIngredients(listOfIngredients1);
			recipe2.setIngredients(listOfIngredients2);

			recipeRepository.save(recipe1);
			recipeRepository.save(recipe2);

			User user1 = new User("user", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6", "USER");
			User user2 = new User("admin", "$2a$10$0MMwY.IQqpsVc1jC8u7IJ.2rT8b0Cd3b3sfIBGV2zfgnPGtT4r0.C", "ADMIN");
			userRepository.save(user1);
			userRepository.save(user2);
		};
	}

}
