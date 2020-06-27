package com.example.ngekostkuy.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import static com.example.ngekostkuy.Contract.TransactionContract.TransactionEntry.COLUMN_BOOKING_ID;
import static com.example.ngekostkuy.Contract.TransactionContract.TransactionEntry.COLUMN_KOST_BOOKING_DATE;
import static com.example.ngekostkuy.Contract.TransactionContract.TransactionEntry.COLUMN_KOST_DESC;
import static com.example.ngekostkuy.Contract.TransactionContract.TransactionEntry.COLUMN_KOST_FACILITY;
import static com.example.ngekostkuy.Contract.TransactionContract.TransactionEntry.COLUMN_KOST_LATITUDE;
import static com.example.ngekostkuy.Contract.TransactionContract.TransactionEntry.COLUMN_KOST_LONGITUDE;
import static com.example.ngekostkuy.Contract.TransactionContract.TransactionEntry.COLUMN_KOST_NAME;
import static com.example.ngekostkuy.Contract.TransactionContract.TransactionEntry.COLUMN_KOST_PRICE;
import static com.example.ngekostkuy.Contract.TransactionContract.TransactionEntry.TABLE_BOOKING;
import static com.example.ngekostkuy.Contract.UserContract.UserEntry.COLUMN_DOB;
import static com.example.ngekostkuy.Contract.UserContract.UserEntry.COLUMN_GENDER;
import static com.example.ngekostkuy.Contract.UserContract.UserEntry.COLUMN_PASSWORD;
import static com.example.ngekostkuy.Contract.UserContract.UserEntry.COLUMN_PHONE_NUMBER;
import static com.example.ngekostkuy.Contract.UserContract.UserEntry.COLUMN_USERNAME;
import static com.example.ngekostkuy.Contract.UserContract.UserEntry.COLUMN_USER_ID;
import static com.example.ngekostkuy.Contract.UserContract.UserEntry.TABLE_USER;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "kost.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE_USER = " CREATE TABLE IF NOT EXISTS " + TABLE_USER + "(" +
        COLUMN_USER_ID + " TEXT PRIMARY KEY, " +
        COLUMN_USERNAME + " TEXT, " +
        COLUMN_GENDER + " TEXT, " +
        COLUMN_PHONE_NUMBER + " TEXT, " +
        COLUMN_PASSWORD + " TEXT, " +
        COLUMN_DOB + " TEXT);";

    private static final String CREATE_TABLE_BOOKING = " CREATE TABLE IF NOT EXISTS " + TABLE_BOOKING + "(" +
        COLUMN_BOOKING_ID + " TEXT PRIMARY KEY, " +
        COLUMN_KOST_NAME + " TEXT, " +
        COLUMN_KOST_FACILITY + " TEXT, " +
        COLUMN_KOST_PRICE + " TEXT, " +
        COLUMN_KOST_DESC + " TEXT, " +
        COLUMN_KOST_LATITUDE + " TEXT, "+
        COLUMN_KOST_LONGITUDE + " TEXT, " +
        COLUMN_KOST_BOOKING_DATE + " TEXT," +
        COLUMN_USER_ID + " TEXT, " + " FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + ")" + ");";

    private static final String SQL_DELETE_ENTRIES_USER =
            "DROP TABLE IF EXISTS " + TABLE_USER;

    private static final String SQL_DELETE_ENTRIES_BOOKING_TRANSACTION =
            "DROP TABLE IF EXISTS " + TABLE_BOOKING;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_BOOKING);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES_USER);
        db.execSQL(SQL_DELETE_ENTRIES_BOOKING_TRANSACTION);
        onCreate(db);
    }
}
