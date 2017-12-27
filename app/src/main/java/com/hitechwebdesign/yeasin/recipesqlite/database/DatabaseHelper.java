package com.hitechwebdesign.yeasin.recipesqlite.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by BITM Student md yeasin ail  on 12/27/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "bitmrecipe";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_USER = "user";
    public static final String TABLE_RECIPE = "recipe";
    public static final String TABLE_COMMENT = "comment";
    public static final String TABLE_FAVORIT = "favorit";
    public static final String TABLE_RATING = "rating";

    //---------------------user column ---------------//
    public static final String USER_ID = "uId";
    public static final String USER_NAME = "uName";
    public static final String USER_EMAIL = "email";
    public static final String USER_PASS = "password";
    public static final String USER_IMG = "image";
    public static final String USER_PHN = "phone";
    public static final String USER_STATUS = "status";
    //-----------recipe  column -----------------------//
    public static final String REC_ID = "_id";
    public static final String REC_NAME = "name";
    public static final String REC_ING = "ingredients";
    public static final String REC_PRE = "preparation";
    public static final String REC_TIME = "time";
    public static final String REC_CAT = "category";
    public static final String REC_IMG = "image";
    //-----------favorit column -----------------------//
    public static final String FAV_ID = "f_id";
    public static final String FAV_USERID = "uId";
    public static final String FAV_RECID = "_id";

    //-----------rating column -----------------------//
    public static final String RAT_ID = "r_id";
    public static final String RAT_USERID = "uId";
    public static final String RAT_RECID = "_id";
    public static final String RAT_POINT = "point";

    //-----------comment column -----------------------//
    public static final String COM_ID = "c_id";
    public static final String COM_USERID = "uId";
    public static final String COM_RECID = "_id";
    public static final String COM_TEXT = "commentest";

    public static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + "(" +
            USER_ID + " INTEGER PRIMARY KEY, " +
            USER_NAME + " TEXT, " +
            USER_EMAIL + " TEXT, " +
            USER_PASS + " TEXT, " +
            USER_IMG + " TEXT, " +
            USER_PHN + " TEXT, " +
            USER_STATUS + " TEXT);";

    public static final String CREATE_TABLE_RECIPE = "CREATE TABLE " + TABLE_RECIPE + "(" +
            REC_ID + " INTEGER PRIMARY KEY, " +
            REC_NAME + " TEXT, " +
            REC_ING + " TEXT, " +
            REC_PRE + " TEXT, " +
            REC_TIME + " TEXT, " +
            REC_CAT + " TEXT, " +
            REC_IMG + " TEXT);";

    public static final String CREATE_TABLE_FAVORIT = "CREATE TABLE " + TABLE_FAVORIT + "(" +
            FAV_ID + " INTEGER PRIMARY KEY, " +
            FAV_USERID + " INTEGER, " +
            FAV_RECID + " INTEGER);";

    public static final String CREATE_TABLE_RATING = "CREATE TABLE " + TABLE_RATING + "(" +
            RAT_ID + " INTEGER PRIMARY KEY, " +
            RAT_USERID + " INTEGER, " +
            RAT_POINT + " TEXT, " +
            RAT_RECID + " INTEGER);";

    public static final String CREATE_TABLE_COMMENT = "CREATE TABLE " + TABLE_COMMENT + "(" +
            COM_ID + " INTEGER PRIMARY KEY, " +
            COM_USERID + " INTEGER, " +
            COM_RECID + " INTEGER, " +
            COM_TEXT + " TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_RECIPE);
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_FAVORIT);
        db.execSQL(CREATE_TABLE_RATING);
        db.execSQL(CREATE_TABLE_COMMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORIT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RATING);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENT);
        onCreate(db);
    }
}
