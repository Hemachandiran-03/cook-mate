package com.chanproject.cookmate;

import com.chanproject.cookmate.ClassObjects.RecipeClass;
import com.chanproject.cookmate.ClassObjects.RecipeList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

        @GET("/recipes/{uid}")
        Call<RecipeClass> getRecipeById(@Path("uid") String uid);

        @GET("/recipes?limit=10&select=name,image,cuisine")
        Call<RecipeList> getRecommentation(@Query("skip") int skipCount);

        @GET("/recipes?sortBy=rating&order=desc&limit=10&select=name,image,rating,prepTimeMinutes,cookTimeMinutes")
        Call<RecipeList> getTopRated();

        @GET("/recipes/meal-type/{type}")
        Call<RecipeList> getCategory(@Path("type") String type);

        @GET("/recipes/search")
        Call<RecipeList> getSearch(@Query("q") String search);

}
