package com.ahadu.userinformation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    EditText fname,uname,email,phone_num,password,confirm_pass;
    Spinner gender;
    Button login_btn, registerbtn;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fname = findViewById(R.id.full_name);
        uname = findViewById(R.id.user_name);
        email = findViewById(R.id.email);
        phone_num = findViewById(R.id.phone_number);
        password = findViewById(R.id.password);
        confirm_pass = findViewById(R.id.confirm_password);
        gender = findViewById(R.id.spinner);
        login_btn = findViewById(R.id.login_btn);
        registerbtn = findViewById(R.id.register_btn);
        databaseHelper = new DatabaseHelper(this);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(v.getContext(),Login.class);
                startActivity(intent);
            }
        });
        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert(fname.getText().toString(),uname.getText().toString(),email.getText().toString(),phone_num.getText().toString()
                ,password.getText().toString(),"");
                Intent intent= new Intent(v.getContext(),MainActivity.class);
                intent.putExtra("email",email.getText().toString());
                startActivity(intent);
            }
        });

    }
    public void insert(String f, String u, String m, String p, String pwd, String gender) {

        boolean result = databaseHelper.insert(f,u,m,p,pwd,gender);
        if (result) {
            Toast.makeText(this, "Succesfully inserted", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Error occured", Toast.LENGTH_SHORT).show();
        }

    }

}
