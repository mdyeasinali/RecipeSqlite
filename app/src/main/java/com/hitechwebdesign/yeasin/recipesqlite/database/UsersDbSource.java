package com.hitechwebdesign.yeasin.recipesqlite.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.hitechwebdesign.yeasin.recipesqlite.model.RecipeModel;
import com.hitechwebdesign.yeasin.recipesqlite.model.UsersModel;

import java.util.ArrayList;

/**
 * Created by BITM Student md yeasin ail  on 12/27/2017.
 */
public class UsersDbSource {
    private DatabaseHelper helper;
    private SQLiteDatabase db;

    public UsersDbSource(Context context) {
        helper = new DatabaseHelper(context);
    }

    public void open() {
        db = helper.getWritableDatabase();
    }

    public void close() {
        db.close();
    }


    public ArrayList<UsersModel> getAllUser() {
        this.open();
        ArrayList<UsersModel> user = new ArrayList<>();
        Cursor cursor = db.query(DatabaseHelper.TABLE_USER, null, null, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.USER_ID));
                String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.USER_NAME));
                String email = cursor.getString(cursor.getColumnIndex(DatabaseHelper.USER_EMAIL));
                String pass = cursor.getString(cursor.getColumnIndex(DatabaseHelper.USER_PASS));
                String phone = cursor.getString(cursor.getColumnIndex(DatabaseHelper.USER_PHN));
                String status = cursor.getString(cursor.getColumnIndex(DatabaseHelper.USER_STATUS));
                String image = cursor.getString(cursor.getColumnIndex(DatabaseHelper.USER_IMG));
                user.add(new UsersModel(id, name, email, pass, phone, status, image));
            } while (cursor.moveToNext());
        }
        cursor.close();
        this.close();
        return user;
    }

    public boolean insertUser(UsersModel usersModel) {
        this.open();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.USER_NAME, usersModel.getUn());
        values.put(DatabaseHelper.USER_EMAIL, usersModel.getUemail());
        values.put(DatabaseHelper.USER_PASS, usersModel.getPassword());
        values.put(DatabaseHelper.USER_PHN, usersModel.getUPhn());
        values.put(DatabaseHelper.USER_STATUS, usersModel.getuStatus());;
        long insertedRow = db.insert(DatabaseHelper.TABLE_USER, null, values);
        this.close();
        if (insertedRow > 0) {
            return true;
        } else {
            return false;
        }
    }


    public boolean checkUser(String email, String password) {

        // array of columns to fetch
        String[] columns = {
                DatabaseHelper.USER_ID
        };
        this.open();
        String selection = DatabaseHelper.USER_EMAIL + " = ?" + " AND " + DatabaseHelper.USER_PASS  + " = ?";
        String[] selectionArgs = {email, password};
        Cursor cursor = db.query(DatabaseHelper.TABLE_USER,columns,selection,selectionArgs,null,null,null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }




    public boolean deleteUser(int id) {
        this.open();
        int deletedRow = db.delete(DatabaseHelper.TABLE_USER,
                DatabaseHelper.USER_ID + "=" + id, null);
        if (deletedRow > 0) {
            return true;
        } else {
            return false;
        }
    }


    public UsersModel getUserByEmail(String email) {
        this.open();
        UsersModel user = null;
        try {
        Cursor cursor = db.query(DatabaseHelper.TABLE_USER,null,DatabaseHelper.USER_EMAIL + "=" + email,null,null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            int idS = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.USER_ID));
            String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.USER_NAME));
            String emaild = cursor.getString(cursor.getColumnIndex(DatabaseHelper.USER_EMAIL));
            user = new UsersModel(idS, name, email);
        }
        this.close();
        } catch (Exception e) {
            e.printStackTrace();
            return user;
        }
        return user;
    }
}
