package com.chanproject.cookmate.recyclerviewAdapters;

        import android.content.Intent;
        import android.graphics.drawable.Drawable;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;

        import androidx.annotation.DrawableRes;
        import androidx.annotation.NonNull;
        import androidx.cardview.widget.CardView;
        import androidx.core.graphics.drawable.DrawableCompat;
        import androidx.recyclerview.widget.RecyclerView;

        import com.chanproject.cookmate.ClassObjects.CategoryClass;
        import com.chanproject.cookmate.R;
        import com.chanproject.cookmate.SearchActivity;

        import java.util.ArrayList;

public class CategoriesRecyclerViewAdapter extends RecyclerView.Adapter<CategoriesRecyclerViewAdapter.CategoriesRecyclerViewHolder> {

    private ArrayList<CategoryClass> categoryClassArrayList;

    public CategoriesRecyclerViewAdapter(ArrayList<CategoryClass> categoryClassArrayList) {
        this.categoryClassArrayList = categoryClassArrayList;
    }

    public static class CategoriesRecyclerViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageView;
        public TextView textView;

        public CardView cardView;

        public CategoriesRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView3);
            cardView = itemView.findViewById(R.id.categoriesCardViewID);

        }
    }


    @NonNull
    @Override
    public CategoriesRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_view,parent,false);
        CategoriesRecyclerViewHolder viewHolder = new CategoriesRecyclerViewHolder(view);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesRecyclerViewHolder holder, int position) {

        CategoryClass item = categoryClassArrayList.get(position);

        holder.textView.setText(item.getCategoryName());
        holder.imageView.setImageResource(item.getImagePath());

        holder.cardView.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), SearchActivity.class);
            intent.putExtra("type",1);
            intent.putExtra("category",item.getCategoryName());
            view.getContext().startActivity(intent);
        });


    }

    @Override
    public int getItemCount() {
        return categoryClassArrayList.size();
    }


}
