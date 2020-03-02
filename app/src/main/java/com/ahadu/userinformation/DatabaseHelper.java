package com.ahadu.userinformation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "user.db";
    public static final String TABLE_NAME= "user_info";
    public static final String fname= "full_name";
    public static final String username= "user_name";
    public static final String phone= "phone";
    public static final String email= "email";
    public static final String password = "password";
    public static final String sex = "sex";
    public static final String date = "Date";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTable = "CREATE TABLE " + TABLE_NAME +
                " ( "
                + fname + " varchar(50) ,"
                + username + " varchar(50) ,"
                + phone + " varchar(50) ,"
                + email + " varchar(50) ,"
                + password + " varchar(50) ,"
                + sex + " varchar(50) "
                + " ) ";
        db.execSQL(createUserTable);
    }


    //called when the database needs to upgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if( oldVersion != newVersion){
            db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
            onCreate(db);
        }
    }

    public boolean insert(String fullname, String username, String mail, String ph_num, String pwd, String gender){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues= new ContentValues();

        contentValues.put("full_name",fullname);
        contentValues.put("user_name",username);
        contentValues.put("email",mail);
        contentValues.put("phone",ph_num);
        contentValues.put("password",pwd);
        contentValues.put("sex",gender);



        long result= db.insert(TABLE_NAME,null,contentValues);
        if(result == -1){
            return false;
        }else {
            return true;
        }
    }

    public Cursor getAllData() {
        SQLiteDatabase db= getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        return res;
    }

    public Cursor getDataForLogin(String mail){
        SQLiteDatabase db = getWritableDatabase();
        String filter = username + " , " + password;
        String condition = "'"+ mail +"'" + " = " + username;
        Cursor res = db.rawQuery("SELECT "+ filter + " FROM " + TABLE_NAME + " WHERE " + condition, null );
        return res;
    }
    public Cursor getDataForRecycler(){
        SQLiteDatabase db = getWritableDatabase();
        String filter = username + " , " + password;

        Cursor res = db.rawQuery("SELECT "+ filter + " FROM " + TABLE_NAME , null );
        return res;
    }
    public Cursor getDataForDetails(String filter){
        SQLiteDatabase db = getWritableDatabase();
        String filterer = email + " ";
        String condition = "'"+ filter +"'" + " = " + username;
        Cursor res = db.rawQuery("SELECT "+ filter + " FROM " + TABLE_NAME + " WHERE " + condition, null );
        return res;
    }

    public void delete(String user_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = "'"+user_name+"'"+ " = "+username;
        Cursor cursor = db.rawQuery("DELETE FROM "+TABLE_NAME+" WHERE "+condition ,null);
        cursor.close();
    }
}