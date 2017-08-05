package com.example.android.foodwhips.models;

/**
 * Created by li-en on 7/2/17.
 */

public class SearchRecipe {
    private String img;
    private String sourceDisplayName;
    private String[] ingredients;
    private String id;
    private String recipeName;
    private String timeInSecs;
    private String rating;
    private String[] courses;
    private String[] cuisines;

    private String totalResults;


    public SearchRecipe(String img, String sourceDisplayName, String[] ingredients, String id,
                        String recipeName, String timeInSecs, String rating, String[] courses,
                        String[] cuisines, String totalResults) {
        this.img = img;
        this.sourceDisplayName = sourceDisplayName;
        this.ingredients = ingredients;
        this.id = id;
        this.recipeName = recipeName;
        this.timeInSecs = timeInSecs;
        this.rating = rating;
        this.courses = courses;
        this.cuisines = cuisines;
        this.totalResults = totalResults;
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

    public String printIngredients() {
        String list = "";
        for(int i = 0; i < ingredients.length; i++){
            list += ingredients[i] + (i < ingredients.length - 1 ? ", " : "");
        }
        return list;
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

    public String[] getCourses() {
        return courses;
    }

    public void setCourses() {
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

    public String[] getCuisines(){
        return cuisines;
    }


    public void setCuisines(){
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

    public String getTotalResults(){
        return totalResults;
    }

    public void setTotalResults(String newTotal){
        this.totalResults = newTotal;
    }
}