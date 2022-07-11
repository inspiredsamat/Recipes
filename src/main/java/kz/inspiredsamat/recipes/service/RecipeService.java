package kz.inspiredsamat.recipes.service;

import org.springframework.http.ResponseEntity;

import kz.inspiredsamat.recipes.model.Recipe;
import kz.inspiredsamat.recipes.model.User;

import java.util.List;

public interface RecipeService {
    Recipe saveRecipe(Recipe newRecipe, User user);

    Recipe getRecipeById(Long id);

    void deleteRecipeById(Long id, User user);

    ResponseEntity<List<Recipe>> searchByParam(String name, String category);

    void updateRecipe(Long id, Recipe recipe, User user);
}
