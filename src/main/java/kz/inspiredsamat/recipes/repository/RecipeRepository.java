package kz.inspiredsamat.recipes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kz.inspiredsamat.recipes.model.Recipe;
import kz.inspiredsamat.recipes.model.User;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findAll();

    Recipe findRecipeByIdAndAndUser(Long id, User user);
}
