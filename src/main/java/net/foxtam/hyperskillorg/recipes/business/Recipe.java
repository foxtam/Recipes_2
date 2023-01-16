package net.foxtam.hyperskillorg.recipes.business;

import lombok.Data;

import java.util.List;

@Data
public class Recipe {
    private String name;
    private String description;
    private List<String> ingredients;
    private List<String> directions;
}
