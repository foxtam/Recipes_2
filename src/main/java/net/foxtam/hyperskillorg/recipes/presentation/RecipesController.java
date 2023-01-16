package net.foxtam.hyperskillorg.recipes.representation;

import net.foxtam.hyperskillorg.recipes.business.Recipe;
import net.foxtam.hyperskillorg.recipes.business.RecipeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    ResponseEntity<Map<?, ?>> postRecipe(@RequestBody Recipe recipe) {
        log.trace("{}", recipe);
        int id = recipeService.addRecipe(recipe);
        return ResponseEntity.ok(Map.of("id", id));
    }

    @GetMapping("/api/recipe/{id}")
    ResponseEntity<Recipe> getRecipe(@PathVariable int id) {
        Optional<Recipe> recipe = recipeService.getRecipe(id);
        return recipe.map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }
}
