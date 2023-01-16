package net.foxtam.hyperskillorg.recipes.persistance;

import net.foxtam.hyperskillorg.recipes.business.Recipe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipesRepository extends CrudRepository<Recipe, Long> {
}
