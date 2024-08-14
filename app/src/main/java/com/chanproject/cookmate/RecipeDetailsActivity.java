package com.chanproject.cookmate;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chanproject.cookmate.ClassObjects.RecipeClass;
import com.chanproject.cookmate.recyclerviewAdapters.IngredientsRecyclerViewAdapter;
import com.chanproject.cookmate.recyclerviewAdapters.InstructionsRecyclerViewAdapter;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeDetailsActivity extends AppCompatActivity {

    private ImageView recipeDetailsImage,detailsBtn,instructionBtn,ingredientBtn;
    private CardView recipeDetailsBackButton;
    private TextView recipeDetailsTitle,recipeDetailsCuisine,recipeDetailsRating,recipeDetailsTime,recipeDetailsDifficaluty,recipeDetailsCalories,recipeDetailsMealType;
    private RecyclerView recipeDetailsIngredientsRecyclerView,recipeDetailsInstructionsRecyclerView;

    private RecyclerView.Adapter recipeDetailsIngredientsAdapter,recipeDetailsInstructionsAdapter;
    private LinearLayoutManager recipeDetailsIngredientsLayoutManager,recipeDetailsInstructionsLayoutManager;

    private ConstraintLayout detailsLayout;
    private LinearLayout instructionLayout,ingredientLayout;

    private int recipeId;
    private RecipeClass recipe;

    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recipe_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (getIntent().getExtras() != null) {
            recipeId = getIntent().getExtras().getInt("recipeID");
            Log.v("success", "Recivied id : " + recipeId);
        } else {
            recipeId = 11;
        }

        apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);

        recipeDetailsImage = findViewById(R.id.recipeDetailsImage);
        recipeDetailsBackButton = findViewById(R.id.recipeDetailsBackButton);
        recipeDetailsTitle = findViewById(R.id.recipeDetailsTitle);
        recipeDetailsCuisine = findViewById(R.id.recipeDetailsCuisine);
        recipeDetailsRating = findViewById(R.id.recipeDetailsRating);
        recipeDetailsMealType = findViewById(R.id.recipeDetailsMealType);
        recipeDetailsTime = findViewById(R.id.recipeDetailsTime);
        recipeDetailsDifficaluty = findViewById(R.id.recipeDetailsDifficaluty);
        recipeDetailsCalories = findViewById(R.id.recipeDetailsCalories);
        recipeDetailsIngredientsRecyclerView = findViewById(R.id.recipeDetailsIngredientsRecyclerView);
        recipeDetailsInstructionsRecyclerView = findViewById(R.id.recipeDetailsInstructionsRecyclerView);

        detailsBtn = findViewById(R.id.detailBtn);
        ingredientBtn = findViewById(R.id.ingredientsBtn);
        instructionBtn = findViewById(R.id.instructionBtn);
        detailsLayout = findViewById(R.id.detailLayout);
        ingredientLayout = findViewById(R.id.ingredientsLayout);
        instructionLayout = findViewById(R.id.instructionLayout);

        Call<RecipeClass> call = apiInterface.getRecipeById(String.valueOf(recipeId));
        call.enqueue(new Callback<RecipeClass>() {
            @Override
            public void onResponse(Call<RecipeClass> call, Response<RecipeClass> response) {
                recipe = response.body();

                Picasso.get().load(recipe.getImage()).centerCrop().fit().into(recipeDetailsImage);

                recipeDetailsTitle.setText(recipe.getName());
                recipeDetailsCuisine.setText(recipe.getCuisine());
                recipeDetailsRating.setText(String.valueOf(recipe.getRating()));
                recipeDetailsTime.setText(" "+String.valueOf(recipe.getCookTimeMinutes()+recipe.getPrepTimeMinutes()) + " mins");
                recipeDetailsDifficaluty.setText(" "+recipe.getDifficulty());
                recipeDetailsCalories.setText(" "+String.valueOf(recipe.getCaloriesPerServing()) + " cal");

                String mealType=" ";
                for(String str : recipe.getMealType()){
                    mealType = mealType + str + " | ";
                }

                recipeDetailsMealType.setText(mealType);

                recipeDetailsInstructionsLayoutManager = new LinearLayoutManager(getApplicationContext());
                recipeDetailsInstructionsAdapter = new InstructionsRecyclerViewAdapter(recipe.getInstructions());

                recipeDetailsInstructionsRecyclerView.setLayoutManager(recipeDetailsInstructionsLayoutManager);
                recipeDetailsInstructionsRecyclerView.setAdapter(recipeDetailsInstructionsAdapter);

                recipeDetailsIngredientsLayoutManager = new LinearLayoutManager(getApplicationContext());
                recipeDetailsIngredientsAdapter = new IngredientsRecyclerViewAdapter(recipe.getIngredients());

                recipeDetailsIngredientsRecyclerView.setLayoutManager(recipeDetailsIngredientsLayoutManager);
                recipeDetailsIngredientsRecyclerView.setAdapter(recipeDetailsIngredientsAdapter);


            }

            @Override
            public void onFailure(Call<RecipeClass> call, Throwable throwable) {
                Log.v("failure", throwable.getMessage());
            }
        });


        recipeDetailsBackButton.setOnClickListener(view -> {
            finish();
        });

        detailsBtn.setOnClickListener(view -> {

            detailsLayout.setVisibility(View.VISIBLE);
            ingredientLayout.setVisibility(View.GONE);
            instructionLayout.setVisibility(View.GONE);

            detailsBtn.setColorFilter(ContextCompat.getColor(this,R.color.green));
            ingredientBtn.setColorFilter(ContextCompat.getColor(this,R.color.grey));
            instructionBtn.setColorFilter(ContextCompat.getColor(this,R.color.grey));


        });

        ingredientBtn.setOnClickListener(view -> {

            detailsLayout.setVisibility(View.GONE);
            ingredientLayout.setVisibility(View.VISIBLE);
            instructionLayout.setVisibility(View.GONE);

            detailsBtn.setColorFilter(ContextCompat.getColor(this,R.color.grey));
            ingredientBtn.setColorFilter(ContextCompat.getColor(this,R.color.green));
            instructionBtn.setColorFilter(ContextCompat.getColor(this,R.color.grey));

        });

        instructionBtn.setOnClickListener(view -> {

            detailsLayout.setVisibility(View.GONE);
            ingredientLayout.setVisibility(View.GONE);
            instructionLayout.setVisibility(View.VISIBLE);

            detailsBtn.setColorFilter(ContextCompat.getColor(this,R.color.grey));
            ingredientBtn.setColorFilter(ContextCompat.getColor(this,R.color.grey));
            instructionBtn.setColorFilter(ContextCompat.getColor(this,R.color.green));

        });


    }

}