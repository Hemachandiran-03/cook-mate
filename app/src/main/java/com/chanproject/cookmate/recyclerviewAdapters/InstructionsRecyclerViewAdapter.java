package com.chanproject.cookmate.recyclerviewAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chanproject.cookmate.R;

import java.util.ArrayList;

public class InstructionsRecyclerViewAdapter extends RecyclerView.Adapter<InstructionsRecyclerViewAdapter.InstructionsRecyclerViewHolder> {

    private ArrayList<String> instructionsList;

    public InstructionsRecyclerViewAdapter(ArrayList<String> arg) {
        this.instructionsList = arg;
    }

    public class InstructionsRecyclerViewHolder extends RecyclerView.ViewHolder{

        public TextView instruction,instructionPosition;

        public InstructionsRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            instruction = itemView.findViewById(R.id.instructionItem);
            instructionPosition = itemView.findViewById(R.id.instructionPositionItem);

        }
    }


    @NonNull
    @Override
    public InstructionsRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.instructions_item_view,parent,false);
        InstructionsRecyclerViewAdapter.InstructionsRecyclerViewHolder viewHolder = new InstructionsRecyclerViewAdapter.InstructionsRecyclerViewHolder(view);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionsRecyclerViewHolder holder, int position) {

        holder.instruction.setText(instructionsList.get(position));
        holder.instructionPosition.setText((position+1)+ ".");

    }

    @Override
    public int getItemCount() {
        return instructionsList.size();
    }


}
