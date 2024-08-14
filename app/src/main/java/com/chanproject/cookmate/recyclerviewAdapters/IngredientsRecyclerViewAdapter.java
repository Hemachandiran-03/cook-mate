package com.chanproject.cookmate.recyclerviewAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chanproject.cookmate.R;

import java.util.ArrayList;

public class IngredientsRecyclerViewAdapter extends RecyclerView.Adapter<IngredientsRecyclerViewAdapter.IngredientsRecyclerViewHolder> {

    private ArrayList<String> ingredientsList;

    public IngredientsRecyclerViewAdapter(ArrayList<String> arg) {
        this.ingredientsList = arg;
    }

    public class IngredientsRecyclerViewHolder extends RecyclerView.ViewHolder{

        public TextView itemTextView;

        public IngredientsRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            itemTextView = itemView.findViewById(R.id.ingredientsItem);

        }
    }


    @NonNull
    @Override
    public IngredientsRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredients_item_view,parent,false);
        IngredientsRecyclerViewAdapter.IngredientsRecyclerViewHolder viewHolder = new IngredientsRecyclerViewAdapter.IngredientsRecyclerViewHolder(view);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsRecyclerViewHolder holder, int position) {

        holder.itemTextView.setText(ingredientsList.get(position));

    }

    @Override
    public int getItemCount() {
        return ingredientsList.size();
    }


}
