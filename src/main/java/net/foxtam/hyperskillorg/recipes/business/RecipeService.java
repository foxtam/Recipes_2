package net.foxtam.hyperskillorg.recipes.business;

import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class RecipeService {
    private final ConcurrentMap<Integer, Recipe> recipes = new ConcurrentHashMap<>();
    private int idCounter = 0;

    public synchronized int addRecipe(Recipe recipe) {
        idCounter++;
        recipes.put(idCounter, recipe);
        return idCounter;
    }

    public synchronized Optional<Recipe> getRecipe(int id) {
        return Optional.ofNullable(recipes.get(id));
    }
}
