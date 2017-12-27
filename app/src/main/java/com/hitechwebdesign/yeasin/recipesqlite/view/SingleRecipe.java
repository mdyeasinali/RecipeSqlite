package com.hitechwebdesign.yeasin.recipesqlite.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.hitechwebdesign.yeasin.recipesqlite.R;
import com.hitechwebdesign.yeasin.recipesqlite.database.RecipeDbSource;
import com.hitechwebdesign.yeasin.recipesqlite.database.UsersDbSource;
import com.hitechwebdesign.yeasin.recipesqlite.session.MyPreferencesHelperSinglaton;

import static com.hitechwebdesign.yeasin.recipesqlite.database.DatabaseHelper.REC_NAME;

public class SingleRecipe extends AppCompatActivity {
    private String names, recImage, recing, recdir, pretime;
    private TextView ing, pre,edit,delete;
    private String email;
    private  int id;
    private ImageButton favorite;
    private RecipeDbSource source;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_recipe);
        source = new RecipeDbSource(getApplicationContext());
        email = MyPreferencesHelperSinglaton.getInstance(getApplicationContext()).getString(MyPreferencesHelperSinglaton.Key.KEY_USER_EMAIL,"");

        //RECEIVE DATA
        Intent i = this.getIntent();

        id = i.getExtras().getInt("R_ID");
        names = i.getExtras().getString("REC_NAME");
        recImage = i.getExtras().getString("REC_IMAGE");
        recing = i.getExtras().getString("REC_ING");
        recdir = i.getExtras().getString("REC_DIR");
        pretime = i.getExtras().getString("PRETIME_KEY");
        ing = (TextView) findViewById(R.id.textIngredients);
        pre = (TextView) findViewById(R.id.textPreparation);
        setTitle(names);

        edit = (TextView) findViewById(R.id.editRecipeTextView);
        delete = (TextView) findViewById(R.id.deleteRecipeTextView);
        favorite = (ImageButton) findViewById(R.id.btn_favorite);

        if (email.equals("admin@gmail.com")) {
            edit.setVisibility(View.VISIBLE);
            delete.setVisibility(View.VISIBLE);
        }else{
            edit.setVisibility(View.GONE);
            delete.setVisibility(View.GONE);
        }
        ing.setText(recing);
        pre.setText(recdir);

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                final boolean recFavorate = source.insertFavorate(,id);
//                if(recFavorate){
//                    Toast.makeText(SingleRecipe.this, "Sucess", Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(SingleRecipe.this, "Error", Toast.LENGTH_SHORT).show();
//                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final boolean recipedelcte = source.deleteRecipe(id);
                if(recipedelcte){
                    Toast.makeText(SingleRecipe.this, "Sucess", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(SingleRecipe.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}
