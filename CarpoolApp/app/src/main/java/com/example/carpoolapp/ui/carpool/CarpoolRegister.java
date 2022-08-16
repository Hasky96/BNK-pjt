package com.example.carpoolapp.ui.carpool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.carpoolapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CarpoolRegister extends AppCompatActivity {

    FloatingActionButton flbCarpoolRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carpool_register);

        flbCarpoolRegister = findViewById(R.id.flbCarpoolRegister);

        flbCarpoolRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent carRegister = new Intent();
            }
        });

    }
}