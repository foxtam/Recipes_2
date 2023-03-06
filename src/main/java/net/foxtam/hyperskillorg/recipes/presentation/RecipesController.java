package net.foxtam.hyperskillorg.recipes.presentation;

import net.foxtam.hyperskillorg.recipes.business.RecipeService;
import net.foxtam.hyperskillorg.recipes.business.UserService;
import net.foxtam.hyperskillorg.recipes.persistance.Recipe;
import net.foxtam.hyperskillorg.recipes.persistance.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RestController
public class RecipesController {
    private static final Logger log = LoggerFactory.getLogger(RecipesController.class);
    private final RecipeService recipeService;
    private final UserService userService;

    @Autowired
    public RecipesController(RecipeService recipeService, UserService userService) {
        this.recipeService = recipeService;
        this.userService = userService;
    }

    @PostMapping("/api/recipe/new")
    ResponseEntity<Map<?, ?>> postRecipe(@AuthenticationPrincipal UserDetails details,
                                         @Valid @RequestBody Recipe recipe) {
        log.trace("postRecipe input: {}", recipe);
        Optional<User> optionalUser = userService.getUserByEmail(details.getUsername());
        recipe.setUser(optionalUser.orElseThrow());
        long id = recipeService.addRecipe(recipe);
        return ResponseEntity.ok(Map.of("id", id));
    }

    @GetMapping("/api/recipe/{id}")
    ResponseEntity<Recipe> getRecipe(@PathVariable long id) {
        Optional<Recipe> optionalRecipe = recipeService.getRecipe(id);
        log.trace("getRecipe: {}", optionalRecipe);
        return optionalRecipe.map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/api/recipe/{id}")
    ResponseEntity<?> deleteRecipe(@AuthenticationPrincipal UserDetails details,
                                   @PathVariable long id) {
        Optional<Recipe> optionalRecipe = recipeService.getRecipe(id);
        if (optionalRecipe.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if (optionalRecipe.get().getUser().getEmail().equals(details.getUsername())) {
            recipeService.deleteRecipe(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/api/recipe/{id}")
    ResponseEntity<?> updateRecipe(@AuthenticationPrincipal UserDetails details,
                                   @PathVariable long id,
                                   @Valid @RequestBody Recipe recipe) {
        Optional<Recipe> optionalRecipe = recipeService.getRecipe(id);
        if (optionalRecipe.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if (optionalRecipe.get().getUser().getEmail().equals(details.getUsername())) {
            recipe.setUser(optionalRecipe.get().getUser());
            recipeService.updateRecipeById(id, recipe);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/api/recipe/search")
    ResponseEntity<?> getRecipes(@RequestParam(required = false) String category,
                                 @RequestParam(required = false) String name) {
        if (Collections.frequency(Arrays.asList(name, category), null) != 1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (category != null) {
            return ResponseEntity.ok(recipeService.getRecipesByCategory(category));
        } else {
            return ResponseEntity.ok(recipeService.getRecipesContainingName(name));
        }
    }
}
