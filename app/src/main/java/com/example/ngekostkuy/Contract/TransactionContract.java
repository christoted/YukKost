package com.example.ngekostkuy.Contract;

import android.provider.BaseColumns;

public class TransactionContract {
    private TransactionContract(){

    }
    public static final class TransactionEntry implements BaseColumns {
        public static final String TABLE_BOOKING = "TrBooking";
        public static final String COLUMN_BOOKING_ID   = "BookingId";
        public static final String COLUMN_KOST_NAME    = "KostName";
        public static final String COLUMN_KOST_FACILITY    = "KostFacility";
        public static final String COLUMN_KOST_PRICE   = "KostPrice";
        public static final String COLUMN_KOST_DESC  = "KostDesc";
        public static final String COLUMN_KOST_LONGITUDE  = "KostLongitude";
        public static final String COLUMN_KOST_LATITUDE  = "KostLatitude";
        public static final String COLUMN_KOST_BOOKING_DATE  = "BookingDate";

    }
}
