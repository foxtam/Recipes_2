package net.foxtam.hyperskillorg.recipes.presentation;

import jakarta.validation.Valid;
import net.foxtam.hyperskillorg.recipes.business.Recipe;
import net.foxtam.hyperskillorg.recipes.business.RecipeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class RecipesController {
    private static final Logger log = LoggerFactory.getLogger(RecipesController.class);
    private final RecipeService recipeService;

    @Autowired
    public RecipesController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/api/recipe/new")
    ResponseEntity<Map<?, ?>> postRecipe(@Valid @RequestBody Recipe recipe) {
        log.trace("postRecipe: {}", recipe);
        long id = recipeService.addRecipe(recipe);
        return ResponseEntity.ok(Map.of("id", id));
    }

    @GetMapping("/api/recipe/{id}")
    ResponseEntity<Recipe> getRecipe(@Valid @PathVariable long id) {
        Optional<Recipe> recipe = recipeService.getRecipe(id);
        log.trace("getRecipe: {}", recipe);
        return recipe.map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @DeleteMapping("/api/recipe/{id}")
    ResponseEntity<?> deleteRecipe(@Valid @PathVariable long id) {
        boolean deleted = recipeService.deleteRecipe(id);
        return ResponseEntity.status(deleted ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND).build();
    }
}
