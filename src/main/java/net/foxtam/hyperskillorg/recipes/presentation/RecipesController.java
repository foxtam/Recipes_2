package net.foxtam.hyperskillorg.recipes.presentation;

import jakarta.validation.Valid;
import net.foxtam.hyperskillorg.recipes.business.RecipeService;
import net.foxtam.hyperskillorg.recipes.business.UserService;
import net.foxtam.hyperskillorg.recipes.persistance.Recipe;
import net.foxtam.hyperskillorg.recipes.persistance.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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
    ResponseEntity<Map<?, ?>> postRecipe(@Valid @RequestBody Recipe recipe) {
        log.trace("postRecipe input: {}", recipe);
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
    ResponseEntity<?> deleteRecipe(@PathVariable long id) {
        boolean deleted = recipeService.deleteRecipe(id);
        return new ResponseEntity<>(deleted ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND);
    }

    @PutMapping("/api/recipe/{id}")
    ResponseEntity<?> putRecipe(@PathVariable long id, @Valid @RequestBody Recipe recipe) {
        boolean updated = recipeService.updateRecipeById(id, recipe);
        return new ResponseEntity<>(updated ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND);
    }

    @GetMapping("/api/recipe/search")
    ResponseEntity<?> getRecipes(@RequestParam(required = false) String category,
                                 @RequestParam(required = false) String name) {
        if (Collections.frequency(Arrays.asList(name, category), null) != 1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (category != null) {
            return ResponseEntity.ok(recipeService.getRecipesByCategory(category));
        } else {
            return ResponseEntity.ok(recipeService.getContainingName(name));
        }
    }

    @PostMapping("/api/register")
    ResponseEntity<?> register(@Valid @RequestBody User user) {
        user.setPassword(getEncoder().encode(user.getPassword()));
        boolean registered = userService.registerUser(user);
        return new ResponseEntity<>(registered ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }
}
