package com.example.carpoolapp.ui.carpool;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.app.usage.NetworkStats;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
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
import android.widget.Toast;

import com.example.carpoolapp.R;
import com.example.carpoolapp.model.CarpoolDetailRes;
import com.example.carpoolapp.model.CarpoolRequest;
import com.example.carpoolapp.model.CarpoolResponse;
import com.example.carpoolapp.model.CommonResponse;
import com.example.carpoolapp.server.Retrofit_client;
import com.example.carpoolapp.server.Retrofit_interface;
import com.example.carpoolapp.util.NetworkStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CarpoolRegisterActivity extends AppCompatActivity {


	TextView tvDepartureTime, tvDepartureDate, tvLocation, tvchkDriver;
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
	int mhourOfDay, mminute;
	CarpoolDetailRes cdetail = new CarpoolDetailRes();
	ActivityResultLauncher<Intent> addrActivityResultLauncher;
	// ?????? ???????????? ?????? requestCode
	private static final int SEARCH_ADDRESS_ACTIVITY = 10000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_carpool_register);

		overridePendingTransition(R.anim.slide_page_in,R.anim.slide_page_out);

		preferences = getSharedPreferences("User", Context.MODE_PRIVATE);
		Authorization = preferences.getString("Authorization", null);

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
		tvLocation = findViewById(R.id.tvLocation);
		tvchkDriver = findViewById(R.id.tvchkDriver);

		edtLocation.clearFocus();

		arrayAdapter = ArrayAdapter.createFromResource(this, R.array.spPersonamount, android.R.layout.simple_spinner_item);
		spPersonamount.setAdapter(arrayAdapter);

		Log.d(">>>", preferences.getString("userCarNo", null));
		if( preferences.getString("userCarNo", null).equals("")){
			tvchkDriver.setVisibility(View.INVISIBLE);
			chkDriver.setVisibility(View.INVISIBLE);
		}

		Intent intent = getIntent();
		if (intent.getStringExtra("from") != null && intent.getStringExtra("from").equals("detail")){

			btnCarpoolRegister.setText("??????");
			btnCarpoolRegister.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#B7A997")));

			cdetail = (CarpoolDetailRes) intent.getSerializableExtra("cdetail");

			if(cdetail.isType()){
				rdoBackHome.setChecked(true);
				tvLocation.setText("?????????");
			}else{
				rdoGoWork.setChecked(true);
				tvLocation.setText("?????????");
			}

			tvDepartureDate.setText(cdetail.getTime().split("T")[0]);
			tvDepartureTime.setText(cdetail.getTime().split("T")[1].substring(0,5));
			edtLocation.setText(cdetail.getLocation());
			spPersonamount.setSelection(arrayAdapter.getPosition(cdetail.getQuota()+""));
			edtInfo.setText(cdetail.getInfo());

			if( cdetail.getDriverNo() == 0){
				chkDriver.setChecked(false);
			}else{
				chkDriver.setChecked(true);
			}
			cdetail.setCarpoolNo(cdetail.getCarpoolNo());
		}

		rdoGoWork.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				tvLocation.setText("?????????");
			}
		});

		rdoBackHome.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				tvLocation.setText("?????????");
			}
		});


		Calendar c = Calendar.getInstance();
		int mYear = c.get(Calendar.YEAR);
		int mMonth = c.get(Calendar.MONTH);
		int mDay = c.get(Calendar.DAY_OF_MONTH);

		DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
				String from = year + "-" + String.format("%02d", month + 1) + "-" + String.format("%02d", dayOfMonth);
				tvDepartureDate.setText(from);
			}
		}, mYear, mMonth, mDay);

		datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());

		tvDepartureDate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (tvDepartureDate.isClickable()) {
					datePickerDialog.show();
				}
			}
		});

		LocalTime now = LocalTime.now();
		mhourOfDay = now.getHour();
		Log.d(">>",mhourOfDay+"");
		mminute = now.getMinute();

		tvDepartureTime.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				TimePickerDialog dialog = new TimePickerDialog(CarpoolRegisterActivity.this,
						android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
						new TimePickerDialog.OnTimeSetListener() {
							@Override
							public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
								if (flag == 0) {
									tvDepartureTime.setText(String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute));
									mhourOfDay = hourOfDay;
									mminute = minute;
								}
							}
						}, mhourOfDay, mminute, true
				);
				dialog.setTitle("??????");
				dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
				dialog.show();
			}
		});

		addrActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
			@Override
			public void onActivityResult(ActivityResult result) {
				if (result.getResultCode() == RESULT_OK) {
//					Log.d(">>","?????? ?????? ??????" + result.getData().getStringExtra("data"));
					edtLocation.setText(result.getData().getStringExtra("data"));
				}

			}
		});


		edtLocation.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Log.i("?????????????????????", "??????????????? ??????");
				int status = NetworkStatus.getConnectivityStatus(getApplicationContext());
				if(status == NetworkStatus.TYPE_MOBILE || status == NetworkStatus.TYPE_WIFI) {

					Log.i("?????????????????????", "??????????????? ??????");
					Intent intentaddr = new Intent(getApplicationContext(), AddressApiActivity.class);
					// ???????????? ??????????????? ?????????
					overridePendingTransition(0, 0);
					// ????????????
					addrActivityResultLauncher.launch(intentaddr);

				}else {
					Toast.makeText(getApplicationContext(), "????????? ????????? ??????????????????.", Toast.LENGTH_SHORT).show();
				}
			}
		});

		btnCarpoolRegister.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				String strCarpoolTime = tvDepartureDate.getText().toString() + "T" + tvDepartureTime.getText().toString();

				CarpoolRequest carpoolRequest = new CarpoolRequest();

				carpoolType = rdoGrouptoggle.getCheckedRadioButtonId() == R.id.rdoBackHome ? true : false;
				carpoolRequest.setCarpoolType(carpoolType);
				carpoolRequest.setCarpoolWriter(preferences.getInt("userNo", 0));
				carpoolRequest.setCarpoolTime(strCarpoolTime);
				carpoolRequest.setCarpoolLocation(edtLocation.getText().toString());
				carpoolRequest.setCarpoolQuota(Integer.parseInt(spPersonamount.getSelectedItem().toString()));
				carpoolRequest.setCarpoolFee(0);
				carpoolRequest.setCarpoolInfo(edtInfo.getText().toString());

				if (chkDriver.isChecked()) {
					Log.d("register", "" + chkDriver.isChecked() + preferences.getInt("userNo",123));
					carpoolRequest.setCarpoolDriver(preferences.getInt("userNo",0));
				} else {
					Log.d("register", "" + chkDriver.isChecked());
					// ????????? ?????? ?????? ?????? userNo 0
					carpoolRequest.setCarpoolDriver(0);
				}

				// ?????? ???????????? ????????? ????????? ????????? ??????
				carpoolService = Retrofit_client.getApiService();
				if( btnCarpoolRegister.getText().equals("??????")){
					Call<CarpoolResponse> call = carpoolService.insertCarpool(Authorization, carpoolRequest);
					call.enqueue(new Callback<CarpoolResponse>() {
						@Override
						public void onResponse(Call<CarpoolResponse> call, Response<CarpoolResponse> response) {
							Log.d(">>", "insert success");
							finish();
						}

						@Override
						public void onFailure(Call<CarpoolResponse> call, Throwable t) {
							Log.d(">>", "fail");
						}
					});
				}else if( btnCarpoolRegister.getText().equals("??????")){
					Call<CommonResponse> callupdate = carpoolService.updateCarpool(Authorization,cdetail.getCarpoolNo(), carpoolRequest);
					callupdate.enqueue(new Callback<CommonResponse>() {
						@Override
						public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
							// ?????? ????????? ??? ?????? ????????? ????????? ??????
							// ?????? ????????? ?????? ???????????? ?????? ??????????????? ??????????????? load????????? ??????
							setResult(807);
							finish();
						}
						@Override
						public void onFailure(Call<CommonResponse> call, Throwable t) {
						}
					});
				}
			}
		});



	}


}