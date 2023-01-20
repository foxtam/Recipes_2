package net.foxtam.hyperskillorg.recipes.persistance;

import net.foxtam.hyperskillorg.recipes.business.Recipe;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RecipesRepository extends CrudRepository<Recipe, Long> {

    List<Recipe> findByCategoryIgnoreCaseOrderByDateDesc(String category);

    List<Recipe> findByNameContainingIgnoreCaseOrderByDateDesc(String name);

    @Transactional
    @Modifying
    @Query("delete from Recipe r where r.id = ?1")
    int deleteRecipeById(long id);
}
