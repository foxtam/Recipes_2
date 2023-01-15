package net.foxtam.hyperskillorg.recipes.representation;

import net.foxtam.hyperskillorg.recipes.business.Recipe;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecipesController {

    private static final Logger log = LoggerFactory.getLogger(RecipesController.class);

    private Recipe lastRecipe;

    @PostMapping("/api/recipe")
    void postRecipe(@RequestBody Recipe recipe) {
        log.debug("{}", recipe);
        lastRecipe = recipe;
    }

    @GetMapping("/api/recipe")
    Recipe getRecipe() {
        return lastRecipe;
    }
}
