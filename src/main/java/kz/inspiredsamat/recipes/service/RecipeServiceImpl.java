package kz.inspiredsamat.recipes.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import kz.inspiredsamat.recipes.model.Recipe;
import kz.inspiredsamat.recipes.model.User;
import kz.inspiredsamat.recipes.repository.RecipeRepository;
import kz.inspiredsamat.recipes.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeServiceImpl implements RecipeService {
    RecipeRepository recipeRepository;
    UserRepository userRepository;
    PasswordEncoder encoder;

    public RecipeServiceImpl(RecipeRepository recipeRepository, UserRepository userRepository,
            PasswordEncoder encoder) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public Recipe saveRecipe(Recipe recipe, User user) {
        recipe.setDate(LocalDateTime.now());
        recipe.setUser(user);
        return recipeRepository.save(recipe);
    }

    @Override
    public Recipe getRecipeById(Long id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        if (recipe.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return recipe.get();
    }

    @Override
    public void deleteRecipeById(Long id, User user) {
        recipeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found"));
        Recipe recipeWithUser = recipeRepository.findRecipeByIdAndAndUser(id, user);
        if (recipeWithUser == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        } else {
            recipeRepository.deleteById(id);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }

    @Override
    public ResponseEntity<List<Recipe>> searchByParam(String name, String category) {
        if ((name == null && category == null) || (name != null && category != null)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<Recipe> recipeList = recipeRepository.findAll();
        List<Recipe> recipeResult = new ArrayList<>();
        Collections.reverse(recipeList);

        if (name != null) {
            name = name.toLowerCase();
            for (Recipe recipe : recipeList) {
                if (recipe.getName().toLowerCase().contains(name)) {
                    recipeResult.add(recipe);
                }
            }
        } else {
            category = category.toLowerCase();
            for (Recipe recipe : recipeList) {
                if (recipe.getCategory().toLowerCase().equals(category)) {
                    recipeResult.add(recipe);
                }
            }
        }
        return new ResponseEntity<>(recipeResult, HttpStatus.OK);
    }

    @Override
    public void updateRecipe(Long id, Recipe recipe, User user) {
        Recipe recipeToUpdate = recipeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Recipe recipeWithUser = recipeRepository.findRecipeByIdAndAndUser(id, user);
        if (recipeWithUser == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        } else {
            recipeToUpdate.setName(recipe.getName());
            recipeToUpdate.setCategory(recipe.getCategory());
            recipeToUpdate.setDescription(recipe.getDescription());
            recipeToUpdate.setIngredients(recipe.getIngredients());
            recipeToUpdate.setDirections(recipe.getDirections());
            recipeToUpdate.setDate(LocalDateTime.now());
            recipeRepository.save(recipeToUpdate);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }
}