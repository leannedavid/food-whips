package com.example.android.foodwhips.models;

/**
 * Created by li-en on 7/10/17.
 */

public class GetRecipe {
    private String totalTime;
    private String imgUrl;
    private String recipeName;
    private String sourceName;
    private String sourceRecipeUrl;
    private String id;
    private String[] ingredientsList;
    private String servings;
    private String[] cuisines;
    private String[] courses;
    private String[] flavors;
    private String rating;

    public GetRecipe(String totalTime, String imgUrl, String recipeName, String sourceName,
                     String sourceRecipeUrl, String id, String[] ingredientsList,
                     String servings, String[] cuisines, String[] courses, String[] flavors,
                     String rating) {
        this.totalTime = totalTime;
        this.imgUrl = imgUrl;
        this.recipeName = recipeName;
        this.sourceName = sourceName;
        this.sourceRecipeUrl = sourceRecipeUrl;
        this.id = id;
        this.ingredientsList = ingredientsList;
        this.servings = servings;
        this.cuisines = cuisines;
        this.courses = courses;
        this.flavors = flavors;
        this.rating = rating;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getSourceRecipeUrl() {
        return sourceRecipeUrl;
    }

    public void setSourceRecipeUrl(String sourceRecipeUrl) {
        this.sourceRecipeUrl = sourceRecipeUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String[] getIngredientsList() {
        return ingredientsList;
    }

    public void setIngredientsList(String[] ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    public String printIngredients() {
        String list = "";
        for(int i = 0; i < ingredientsList.length; i++){
            list += ingredientsList[i] + "\n";
        }
        return list;
    }

    public String getServings() {
        return servings;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }

    public String[] getCuisines() {
        return cuisines;
    }

    public void setCuisines(String[] cuisines) {
        this.cuisines = cuisines;
    }

    public String printCuisines() {
        String list = "";
        for(int i = 0; i < cuisines.length; i++){
            list += cuisines[i] + (i < cuisines.length - 1 ? ", " : "");
        }
        return list;
    }

    public boolean isCuisinesEmpty(){
        if (cuisines == null || cuisines.length == 0) { return true; }
        return false;
    }

    public String[] getCourses() {
        return courses;
    }

    public void setCourses(String[] courses) {
        this.courses = courses;
    }

    public String printCourses(){
        String list = "";
        for(int i = 0; i < courses.length; i++){
            list += courses[i] + (i < courses.length - 1 ? ", " : "");
        }
        return list;
    }

    public boolean isCoursesEmpty(){
        if (courses == null || courses.length == 0) { return true; }
        return false;
    }

    public String[] getFlavors() {
        return flavors;
    }

    public void setFlavors(String[] flavors) {
        this.flavors = flavors;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
