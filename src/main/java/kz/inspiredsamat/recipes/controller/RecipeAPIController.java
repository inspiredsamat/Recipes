package kz.inspiredsamat.recipes.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import kz.inspiredsamat.recipes.model.Recipe;
import kz.inspiredsamat.recipes.model.User;
import kz.inspiredsamat.recipes.service.RecipeService;
import kz.inspiredsamat.recipes.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
public class RecipeAPIController {

    private final UserService userService;
    private final RecipeService recipeService;

    public RecipeAPIController(RecipeService recipeService, UserService userService) {
        this.recipeService = recipeService;
        this.userService = userService;
    }

    @GetMapping("/api/recipe/{id}")
    public Recipe getRecipe(@PathVariable Long id) {
        return recipeService.getRecipeById(id);
    }

    @GetMapping("/api/recipe/search")
    public ResponseEntity<List<Recipe>> search(@Valid @RequestParam(required = false) String name,
            @Valid @RequestParam(required = false) String category) {
        return recipeService.searchByParam(name, category);
    }

    @PostMapping("/api/recipe/new")
    public Map<String, Long> postRecipe(@AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody Recipe newRecipe) {
        User user = userService.findUserByEmail(userDetails.getUsername());
        Recipe recipeCreate = recipeService.saveRecipe(newRecipe, user);
        return Map.of("id", recipeCreate.getId());
    }

    @DeleteMapping("/api/recipe/{id}")
    public void deleteRecipe(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id) {
        User user = userService.findUserByEmail(userDetails.getUsername());
        recipeService.deleteRecipeById(id, user);
    }

    @PutMapping("/api/recipe/{id}")
    public void updateRecipe(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody Recipe recipe,
            @PathVariable Long id) {
        User user = userService.findUserByEmail(userDetails.getUsername());
        recipeService.updateRecipe(id, recipe, user);
    }
}