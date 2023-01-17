package net.foxtam.hyperskillorg.recipes.business;

import net.foxtam.hyperskillorg.recipes.persistance.RecipesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class RecipeService {

    private static final Logger LOG = LoggerFactory.getLogger(RecipeService.class);

    private final RecipesRepository repository;

    @Autowired
    public RecipeService(RecipesRepository repository) {
        this.repository = repository;
    }

    public long addRecipe(Recipe recipe) {
        recipe.setDate(LocalDateTime.now());
        repository.save(recipe);
        LOG.trace("addRecipe after save: {}", recipe);
        return recipe.getId();
    }

    public Optional<Recipe> getRecipe(long id) {
        return repository.findById(id);
    }

    public boolean deleteRecipe(long id) {
        Optional<Recipe> optionalRecipe = repository.findById(id);
        optionalRecipe.ifPresent(recipe -> repository.deleteById(recipe.getId()));
        return optionalRecipe.isPresent();
    }

    public boolean updateRecipeById(long id, Recipe newRecipe) {
        Optional<Recipe> optionalRecipe = repository.findById(id);
        optionalRecipe.ifPresent(recipe -> {
            recipe.setName(newRecipe.getName());
            recipe.setCategory(newRecipe.getCategory());
            recipe.setDate(LocalDateTime.now());
            recipe.setDescription(newRecipe.getDescription());
            recipe.setIngredients(newRecipe.getIngredients());
            recipe.setDirections(newRecipe.getDirections());
            repository.save(recipe);
        });
        return optionalRecipe.isPresent();
    }
}
