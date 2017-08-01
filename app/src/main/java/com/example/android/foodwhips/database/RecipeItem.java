package com.example.android.foodwhips.database;

public class RecipeItem {
    private String recipeId;
    private String title;
    private String sourceName;
    private String sourceUrl;
    private String imgUrl;
    private String rating;
    private String timeTaken;
    private String servings;
    private String courses;
    private String cuisines;
    private String flavors;

    private String ingredients;

    private String favorite;
    private String photo;

    public RecipeItem(String recipeId, String title, String sourceName,
                      String sourceUrl, String imgUrl, String rating, String timeTaken,
                      String servings, String courses, String cuisines,
                      String flavors, String ingredients, String favorite,
                      String photo) {
        this.recipeId = recipeId;
        this.title = title;
        this.sourceName = sourceName;
        this.sourceUrl = sourceUrl;
        this.imgUrl = imgUrl;
        this.rating = rating;
        this.timeTaken = timeTaken;
        this.servings = servings;
        this.courses = courses;
        this.cuisines = cuisines;
        this.flavors = flavors;
        this.ingredients = ingredients;
        this.favorite = favorite;
        this.photo = photo;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(String timeTaken) {
        this.timeTaken = timeTaken;
    }

    public String getServings() {
        return servings;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }

    public String getCourses() {
        return courses;
    }

    public void setCourses(String courses) {
        this.courses = courses;
    }

    public String getCuisines() {
        return cuisines;
    }

    public void setCuisines(String cuisines) {
        this.cuisines = cuisines;
    }

    public String getFlavors() {
        return flavors;
    }

    public void setFlavors(String flavors) {
        this.flavors = flavors;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
