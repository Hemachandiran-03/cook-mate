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

public class TopRatedRecyclerViewAdapter extends RecyclerView.Adapter<TopRatedRecyclerViewAdapter.TopRatedRecyclerViewHolder> {

    private ArrayList<RecipeClass> recipeArrayList;

    public TopRatedRecyclerViewAdapter(ArrayList<RecipeClass> recipeArrayList) {
        this.recipeArrayList = recipeArrayList;
    }

    public class TopRatedRecyclerViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageView;
        public TextView title,time,rating;

        public LinearLayout topRatedLayout;

        public TopRatedRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.topratedImageViewID);
            title = itemView.findViewById(R.id.topRatedTitleView);
            time = itemView.findViewById(R.id.topRatedTimeView);
            rating = itemView.findViewById(R.id.topRatedRatingView);
            topRatedLayout = itemView.findViewById(R.id.topRatedLayout);
        }
    }


    @NonNull
    @Override
    public TopRatedRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_rated_item_view,parent,false);
        TopRatedRecyclerViewAdapter.TopRatedRecyclerViewHolder viewHolder = new TopRatedRecyclerViewAdapter.TopRatedRecyclerViewHolder(view);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TopRatedRecyclerViewHolder holder, int position) {
        RecipeClass item = recipeArrayList.get(position);

        String name = item.getName();
        if(name.length()>20){
            name = name.substring(0,17)+"...";
        }
        holder.title.setText(name);
        holder.time.setText(String.valueOf(item.getPrepTimeMinutes()+item.getCookTimeMinutes()) + " min");
        holder.rating.setText(String.valueOf(item.getRating()));

        Picasso.get().load(item.getImage()).fit().into(holder.imageView);

        holder.topRatedLayout.setOnClickListener(view -> {

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
