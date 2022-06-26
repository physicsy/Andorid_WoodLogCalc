package com.example.bajrangbalitimber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomePage extends AppCompatActivity {

    private Button WoodLog;
    private Button SizeWood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_home);

        WoodLog = (Button)findViewById(R.id.woodLogBtn);
        SizeWood = (Button)findViewById(R.id.sizeWoodbtn);


        WoodLog.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, WoodLog.class);
                startActivity(intent);
                //validate(Password.getText().toString());
            }
        });

        SizeWood.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, SizeWood.class);
                startActivity(intent);
                //validate(Password.getText().toString());
            }
        });
    }
}