package com.chanproject.cookmate;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chanproject.cookmate.ClassObjects.RecipeClass;
import com.chanproject.cookmate.ClassObjects.RecipeList;
import com.chanproject.cookmate.Utils.Utils;
import com.chanproject.cookmate.recyclerviewAdapters.SearchRecyclerViewAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {



    private String categoryType;
    private LinearLayout searchLayout;
    private TextView searchTitle,searchBtn,noRecipeFound;
    private EditText searchEditText;
    private ProgressBar searchProgressBar;
    private RecyclerView searchRecyclerView;
    private RecyclerView.Adapter searchAdapter;
    private LinearLayoutManager searchLayoutManager;
    private ArrayList<RecipeClass> searchArrayList = new ArrayList<>();
    private ApiInterface apiInterface;

    private Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        utils = new Utils(this);

        searchLayout=findViewById(R.id.searchSectionLayout);
        searchTitle=findViewById(R.id.searchHeadingTextView);
        searchProgressBar=findViewById(R.id.searchActivityProgressBar);
        searchRecyclerView=findViewById(R.id.searchRecyclerView);
        searchEditText=findViewById(R.id.searchInput);
        searchBtn=findViewById(R.id.searchBtn);
        noRecipeFound=findViewById(R.id.searchActivityEmpty);

        if(getIntent().getExtras()!=null){
            int type = getIntent().getExtras().getInt("type");
            if(type==1){

                categoryType=getIntent().getExtras().getString("category");
                searchTitle.setText(categoryType);
                searchLayout.setVisibility(View.GONE);


                searchProgressBar.setVisibility(View.VISIBLE);
                searchRecyclerView.setVisibility(View.GONE);
                Call<RecipeList>callCategory = apiInterface.getCategory(categoryType);
                callCategory.enqueue(new Callback<RecipeList>() {
                    @Override
                    public void onResponse(Call<RecipeList> call, Response<RecipeList> response) {

                        Log.v("SEARCH",response.raw().request().url().url().toString());

                        Log.v("SEARCH",response.body().toString());


                        if(response.body()!=null){

                            searchArrayList.addAll(response.body().getRecipeArrayList());

                            if(searchArrayList.size()==0){
                                noRecipeFound.setVisibility(View.VISIBLE);
                            }else {
                                noRecipeFound.setVisibility(View.GONE);
                            }

                            searchAdapter = new SearchRecyclerViewAdapter(searchArrayList);
                            searchLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);

                            searchRecyclerView.setLayoutManager(searchLayoutManager);
                            searchRecyclerView.setAdapter(searchAdapter);

                            searchProgressBar.setVisibility(View.GONE);
                            searchRecyclerView.setVisibility(View.VISIBLE);
                            utils.hideKeyboard(searchEditText);

                        }

                    }

                    @Override
                    public void onFailure(Call<RecipeList> call, Throwable throwable) {

                    }
                });


            }else if(type==2){
                searchTitle.setText("Search");
                searchLayout.setVisibility(View.VISIBLE);
                searchProgressBar.setVisibility(View.GONE);
            }
        }



        searchBtn.setOnClickListener(view -> {

            searchProgressBar.setVisibility(View.VISIBLE);
            searchRecyclerView.setVisibility(View.GONE);
            noRecipeFound.setVisibility(View.GONE);
            searchArrayList.clear();

            String keyword = searchEditText.getText().toString();
            Log.v("SEARCH","1"+keyword);

            apiInterface.getSearch(keyword).enqueue(new Callback<RecipeList>() {
                @Override
                public void onResponse(Call<RecipeList> call, Response<RecipeList> response) {

                    Log.v("SEARCH",response.raw().request().url().url().toString());

                    Log.v("SEARCH",response.body().toString());

                    searchArrayList.addAll(response.body().getRecipeArrayList());

                    if(searchArrayList.size()==0){
                        noRecipeFound.setVisibility(View.VISIBLE);
                    }else {
                        noRecipeFound.setVisibility(View.GONE);
                    }

                    searchAdapter = new SearchRecyclerViewAdapter(searchArrayList);
                    searchLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);

                    searchRecyclerView.setLayoutManager(searchLayoutManager);
                    searchRecyclerView.setAdapter(searchAdapter);

                    searchProgressBar.setVisibility(View.GONE);
                    searchRecyclerView.setVisibility(View.VISIBLE);
                    utils.hideKeyboard(searchEditText);


                }

                @Override
                public void onFailure(Call<RecipeList> call, Throwable throwable) {
                    Log.v("SEARCH",throwable.getMessage().toString());
                }
            });


        });


    }
}