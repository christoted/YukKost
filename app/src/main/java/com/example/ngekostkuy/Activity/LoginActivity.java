package com.example.ngekostkuy.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ngekostkuy.Database.DatabaseUser;
import com.example.ngekostkuy.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etUsername, etPassword;
    Button btnLogin, btnGoToRegisterPage;
    DatabaseUser databaseUser;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        databaseUser = new DatabaseUser(getApplicationContext());

        sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);

        if (sharedPreferences.getBoolean("logged",false)){
            moveActivity();
        }

        etUsername = findViewById(R.id.etLoginUsername);
        etPassword = findViewById(R.id.etLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnGoToRegisterPage = findViewById(R.id.btnLoginRegister);

        btnGoToRegisterPage.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if ( v == btnGoToRegisterPage) {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        } else if ( v == btnLogin) {
            checkUsername();
        }
    }

    private void checkUsername() {
        boolean isValid = true;
        if (isEmpty(etUsername)){
            etUsername.setError("This input must be filled");
            // Save userId Info
            String userId = databaseUser.getUserId();
            sharedPreferences.edit().putString("userId",userId).apply();
            sharedPreferences.edit().putBoolean("logged",true).apply();

            isValid = false;
        }
        if (isEmpty(etPassword)){
            etPassword.setError("This input must be filled");
            isValid = false;
        }

        if (isValid == true) {

            String usrnm = etUsername.getText().toString().trim();
            String pswrd = etPassword.getText().toString().trim();


            if (databaseUser.checkLogin(usrnm,pswrd)){

                String userId = databaseUser.getUserId();
                sharedPreferences.edit().putString("userId",userId).apply();
                sharedPreferences.edit().putBoolean("logged",true).apply();
                Log.d("LoginActivity.this", "user : " + usrnm + " password : " + pswrd);
                moveActivity();
                return;
            }
            Toast toast = Toast.makeText(getApplicationContext(), "Username or Password invalid", Toast.LENGTH_SHORT);
        //    Log.d("LoginActivity.this", "user : " + usrnm + " password : " + pswrd);
            toast.show();
        }
    }

    boolean isEmpty(EditText text){
        String checktext = text.getText().toString();
        return TextUtils.isEmpty(checktext);
    }

    private void moveActivity(){
        Intent intent = new Intent(LoginActivity.this, KostListActivity.class);
        startActivity(intent);
    }

//    private void checkUser() {
//        String username = etUsername.getText().toString().trim();
//        String password = etPassword.getText().toString().trim();
//
//        boolean isValid = true;
//        if (username.isEmpty()){
//            etUsername.setError("This input must be filled");
//            isValid = false;
//        } else if ( password.isEmpty()) {
//            etPassword.setError("Isi woy");
//            isValid = false;
//        }
//
//        if ( isValid == true) {
//            if ( databaseUser.checkLogin(username,password)) {
//                String userId = databaseUser.getUserId();
//                Intent intent = new Intent(LoginActivity.this, KostListActivity.class);
//                startActivity(intent);
//                return;
//            }
//        }
//    }
}
