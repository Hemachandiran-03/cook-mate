package com.chanproject.cookmate.recyclerviewAdapters;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chanproject.cookmate.ClassObjects.RecipeClass;
import com.chanproject.cookmate.R;
import com.chanproject.cookmate.RecipeDetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.SearchRecyclerViewHolder> {

    private ArrayList<RecipeClass> recipeArrayList;

    public SearchRecyclerViewAdapter(ArrayList<RecipeClass> recipeArrayList) {
        this.recipeArrayList = recipeArrayList;
    }

    public class SearchRecyclerViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageView;
        public TextView name,cuisine,mealType,rating;

        public LinearLayout searchItemLayout;

        public SearchRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.searchItemImage);
            name = itemView.findViewById(R.id.searchItemName);
            cuisine = itemView.findViewById(R.id.searchItemCuisine);
            mealType = itemView.findViewById(R.id.searchItemMealType);
            rating = itemView.findViewById(R.id.searchItemRating);
            searchItemLayout = itemView.findViewById(R.id.searchResultItemLayout);
        }
    }


    @NonNull
    @Override
    public SearchRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_recipe_item_view,parent,false);
        SearchRecyclerViewAdapter.SearchRecyclerViewHolder viewHolder = new SearchRecyclerViewAdapter.SearchRecyclerViewHolder(view);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchRecyclerViewHolder holder, int position) {
        RecipeClass item = recipeArrayList.get(position);

        holder.name.setText(item.getName());
        holder.cuisine.setText(item.getCuisine());

        String mealType=" ";
        for(String str : item.getMealType()){
            mealType = mealType + str + " | ";
        }
        holder.mealType.setText(mealType);

        holder.rating.setText(String.valueOf(item.getRating()));

        Picasso.get().load(item.getImage()).fit().into(holder.imageView);

        holder.searchItemLayout.setOnClickListener(view -> {

            Log.v("success",item.getName()+":"+item.getId());

            Intent intent = new Intent(view.getContext(), RecipeDetailsActivity.class);
            intent.putExtra("recipeID",item.getId());
            view.getContext().startActivity(intent);

        });

    }

    @Override
    public int getItemCount() {
        return recipeArrayList.size();
    }


}
