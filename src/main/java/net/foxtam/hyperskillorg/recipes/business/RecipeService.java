package net.foxtam.hyperskillorg.recipes.business;

import net.foxtam.hyperskillorg.recipes.persistance.Recipe;
import net.foxtam.hyperskillorg.recipes.persistance.RecipesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
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
        log.trace("add recipe: {}", recipe);
        recipe.setDate(LocalDateTime.now());
        repository.save(recipe);
        log.trace("add recipe: {}", recipe);
        return recipe.getId();
    }

    public Optional<Recipe> getRecipe(long id) {
        return repository.findById(id);
    }

    public boolean deleteRecipe(long id) {
        return repository.deleteRecipeById(id) > 0;
    }

    @Transactional
    public boolean updateRecipeById(long id, Recipe recipe) {
        boolean exists = repository.existsById(id);
        if (exists) {
            recipe.setId(id);
            recipe.setDate(LocalDateTime.now());
            repository.save(recipe);
        }
        return exists;
    }

    public List<Recipe> getRecipesByCategory(String category) {
        return repository.findByCategoryIgnoreCaseOrderByDateDesc(category);
    }

    public List<Recipe> getContainingName(String name) {
        return repository.findByNameContainingIgnoreCaseOrderByDateDesc(name);
    }
}
