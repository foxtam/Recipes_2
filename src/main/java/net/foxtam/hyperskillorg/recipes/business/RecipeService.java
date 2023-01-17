package net.foxtam.hyperskillorg.recipes.business;

import net.foxtam.hyperskillorg.recipes.persistance.RecipesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RecipeService {

    private static final Logger log = LoggerFactory.getLogger(RecipeService.class);

    private final RecipesRepository repository;

    @Autowired
    public RecipeService(RecipesRepository repository) {
        this.repository = repository;
    }

    public long addRecipe(Recipe recipe) {
        repository.save(recipe);
        log.trace("addRecipe after save: {}", recipe);
        return recipe.getId();
    }

    public Optional<Recipe> getRecipe(long id) {
        return repository.findById(id);
    }

    public boolean deleteRecipe(long id) {
        Optional<Recipe> optional = repository.findById(id);
        optional.ifPresent(recipe -> repository.deleteById(recipe.getId()));
        return optional.isPresent();
    }
}
