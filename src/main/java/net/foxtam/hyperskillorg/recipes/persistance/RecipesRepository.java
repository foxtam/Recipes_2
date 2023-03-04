package net.foxtam.hyperskillorg.recipes.persistance;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipesRepository extends CrudRepository<Recipe, Long> {

    List<Recipe> findByCategoryIgnoreCaseOrderByDateDesc(String category);

    List<Recipe> findByNameContainingIgnoreCaseOrderByDateDesc(String name);

//    @Transactional
//    @Modifying
//    @Query("delete from Recipe r where r.id = ?1")
//    int deleteRecipeById(long id);

    long deleteRecipeById(long id);

}
