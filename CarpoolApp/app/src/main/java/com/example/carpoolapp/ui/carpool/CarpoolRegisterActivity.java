package com.example.carpoolapp.ui.carpool;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.carpoolapp.R;

public class CarpoolRegisterActivity extends AppCompatActivity {

    Spinner spPersonamount;
    private ArrayAdapter arrayAdapter;
    int flag;
    TextView tvDepartureTime;
    Button btnCarpoolRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carpool_register);

        spPersonamount = findViewById(R.id.spPersonamount);
        tvDepartureTime = findViewById(R.id.tvDepartureTime);
        btnCarpoolRegister = findViewById(R.id.btnCarpoolRegister);

        arrayAdapter = ArrayAdapter.createFromResource(this, R.array.spPersonamount, android.R.layout.simple_spinner_item);
        spPersonamount.setAdapter(arrayAdapter);

        TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {

            @Override

            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                if(flag == 0) {
                    tvDepartureTime.setText(hourOfDay + ":" + minute);
                } else if (flag == 1){
                    tvDepartureTime.setText(hourOfDay + ":" + minute);
                }

            }

        };

        tvDepartureTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog dialog = new TimePickerDialog(CarpoolRegisterActivity.this,android.R.style.Theme_Holo_Light_Dialog_NoActionBar, listener, 15, 24, false);
                dialog.setTitle("시간");
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();
            }
        });

        btnCarpoolRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });




    }


}