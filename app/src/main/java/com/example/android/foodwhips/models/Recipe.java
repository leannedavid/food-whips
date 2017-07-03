package com.example.android.foodwhips.models;

/**
 * Created by li-en on 7/2/17.
 */

public class Recipe {
    private String img;
    private String sourceDisplayName;
    private String ingredients[];
    private String id;
    private String recipeName;
    private String timeInSecs;
    private String rating;

    public Recipe(String img, String sourceDisplayName, String[] ingredients, String id,
                  String recipeName, String timeInSecs, String rating) {
        this.img = img;
        this.sourceDisplayName = sourceDisplayName;
        this.ingredients = ingredients;
        this.id = id;
        this.recipeName = recipeName;
        this.timeInSecs = timeInSecs;
        this.rating = rating;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSourceDisplayName() {
        return sourceDisplayName;
    }

    public void setSourceDisplayName(String sourceDisplayName) {
        this.sourceDisplayName = sourceDisplayName;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getTimeInSecs() {
        return timeInSecs;
    }

    public void setTimeInSecs(String timeInSecs) {
        this.timeInSecs = timeInSecs;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}