package com.hitechwebdesign.yeasin.recipesqlite.adpter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.hitechwebdesign.yeasin.recipesqlite.R;
import com.hitechwebdesign.yeasin.recipesqlite.model.RecipeModel;
import com.hitechwebdesign.yeasin.recipesqlite.view.SingleRecipe;

import java.util.List;

/**
 * Created by Yeasin on 12/27/2017.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecyclerViewHolders> {

    private List<RecipeModel> recipeitem;
    private Context context;

    public RecipeAdapter(Context context, List<RecipeModel> recipeitem) {
        this.recipeitem = recipeitem;
        this.context = context;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;
    }
    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
       String name = recipeitem.get(position).getName();
       String cat = recipeitem.get(position).getCatgory();
       String image = recipeitem.get(position).getImage();
       String ing = recipeitem.get(position).getIngredients();
       String pre = recipeitem.get(position).getPreparation();
       String time = recipeitem.get(position).getTime();
        holder.recipeName.setText(name);
        holder.recipePhoto.setImageDrawable(Drawable.createFromPath("file:///"+recipeitem.get(position).getImage()));

        holder.recipeOwner.setText("Category by : "+recipeitem.get(position).getCatgory());

        final int id = recipeitem.get(position).getId();
        final String recName = recipeitem.get(position).getName();
        final String recIng =  recipeitem.get(position).getIngredients();
        final String recDir =  recipeitem.get(position).getPreparation();
        final String images = recipeitem.get(position).getImage();
        final String preTime = recipeitem.get(position).getTime();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SingleRecipe.class);
                intent.putExtra("R_ID",id);
                intent.putExtra("REC_NAME",recName);
                intent.putExtra("REC_IMAGE",images);
                intent.putExtra("REC_ING",recIng);
                intent.putExtra("REC_DIR",recDir);
                intent.putExtra("PRETIME_KEY",preTime);
                context.startActivity(intent);
            }
        });




    }

    @Override
    public int getItemCount() {
        return this.recipeitem.size();
    }

    public class RecyclerViewHolders extends RecyclerView.ViewHolder implements  View.OnLongClickListener {

        public TextView recipeOwner;
        public TextView recipeName;
        public ImageView recipePhoto;
        public TextView publisher_name;


        public RecyclerViewHolders(View itemView) {
            super(itemView);
            itemView.setOnLongClickListener(this);
            recipeName = (TextView) itemView.findViewById(R.id.country_name);
            recipeOwner = (TextView) itemView.findViewById(R.id.recipe_owner);
            recipePhoto = (ImageView) itemView.findViewById(R.id.country_photo);
        }


        @Override
        public boolean onLongClick(View view) {

           // messageDialog("dsf","dsf",context);

            return false;
        }

    }


}