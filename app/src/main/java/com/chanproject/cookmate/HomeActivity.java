package com.chanproject.cookmate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chanproject.cookmate.ClassObjects.CategoryClass;
import com.chanproject.cookmate.ClassObjects.RecipeClass;
import com.chanproject.cookmate.ClassObjects.RecipeList;
import com.chanproject.cookmate.Utils.Utils;
import com.chanproject.cookmate.recyclerviewAdapters.CategoriesRecyclerViewAdapter;
import com.chanproject.cookmate.recyclerviewAdapters.IngredientsRecyclerViewAdapter;
import com.chanproject.cookmate.recyclerviewAdapters.InstructionsRecyclerViewAdapter;
import com.chanproject.cookmate.recyclerviewAdapters.RecommendationRecyclerViewAdapter;
import com.chanproject.cookmate.recyclerviewAdapters.TopRatedRecyclerViewAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {


    private RecyclerView categoriesRecyclerview,recommentationRecyclerView,topRatedRecyclerView;
    private RecyclerView.Adapter categoriesAdapter,recommentationAdapter,topRatedAdapter;
    private RecyclerView.LayoutManager categoriesLayoutManager,recommentationLayoutManager,topRatedLayoutManager;

    private TextView searchBtn,usernameTxt;
    private ImageView avatarImg;

    private ArrayList<CategoryClass> categoryList = new ArrayList<>();
    private ArrayList<RecipeClass> recommenationList = new ArrayList<>();
    private ArrayList<RecipeClass> topRatedList = new ArrayList<>();

    private ApiInterface apiInterface;

    private Utils utils;

    private String uID,username,avatarUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        categoriesRecyclerview = findViewById(R.id.categoriesRecyclerviewId);
        recommentationRecyclerView = findViewById(R.id.recommendationRecyclerviewId);
        topRatedRecyclerView = findViewById(R.id.topRatedRecyclerviewId);
        searchBtn = findViewById(R.id.searchBtnHome);
        usernameTxt = findViewById(R.id.homeUsernameTxtView);
        avatarImg = findViewById(R.id.homeAvatarImgViewID);

        utils = new Utils(this);

        uID = utils.getSharedPreferences().getString(ContextCompat.getString(this,R.string.UID),"default");
        utils.getDatabaseReference().child(uID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    username = snapshot.child("username").getValue().toString();
                    usernameTxt.setText("Hello, "+username);

                    if(snapshot.child("avatar").exists()){
                        avatarUrl = snapshot.child("avatar").getValue().toString();
                        Picasso.get().load(avatarUrl).fit().centerCrop().into(avatarImg);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        Log.v("HOME","UID:"+uID+"|username:"+username);


        final int randomSkipCount = new Random().nextInt(40);

        categoryList.add(new CategoryClass("Breakfast",R.drawable.breakfast));
        categoryList.add(new CategoryClass("Lunch",R.drawable.lunch));
        categoryList.add(new CategoryClass("Dinner",R.drawable.dinner));
        categoryList.add(new CategoryClass("Snack",R.drawable.snack));
        categoryList.add(new CategoryClass("Dessert",R.drawable.dessert));


        categoriesLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        categoriesAdapter = new CategoriesRecyclerViewAdapter(categoryList);

        categoriesRecyclerview.setLayoutManager(categoriesLayoutManager);;
        categoriesRecyclerview.setAdapter(categoriesAdapter);

        searchBtn.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(),SearchActivity.class);
            intent.putExtra("type",2);
            intent.putExtra("category","Search");
            startActivity(intent);
        });

        avatarImg.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
            startActivity(intent);
        });


        apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);


        Call<RecipeList> callRecommentation = apiInterface.getRecommentation(randomSkipCount);
        callRecommentation.enqueue(new Callback<RecipeList>() {
            @Override
            public void onResponse(Call<RecipeList> call, Response<RecipeList> response) {


                Log.v("HOME",response.raw().request().url().url().toString());

                Log.v("HOME", response.body().toString());

                if(response.body() != null){

                    if(response.body().getRecipeArrayList()==null){
                        Log.v("HOME", "SIZE IS ZERO");
                    }else{
                        Log.v("HOME", "SIZE IS "+response.body().getRecipeArrayList().size());
                    }

                    recommenationList.addAll(response.body().getRecipeArrayList());

                    recommentationLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
                    recommentationAdapter = new RecommendationRecyclerViewAdapter(recommenationList);

                    recommentationRecyclerView.setLayoutManager(recommentationLayoutManager);;
                    recommentationRecyclerView.setAdapter(recommentationAdapter);

                }else {
                    Log.v("HOME", "RESULT IS NULL");
                }

            }

            @Override
            public void onFailure(Call<RecipeList> call, Throwable throwable) {
                Log.v("failure", throwable.getMessage());
            }
        });

        Call<RecipeList> callTopRated = apiInterface.getTopRated();
        callTopRated.enqueue(new Callback<RecipeList>() {
            @Override
            public void onResponse(Call<RecipeList> call, Response<RecipeList> response) {


                Log.v("success", response.body().toString());

                if(response.body() != null){

                    if(response.body().getRecipeArrayList()==null){
                        Log.v("success", "SIZE IS ZERO");
                    }else{
                        Log.v("success", "SIZE IS "+response.body().getRecipeArrayList().size());
                    }

                    topRatedList.addAll(response.body().getRecipeArrayList());


                    topRatedLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
                    topRatedAdapter = new TopRatedRecyclerViewAdapter(topRatedList);

                    topRatedRecyclerView.setLayoutManager(topRatedLayoutManager);;
                    topRatedRecyclerView.setAdapter(topRatedAdapter);

                }else {
                    Log.v("success", "RESULT IS NULL");
                }

            }

            @Override
            public void onFailure(Call<RecipeList> call, Throwable throwable) {
                Log.v("failure", throwable.getMessage());
            }
        });


    }
}