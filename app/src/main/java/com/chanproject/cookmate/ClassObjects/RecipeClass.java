package com.chanproject.cookmate.ClassObjects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;



public class RecipeClass {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("ingredients")
    @Expose
    private ArrayList<String> ingredients;
    @SerializedName("instructions")
    @Expose
    private ArrayList<String> instructions;
    @SerializedName("prepTimeMinutes")
    @Expose
    private int prepTimeMinutes;
    @SerializedName("cookTimeMinutes")
    @Expose
    private int cookTimeMinutes;
    @SerializedName("servings")
    @Expose
    private int servings;
    @SerializedName("difficulty")
    @Expose
    private String difficulty;
    @SerializedName("cuisine")
    @Expose
    private String cuisine;
    @SerializedName("caloriesPerServing")
    @Expose
    private int caloriesPerServing;
    @SerializedName("tags")
    @Expose
    private ArrayList<String> tags;
    @SerializedName("userId")
    @Expose
    private int userid;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("rating")
    @Expose
    private float rating;
    @SerializedName("reviewCount")
    @Expose
    private int reviewCount;
    @SerializedName("mealType")
    @Expose
    private ArrayList<String> mealType;

    public RecipeClass(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<String> getInstructions() {
        return instructions;
    }

    public void setInstructions(ArrayList<String> instructions) {
        this.instructions = instructions;
    }

    public int getPrepTimeMinutes() {
        return prepTimeMinutes;
    }

    public void setPrepTimeMinutes(int prepTimeMinutes) {
        this.prepTimeMinutes = prepTimeMinutes;
    }

    public int getCookTimeMinutes() {
        return cookTimeMinutes;
    }

    public void setCookTimeMinutes(int cookTimeMinutes) {
        this.cookTimeMinutes = cookTimeMinutes;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public int getCaloriesPerServing() {
        return caloriesPerServing;
    }

    public void setCaloriesPerServing(int caloriesPerServing) {
        this.caloriesPerServing = caloriesPerServing;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public ArrayList<String> getMealType() {
        return mealType;
    }

    public void setMealType(ArrayList<String> mealType) {
        this.mealType = mealType;
    }
}
