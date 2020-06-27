package com.example.ngekostkuy.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ngekostkuy.Database.DatabaseUser;
import com.example.ngekostkuy.Model.User;
import com.example.ngekostkuy.R;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etRegUsername,etRegPassword,etRegConfPassword,etRegPhoneNumber;
    TextView textViewDOBRes;
    Button btnDOB,btnRegister;
    RadioGroup rgGender;
    CheckBox checkBoxAgreement;
    Calendar calendar;
    DatePickerDialog datePickerDialog;
    DatabaseUser databaseUser;


    private static final String USERNAME_REGEX = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$).{3,26}$";
    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$).{6,26}$";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        databaseUser = new DatabaseUser(getApplicationContext());

        etRegUsername = findViewById(R.id.etRegUsername);
        etRegPassword = findViewById(R.id.etRegPassowrd);
        etRegConfPassword = findViewById(R.id.etRegConfPassowrd);
        etRegPhoneNumber = findViewById(R.id.etRegPhone);
        etRegUsername = findViewById(R.id.etRegUsername);
        textViewDOBRes = findViewById(R.id.tvDOBRes);
        btnDOB = findViewById(R.id.btnPickDOB);
        btnRegister = findViewById(R.id.btnRegisterPage);
        rgGender = findViewById(R.id.rgGender);
        checkBoxAgreement = findViewById(R.id.cbAgree);

        btnDOB.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if ( v == btnDOB) {
            calendar = Calendar.getInstance();
            int DAY = calendar.get(Calendar.DAY_OF_MONTH);
            int MONTH = calendar.get(Calendar.MONTH);
            int YEAR = calendar.get(Calendar.YEAR);

            datePickerDialog = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    textViewDOBRes.setText(dayOfMonth+"/"+month+"/"+year);
                }
            },DAY,MONTH,YEAR);
            datePickerDialog.show();
        } else if ( v == btnRegister) {
            checkValidate();
        }
    }

    private void checkValidate() {

        boolean isValid = true;

//        if (rgGender.getCheckedRadioButtonId()==-1){
//            isValid = false;
//        }
//
//        if (!checkBoxAgreement.isChecked())
//        {
//            checkBoxAgreement.setError("Must checked");
//            isValid = false;
//        }

        if (!etRegUsername.getText().toString().matches("^(?=.*\\d)(?=.*[a-zA-Z]).{3,25}$")) {
            etRegUsername.setError("Username must between 3 and 25 characters,1 digit and alphabetic");
            isValid = false;

        }

        String pass="";
        if (!etRegPassword.getText().toString().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,}$") ) {
            etRegPassword.setError("Password must contain at least 1 lowercase letter, 1 uppercase letter,1 digit and more than 6 character");
            isValid = false;
        }

//        boolean flag = true;
//        int len = etRegPhoneNumber.getText().toString().length();
//        if (len>6){
//            for (int i =0 ; i<len ; i++){
//                if (!(etRegPhoneNumber.getText().toString().charAt(i)>='0' && etRegPhoneNumber.getText().toString().charAt(i)<='9')){
//                    flag = false;
//                }
//            }
//            if (flag){
//                etRegPhoneNumber.setError(null);
//            }
//            else{
//                etRegPhoneNumber.setError("Phone number must contains digit only");
//                isValid = false;
//            }
//
//        }
//        else {
//            etRegPhoneNumber.setError("Phone number must between 10 and 12 character");
//            isValid = false;
//
//        }

