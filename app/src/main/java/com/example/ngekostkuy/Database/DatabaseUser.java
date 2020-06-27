package com.example.ngekostkuy.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.wifi.p2p.WifiP2pManager;

import com.example.ngekostkuy.Model.User;

import java.util.ArrayList;
import java.util.List;

import static com.example.ngekostkuy.Contract.UserContract.UserEntry.COLUMN_DOB;
import static com.example.ngekostkuy.Contract.UserContract.UserEntry.COLUMN_GENDER;
import static com.example.ngekostkuy.Contract.UserContract.UserEntry.COLUMN_PASSWORD;
import static com.example.ngekostkuy.Contract.UserContract.UserEntry.COLUMN_PHONE_NUMBER;
import static com.example.ngekostkuy.Contract.UserContract.UserEntry.COLUMN_USERNAME;
import static com.example.ngekostkuy.Contract.UserContract.UserEntry.COLUMN_USER_ID;
import static com.example.ngekostkuy.Contract.UserContract.UserEntry.TABLE_USER;
import static com.example.ngekostkuy.Storage.StorageUser.users;

public class DatabaseUser {
    private DatabaseHelper databaseHelper;
    private Context mContext;
    private SQLiteDatabase sqLiteDatabase;
    private static String userId;

    public DatabaseUser(Context context) {
        this.mContext = context;
        databaseHelper = new DatabaseHelper(context);
    }

    public void openDB(){
        sqLiteDatabase = databaseHelper.getWritableDatabase();
    }

    public void insertUser(User user) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USER_ID,user.getUserId());
        contentValues.put(COLUMN_USERNAME,user.getUsername());
        contentValues.put(COLUMN_PASSWORD,user.getPassword());
        contentValues.put(COLUMN_DOB,user.getDateofBirth());
        contentValues.put(COLUMN_GENDER,user.getGender());
        contentValues.put(COLUMN_PHONE_NUMBER,user.getPhoneNumber());
        sqLiteDatabase.insert(TABLE_USER, null,contentValues);
        sqLiteDatabase.close();
    }

    public ArrayList<User> getAllUser(){
        openDB();
        String SELECT_QUERY = "SELECT * FROM " + TABLE_USER;
        ArrayList<User> userList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_QUERY,null);
        if ( cursor.moveToFirst()) {
            do{
                User user = new User();
                user.setUserId(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID)));
                user.setUsername(cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD)));
                user.setDateofBirth(cursor.getString(cursor.getColumnIndex(COLUMN_DOB)));
                user.setGender(cursor.getString(cursor.getColumnIndex(COLUMN_GENDER)));
                user.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_PHONE_NUMBER)));
                userList.add(user);
         //       users.add(user);
            } while (cursor.moveToNext());
        }
        return userList;
        //        return users;
    }

    public boolean checkValidUser ( String username) {
        openDB();
        String selection = COLUMN_USERNAME + " = ? ";
        String[] selectionArgss = {username};

        Cursor cursor = sqLiteDatabase.query(TABLE_USER,null,selection,
                selectionArgss,null,null,null);

        int getCursorCount = cursor.getCount();
        cursor.close();
        sqLiteDatabase.close();

        if ( getCursorCount > 0) {
            return true;
        }
        return false;
    }

    public static String getUserId(){
        return userId;
    }

    public boolean checkLogin(String username, String password) {
        openDB();
        String selection = COLUMN_USERNAME + " = ? " + " AND "
                + COLUMN_PASSWORD + " = ? ";

        String column[] = {COLUMN_USER_ID};
        String[] selectionArgs = {username,password};

        Cursor cursor = sqLiteDatabase.query(TABLE_USER,column,selection
                ,selectionArgs, null,null,null);

        int getCount = cursor.getCount();
        if (cursor.moveToFirst()){
            userId = cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID));
        }

        cursor.close();
        sqLiteDatabase.close();

        if ( getCount > 0) {
            return true;
        }

        return false;
    }
}
