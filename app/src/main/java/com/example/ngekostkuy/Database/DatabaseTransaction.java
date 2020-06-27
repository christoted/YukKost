package com.example.ngekostkuy.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ngekostkuy.Model.BookingTransaction;
import com.example.ngekostkuy.Model.User;

import java.util.ArrayList;
import java.util.List;

import static com.example.ngekostkuy.Contract.TransactionContract.TransactionEntry.COLUMN_BOOKING_ID;
import static com.example.ngekostkuy.Contract.TransactionContract.TransactionEntry.COLUMN_KOST_BOOKING_DATE;
import static com.example.ngekostkuy.Contract.TransactionContract.TransactionEntry.COLUMN_KOST_DESC;
import static com.example.ngekostkuy.Contract.TransactionContract.TransactionEntry.COLUMN_KOST_FACILITY;
import static com.example.ngekostkuy.Contract.TransactionContract.TransactionEntry.COLUMN_KOST_LATITUDE;
import static com.example.ngekostkuy.Contract.TransactionContract.TransactionEntry.COLUMN_KOST_LONGITUDE;
import static com.example.ngekostkuy.Contract.TransactionContract.TransactionEntry.COLUMN_KOST_NAME;
import static com.example.ngekostkuy.Contract.TransactionContract.TransactionEntry.COLUMN_KOST_PRICE;
import static com.example.ngekostkuy.Contract.TransactionContract.TransactionEntry.TABLE_BOOKING;
import static com.example.ngekostkuy.Contract.UserContract.UserEntry.COLUMN_USER_ID;
import static com.example.ngekostkuy.Storage.StorageBookingTransaction.bookingTransactions;

public class DatabaseTransaction {
    private DatabaseHelper databaseHelper;
    private Context context;
    private SQLiteDatabase sqLiteDatabase;

    public DatabaseTransaction(Context context) {
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
    }

    public void openDB(){
        sqLiteDatabase = databaseHelper.getWritableDatabase();
    }

    public void insertBooking(BookingTransaction bookingTransaction){
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_BOOKING_ID,bookingTransaction.getBookingId());
        contentValues.put(COLUMN_USER_ID,bookingTransaction.getUserId());
        contentValues.put(COLUMN_KOST_NAME,bookingTransaction.getKostName());
        contentValues.put(COLUMN_KOST_FACILITY,bookingTransaction.getKostFacility());
        contentValues.put(COLUMN_KOST_PRICE,bookingTransaction.getKostPrice());
        contentValues.put(COLUMN_KOST_DESC,bookingTransaction.getKostDesc());
        contentValues.put(COLUMN_KOST_LONGITUDE,bookingTransaction.getKostLongitude());
        contentValues.put(COLUMN_KOST_LATITUDE,bookingTransaction.getKostLatitude());
        contentValues.put(COLUMN_KOST_BOOKING_DATE,bookingTransaction.getBookingDate());

        sqLiteDatabase.insert(TABLE_BOOKING,null,contentValues);
        sqLiteDatabase.close();
    }

    public void deleteBooking(String BookingId){
        openDB();
        sqLiteDatabase.delete(TABLE_BOOKING,COLUMN_BOOKING_ID +  " = ? ",new String[]{BookingId});
        sqLiteDatabase.close();
    }


    public ArrayList<BookingTransaction> filterListBooking(String userId) {
        openDB();
        ArrayList<BookingTransaction> bookingTransactionss = new ArrayList<>();

        String selection = COLUMN_USER_ID + " = ? ";
        String[] selectionArgs = {userId};
        Cursor cursor =
                sqLiteDatabase.query(TABLE_BOOKING,null,
                        selection,selectionArgs,null,null,null);

        if ( cursor.moveToFirst()) {
            do{
                BookingTransaction bookingTransaction = new BookingTransaction();
                bookingTransaction.setBookingId(cursor.getString(cursor.getColumnIndex(COLUMN_BOOKING_ID)));
                bookingTransaction.setUserId(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID)));
                bookingTransaction.setKostName(cursor.getString(cursor.getColumnIndex(COLUMN_KOST_NAME)));
                bookingTransaction.setKostFacility(cursor.getString(cursor.getColumnIndex(COLUMN_KOST_FACILITY)));
                bookingTransaction.setKostPrice(cursor.getString(cursor.getColumnIndex(COLUMN_KOST_PRICE)));
                bookingTransaction.setKostDesc(cursor.getString(cursor.getColumnIndex(COLUMN_KOST_DESC)));
                bookingTransaction.setKostLatitude(cursor.getString(cursor.getColumnIndex(COLUMN_KOST_LATITUDE)));
                bookingTransaction.setKostLongitude(cursor.getString(cursor.getColumnIndex(COLUMN_KOST_LONGITUDE)));
                bookingTransaction.setBookingDate(cursor.getString(cursor.getColumnIndex(COLUMN_KOST_BOOKING_DATE)));

                bookingTransactionss.add(bookingTransaction);
            } while ( cursor.moveToNext());
        }

        cursor.close();
        sqLiteDatabase.close();
        return bookingTransactionss;
    }

    public ArrayList<BookingTransaction> getAllBooking() {
        openDB();
        String SELECT_QUERY = " SELECT * FROM " + TABLE_BOOKING;
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_QUERY,null);
        ArrayList<BookingTransaction> bookingTransactionList = new ArrayList<>();

        if ( cursor.moveToFirst()){
            do{
                BookingTransaction bookingTransaction = new BookingTransaction();
                bookingTransaction.setBookingId(String.valueOf(cursor.getColumnIndex(COLUMN_BOOKING_ID)));
                bookingTransaction.setUserId(String.valueOf(cursor.getColumnIndex(COLUMN_USER_ID)));
                bookingTransaction.setKostName(String.valueOf(cursor.getColumnIndex(COLUMN_KOST_NAME)));
                bookingTransaction.setKostFacility(String.valueOf(cursor.getColumnIndex(COLUMN_KOST_FACILITY)));
                bookingTransaction.setKostPrice(String.valueOf(cursor.getColumnIndex(COLUMN_KOST_PRICE)));
                bookingTransaction.setKostDesc(String.valueOf(cursor.getColumnIndex(COLUMN_KOST_DESC)));
                bookingTransaction.setKostLatitude(String.valueOf(cursor.getColumnIndex(COLUMN_KOST_LATITUDE)));
                bookingTransaction.setKostLongitude(String.valueOf(cursor.getColumnIndex(COLUMN_KOST_LONGITUDE)));
                bookingTransaction.setBookingDate(String.valueOf(cursor.getColumnIndex(COLUMN_KOST_BOOKING_DATE)));

          //      bookingTransactions.add(bookingTransaction);
                bookingTransactionList.add(bookingTransaction);
            } while ( cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
//        return bookingTransactions;
        return bookingTransactionList;
    }

    public boolean checkBook ( String UserId, String KostName, String BookDate) {
        openDB();

        String selection = COLUMN_USER_ID + " = ? " + " AND " + COLUMN_KOST_NAME + " = ? " + " AND " + COLUMN_KOST_BOOKING_DATE + " = ? ";
        String[] selectionArgs = {UserId,KostName,BookDate};

        Cursor cursor = sqLiteDatabase.query(TABLE_BOOKING,null,selection,selectionArgs,null,null,null);

        int getCursorCount = cursor.getCount();
        cursor.close();
        sqLiteDatabase.close();

        if ( getCursorCount > 0) {
            return true;
        }


        return false;
    }
}
