package net.foxtam.hyperskillorg.recipes.business;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonIgnore
    private long id;

    @NotBlank
    @Column(name = "name")
    private String name;

    @NotBlank
    @Column(name = "category")
    private String category;
    
    @Column(name = "date")
    private LocalDateTime date;
    
    @NotBlank
    @Column(name = "description")
    private String description;

    @Size(min = 1)
    @ElementCollection
    @CollectionTable(
            name = "ingredients",
            joinColumns = @JoinColumn(name = "recipe_id"))
    @Column(name = "ingredient")
    private List<String> ingredients = new ArrayList<>();

    @Size(min = 1)
    @ElementCollection
    @CollectionTable(
            name = "directions",
            joinColumns = @JoinColumn(name = "recipe_id"))
    @Column(name = "direction")
    private List<String> directions = new ArrayList<>();
}
