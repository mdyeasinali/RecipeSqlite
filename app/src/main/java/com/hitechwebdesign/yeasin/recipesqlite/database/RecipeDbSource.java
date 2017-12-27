package com.hitechwebdesign.yeasin.recipesqlite.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;


import com.hitechwebdesign.yeasin.recipesqlite.model.RecipeModel;

import java.util.ArrayList;

/**
 * Created by BITM Student md yeasin ail  on 12/27/2017.
 */
public class RecipeDbSource {
    private DatabaseHelper helper;
    private SQLiteDatabase db;

    public RecipeDbSource(Context context) {
        helper = new DatabaseHelper(context);
    }

    public void open() {
        db = helper.getWritableDatabase();
    }

    public void close() {
        db.close();
    }


    public ArrayList<RecipeModel> getAllRecipes() {
        this.open();
        ArrayList<RecipeModel> recipe = new ArrayList<>();
        Cursor cursor = db.query(DatabaseHelper.TABLE_RECIPE, null, null, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.REC_ID));
                String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.REC_NAME));
                String ingr = cursor.getString(cursor.getColumnIndex(DatabaseHelper.REC_ING));
                String pre = cursor.getString(cursor.getColumnIndex(DatabaseHelper.REC_PRE));
                String time = cursor.getString(cursor.getColumnIndex(DatabaseHelper.REC_TIME));
                String diff = cursor.getString(cursor.getColumnIndex(DatabaseHelper.REC_CAT));
                String image = cursor.getString(cursor.getColumnIndex(DatabaseHelper.REC_IMG));
                recipe.add(new RecipeModel(id, name, ingr, pre, time, diff, image));
            } while (cursor.moveToNext());
        }
        cursor.close();
        this.close();
        return recipe;
    }

    public boolean insertRecipe(RecipeModel recipeModel) {
        this.open();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.REC_NAME, recipeModel.getName());
        values.put(DatabaseHelper.REC_ING, recipeModel.getIngredients());
        values.put(DatabaseHelper.REC_PRE, recipeModel.getPreparation());
        values.put(DatabaseHelper.REC_TIME, recipeModel.getTime());
        values.put(DatabaseHelper.REC_CAT, recipeModel.getCatgory());
        values.put(DatabaseHelper.REC_IMG, recipeModel.getImage());
        long insertedRow = db.insert(DatabaseHelper.TABLE_RECIPE, null, values);
        this.close();
        if (insertedRow > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean updateRecipe(RecipeModel fullRecipe) {
        this.open();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.REC_NAME, fullRecipe.getName());
        values.put(DatabaseHelper.REC_ING, fullRecipe.getIngredients());
        values.put(DatabaseHelper.REC_PRE, fullRecipe.getPreparation());
        values.put(DatabaseHelper.REC_TIME, fullRecipe.getTime());
        values.put(DatabaseHelper.REC_CAT, fullRecipe.getCatgory());
        values.put(DatabaseHelper.REC_IMG, fullRecipe.getImage());
        int updatedRow = db.update(DatabaseHelper.TABLE_RECIPE,
                values,
                DatabaseHelper.REC_ID + "=" + fullRecipe.getId(),
                null);
        if (updatedRow > 0) {
            return true;
        } else {
            return false;
        }

    }


    public boolean deleteRecipe(int id) {
        this.open();
        int deletedRow = db.delete(DatabaseHelper.TABLE_RECIPE,
                DatabaseHelper.REC_ID + "=" + id, null);
        if (deletedRow > 0) {
            return true;
        } else {
            return false;
        }
    }


    public RecipeModel getRecipeById(String id) {
        this.open();
        RecipeModel recipe = null;
        Cursor cursor = db.query(DatabaseHelper.TABLE_RECIPE,
                null,
                DatabaseHelper.REC_ID + "=" + id,
                null,
                null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            int ids = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.REC_ID));
            String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.REC_NAME));
            String ingr = cursor.getString(cursor.getColumnIndex(DatabaseHelper.REC_ING));
            String pre = cursor.getString(cursor.getColumnIndex(DatabaseHelper.REC_PRE));
            String time = cursor.getString(cursor.getColumnIndex(DatabaseHelper.REC_TIME));
            String cat = cursor.getString(cursor.getColumnIndex(DatabaseHelper.REC_CAT));
            String image = cursor.getString(cursor.getColumnIndex(DatabaseHelper.REC_IMG));
            recipe = new RecipeModel(new RecipeModel(ids, name, ingr, pre, time, cat, image));
        }
        this.close();
        return recipe;
    }

    public Boolean insertFavorate(String u_id,String r_id) {
       this.open();
        try {
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.FAV_RECID, r_id);
            values.put(DatabaseHelper.FAV_USERID, u_id);
            long insert = db.insert(DatabaseHelper.TABLE_FAVORIT, null, values);
            if (insert == -1) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkFavourite(int id) {
        this.open();
        Cursor c = db.rawQuery("SELECT 1 FROM "+DatabaseHelper.TABLE_RECIPE+" WHERE "+DatabaseHelper.RAT_RECID+" = ?", new String[] {String.valueOf(id)});
        boolean exists = c.moveToFirst();
        c.close();
        return exists;
    }

    public Boolean RecipeComment(String u_id,String r_id,String comment) {
        this.open();
        try {
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COM_RECID, r_id);
            values.put(DatabaseHelper.COM_USERID, u_id);
            values.put(DatabaseHelper.COM_TEXT, comment);
            long insert = db.insert(DatabaseHelper.TABLE_COMMENT, null, values);
            if (insert == -1) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }






}
