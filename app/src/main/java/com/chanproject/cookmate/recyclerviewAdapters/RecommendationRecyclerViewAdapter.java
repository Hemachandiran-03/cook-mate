package com.chanproject.cookmate.recyclerviewAdapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.chanproject.cookmate.ClassObjects.CategoryClass;
import com.chanproject.cookmate.ClassObjects.RecipeClass;
import com.chanproject.cookmate.R;
import com.chanproject.cookmate.RecipeDetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecommendationRecyclerViewAdapter extends RecyclerView.Adapter<RecommendationRecyclerViewAdapter.RecommendationRecyclerViewHolder> {

    private ArrayList<RecipeClass> recipeArrayList;

    public RecommendationRecyclerViewAdapter(ArrayList<RecipeClass> arg2) {
        this.recipeArrayList = arg2;
    }



    public static class RecommendationRecyclerViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageView;
        public TextView title;
        public TextView cuisine;

        public LinearLayout recommentedItem;

        public RecommendationRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.recommendationImageViewID);
            title = itemView.findViewById(R.id.recommendationTitleViewID);
            cuisine = itemView.findViewById(R.id.recommendationCuisineViewID);
            recommentedItem = itemView.findViewById(R.id.recommendationItem);
        }
    }


    @NonNull
    @Override
    public RecommendationRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommendation_item_view,parent,false);
        RecommendationRecyclerViewHolder viewHolder = new RecommendationRecyclerViewHolder(view);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendationRecyclerViewHolder holder, int position) {


        RecipeClass item = recipeArrayList.get(position);

        String name = item.getName();

        if(name.length()>20){
            name = name.substring(0,17)+"...";
        }

        holder.title.setText(name);
        holder.cuisine.setText(item.getCuisine());

        Picasso.get().load(item.getImage()).fit().into(holder.imageView);

        holder.recommentedItem.setOnClickListener(view -> {

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




