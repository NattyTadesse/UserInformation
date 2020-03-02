package com.ahadu.userinformation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    EditText username,password;
    Button signup,login;
    String un, pd;
    SharedPreferences sharedPreferences;
    DatabaseHelper databaseHelper = new DatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = findViewById(R.id.login_btn);
        signup = findViewById(R.id.signup_btn);
        username = findViewById(R.id.lg_usrname);
        password = findViewById(R.id.lg_pwd);
        sharedPreferences = getSharedPreferences("login_preference",MODE_PRIVATE);
        un = username.getText().toString();
        pd = password.getText().toString();
        if(sharedPreferences.getBoolean("isLogged",false)){
            toUserPage();
        }
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(auth(username.getText().toString(),password.getText().toString())){
                        sharedPreferences.edit().putBoolean("isLogged",true).apply();
                        toUserPage();
                    }
            }
        });
    }
    public boolean auth(String username, String pwd){

        Cursor res = databaseHelper.getDataForLogin(username);
        String uname= "", pass="";
        if(res.getCount() == 0){
            Toast.makeText(getApplicationContext(),"No such Data",Toast.LENGTH_SHORT).show();
            return false;
        }

        while (res.moveToNext()){
            uname = res.getString(0);
            pass = res.getString(1);

        }
        if(uname.equals(username) && pass.equals(pwd)){
            return true;
        }else {
            Toast.makeText(this, "Incorrect Password or UserName", Toast.LENGTH_SHORT).show();
            return false;
        }

    }
    public void toUserPage(){
        Intent intent= new Intent(this, MainActivity.class);

        startActivity(intent);
    }
}