//        if (!etRegConfPassword.getText().toString().matches(pass)){
//            etRegConfPassword.setError("Must same with password");
//            isValid = false;
//        }
//        else {
//            etRegConfPassword.setError(null);
//        }

        if (isValid){
            String cekUser = etRegUsername.getText().toString().trim();

            boolean validUser= true;

            if (databaseUser.checkValidUser(cekUser)) {
                etRegUsername.setError("Username already exists");
                validUser = false;
            }

            if(validUser){

                String userId = "";

                int sz = databaseUser.getAllUser().size();

                if (sz<10){
                    userId = "US00" + sz;
                }
                else if (sz>=10 && sz<=99){
                    userId = "US0" + sz;
                }
                else{
                    userId = "US" + sz;
                }

                Log.d("123123",userId);

                String us = etRegUsername.getText().toString();
                String passwrd =  etRegPassword.getText().toString();
    //            String phone = etRegPhoneNumber.getText().toString();
   //             String birth = textViewDOBRes.getText().toString();

                int genId = rgGender.getCheckedRadioButtonId();
//                RadioButton sexgender = findViewById(genId);
//                String sex = sexgender.getText().toString();

                User user = new User();

                user.setUserId(userId);
                user.setUsername(us);
                user.setPassword(passwrd);
         //       user.setPhoneNumber(phone);
          //      user.setDateofBirth(birth);
          //      user.setGender(sex);

                databaseUser.insertUser(user);

                Log.d("RegisterActivity.this", "user: " + us + " pass: " + passwrd + " id : " + userId + " databaseUserSize : " + sz);
                Toast toast = Toast.makeText(getApplicationContext(),"Register Successful",Toast.LENGTH_SHORT);
                toast.show();
              //  this.finish();
            }
        }

    }


    boolean isEmpty(EditText text){
        String checktext = text.getText().toString();
        return TextUtils.isEmpty(checktext);
    }

//    private void checkValidate() {
//        String regUsername = etRegUsername.getText().toString();
//        String regPassword = etRegPassword.getText().toString();
//        String regConfPassword = etRegConfPassword.getText().toString();
//        String DOB = textViewDOBRes.getText().toString();
//
//        boolean validUser= true;
//
//        if (!regUsername.matches(USERNAME_REGEX)) {
//            etRegUsername.setError("Username 3..25 Characters and " +
//                    "contain at least 1 digit and alphabetic.");
//        } else if (!regPassword.matches(PASSWORD_REGEX)) {
//            etRegPassword.setError("Password at least 8 character,1 lowercase letter, 1 uppercase letter and 1 digit.");
//        }
//
//        else if ( etRegPassword.length() < 6) {
//            etRegPassword.setError("Password min 6 characters");
//        } else if ( !regConfPassword.equals(regPassword)) {
//            etRegConfPassword.setError("Confirm password must same");
//        } else if ( etRegPhoneNumber.length() < 10 || etRegPhoneNumber.length() > 12 ) {
//            etRegPhoneNumber.setError("Phone number between 10..12");
//        } else if ( rgGender.getCheckedRadioButtonId() == -1) {
//            Toast.makeText(this, "No Hard feeling, please choose your gender", Toast.LENGTH_SHORT).show();
//        } else if ( !checkBoxAgreement.isChecked()) {
//            checkBoxAgreement.setError("Please Check the agreement");
//        } else if ( DOB.equals("Your DOB")) {
//            textViewDOBRes.setError("Please choose the date");
//        }
//
//        else {
//            if (databaseUser.checkValidUser(regUsername)) {
//                etRegUsername.setError("Username Already Exists");
//                validUser = false;
//            }
//
//            if (validUser == true) {
//
//                String userId = "";
//                int size = databaseUser.getAllUser().size();
//                if (size<10){
//                    userId = "US00" + size;
//                }
//                else if (size>=10 && size<=99){
//                    userId = "US0" + size;
//                }
//                else{
//                    userId = "US" + size;
//                }
//
//                String username = etRegUsername.getText().toString().trim();
//                String password =  etRegPassword.getText().toString();
//                String phone = etRegPhoneNumber.getText().toString();
//                String birth = textViewDOBRes.getText().toString();
//
//                int genId = rgGender.getCheckedRadioButtonId();
//                RadioButton sexgender = findViewById(genId);
//                String sex = sexgender.getText().toString();
//
//                User user = new User();
//                user.setUserId(userId);
//                user.setUsername(username);
//                user.setPassword(password);
//                user.setPhoneNumber(phone);
//                user.setDateofBirth(birth);
//                user.setGender(sex);
//
//                databaseUser.insertUser(user);
//                Toast toast = Toast.makeText(getApplicationContext(),"Register Successful",Toast.LENGTH_SHORT);
//                toast.show();
//                this.finish();
//            }
//
//        }
//    }
}
