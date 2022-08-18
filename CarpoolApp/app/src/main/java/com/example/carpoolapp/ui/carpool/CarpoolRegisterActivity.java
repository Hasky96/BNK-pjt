package com.example.carpoolapp.ui.carpool;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.carpoolapp.R;
import com.example.carpoolapp.model.CarpoolRequest;
import com.example.carpoolapp.model.CarpoolResponse;
import com.example.carpoolapp.server.Retrofit_client;
import com.example.carpoolapp.server.Retrofit_interface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CarpoolRegisterActivity extends AppCompatActivity {


    TextView tvDepartureTime, tvDepartureDate;
    EditText edtLocation, edtFee, edtInfo;
    CheckBox chkDriver;
    Button btnCarpoolRegister;
    RadioButton rdoGoWork, rdoBackHome;
    RadioGroup rdoGrouptoggle;
    Spinner spPersonamount;
    ArrayAdapter arrayAdapter;
    int flag;
    private Retrofit_interface carpoolService;
    boolean carpoolType = false;
    private SharedPreferences preferences;
    private String Authorization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carpool_register);

        preferences = getSharedPreferences("User", Context.MODE_PRIVATE);
        Authorization = preferences.getString("Authorization",null);

        spPersonamount = findViewById(R.id.spPersonamount);
        tvDepartureTime = findViewById(R.id.tvDepartureTime);
        btnCarpoolRegister = findViewById(R.id.btnCarpoolRegister);
        tvDepartureDate = findViewById(R.id.tvDepartureDate);
        edtLocation = findViewById(R.id.edtLocation);
        edtInfo = findViewById(R.id.edtInfo);
        chkDriver = findViewById(R.id.chkDriver);
        rdoGoWork = findViewById(R.id.rdoGoWork);
        rdoBackHome = findViewById(R.id.rdoBackHome);
        rdoGrouptoggle = findViewById(R.id.rdoGrouptoggle);

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

        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String from = year +"-" +  String.format("%02d",month+1) +"-" + String.format("%02d",dayOfMonth);
                tvDepartureDate.setText(from);
            }
        }, mYear, mMonth, mDay);

        tvDepartureDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvDepartureDate.isClickable()) {
                    datePickerDialog.show();
                }
            }
        });

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

                String strCarpoolTime = tvDepartureDate.getText().toString() + "T" + tvDepartureTime.getText().toString();

                CarpoolRequest carpoolRequest = new CarpoolRequest();
                carpoolRequest.setCarpoolWriter(2);
                carpoolRequest.setCarpoolTime(strCarpoolTime);
                Log.d(">>", strCarpoolTime);
                carpoolRequest.setCarpoolLocation(edtLocation.getText().toString());
                carpoolRequest.setCarpoolQuota(Integer.parseInt(spPersonamount.getSelectedItem().toString()));
                carpoolRequest.setCarpoolFee(123);
                carpoolRequest.setCarpoolInfo(edtInfo.getText().toString());
                carpoolType = rdoGrouptoggle.getCheckedRadioButtonId() == R.id.rdoBackHome ? true : false;
                carpoolRequest.setCarpoolType(carpoolType);
                Log.d(">>", carpoolType+"");
                if(chkDriver.isChecked()){
                    int userNo = preferences.getInt("userNo",-1);
                    carpoolRequest.setCarpoolDriver(2);
                }
                else{
                    // 운전자 요청 글일 경우 userNo -1
                    carpoolRequest.setCarpoolDriver(-1);
                }

                carpoolService = Retrofit_client.getApiService();
                Call<CarpoolResponse> call = carpoolService.insertCarpool(Authorization,carpoolRequest);
                call.enqueue(new Callback<CarpoolResponse>() {
                    @Override
                    public void onResponse(Call<CarpoolResponse> call, Response<CarpoolResponse> response) {
                        Log.d(">>", "success");
                    }

                    @Override
                    public void onFailure(Call<CarpoolResponse> call, Throwable t) {
                        Log.d(">>", "fail");
                    }
                });

            }
        });






    }


}