package com.chanproject.cookmate.ClassObjects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class RecipeList {

    @SerializedName("recipes")
    @Expose
    private List<RecipeClass> recipeArrayList;

    public List<RecipeClass> getRecipeArrayList() {
        return recipeArrayList;
    }

    public void setRecipeArrayList(List<RecipeClass> recipeArrayList) {
        this.recipeArrayList = recipeArrayList;
    }
}
