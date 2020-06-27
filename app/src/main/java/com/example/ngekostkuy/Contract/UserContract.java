package com.example.ngekostkuy.Contract;

import android.provider.BaseColumns;

public class UserContract {
    private UserContract(){

    }
    public static final class UserEntry implements BaseColumns{
        public static final String TABLE_USER = "MasterUser";
        public static final String COLUMN_USERNAME = "Username";
        public static final String COLUMN_USER_ID = "UserId";
        public static final String COLUMN_PASSWORD = "Password";
        public static final String COLUMN_PHONE_NUMBER= "PhoneNumber";
        public static final String COLUMN_GENDER = "Gender";
        public static final String COLUMN_DOB = "DateofBirth";
    }
}
