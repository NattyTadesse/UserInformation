package com.ahadu.userinformation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    FloatingActionButton fab;
    List<UserModel> mData;
    SharedPreferences preferences;
    DatabaseHelper databaseHelper = new DatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getSharedPreferences("login_preference",MODE_PRIVATE);
        recyclerView = findViewById(R.id.recyclerview);
        fab = findViewById(R.id.fab_logout);
        mData = new ArrayList<>();
        Intent intent= getIntent();


        Cursor cursor = databaseHelper.getDataForRecycler();
        if(cursor.getCount() == 0){
            //show message
            showMessage("Error","Nothing is found");
            return;
        }
        UserModel s;
        while (cursor.moveToNext()){

            s = new UserModel(cursor.getString(0).toString());
            mData.add(s);

        }
        recyclerViewAdapter = new RecyclerViewAdapter(this,mData);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toExit();
            }
        });

    }
    public void showMessage(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public void toExit(){
        Intent intent = new Intent(getApplicationContext(),Login.class);
        preferences.edit().putBoolean("isLogged",false).apply();
        startActivity(intent);

    }


}
