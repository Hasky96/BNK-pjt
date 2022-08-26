package com.example.carpoolapp.ui.myReservation;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.carpoolapp.R;
import com.example.carpoolapp.databinding.FragmentMyreservationBinding;
import com.example.carpoolapp.model.CarpoolAllDetailRes;
import com.example.carpoolapp.ui.carpool.CarpoolViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class myReservationFragment extends Fragment {

    private SharedPreferences preferences;
    private CarpoolViewModel carpoolViewModel;
    private String userId,from;
    Button btndialog;

    private FragmentMyreservationBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(">>nav","onCreate");
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myReservationViewModel homeViewModel =
                new ViewModelProvider(this).get(myReservationViewModel.class);

        binding = FragmentMyreservationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        carpoolViewModel = new ViewModelProvider(this).get(CarpoolViewModel.class);


        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                from = year + "-" + String.format("%02d", month + 1) + "-" + String.format("%02d", dayOfMonth);
                Log.d("jjk","date" +from);
            }
        }, mYear, mMonth, mDay);


        binding.tvReserveDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.tvReserveDate.isClickable()) {
                    datePickerDialog.show();
                    btndialog = datePickerDialog.getButton(datePickerDialog.BUTTON_POSITIVE);
                    btndialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            binding.tvReserveDate.setText(from);
                            carpoolViewModel.loadCarpools();
                            datePickerDialog.cancel();

                        }
                    });
                }
            }
        });



        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        carpoolViewModel.loadCarpools();

        binding.constraintLayoutGo.setVisibility(View.INVISIBLE);
        binding.constraintLayoutOut.setVisibility(View.INVISIBLE);
        binding.tvNoReserve.setVisibility(View.VISIBLE);

        binding.tvReserveGoDepartLoc.setSelected(true);
        binding.tvReserveGoDestinationLoc.setSelected(true);
        binding.tvReserveOutDepartLoc.setSelected(true);
        binding.tvReserveOutDestinationLoc.setSelected(true);

        preferences= getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
        userId=preferences.getString("userId",null);

        long now=System.currentTimeMillis();
        Date date=new Date(now);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String carpoolToday=sdf.format(date);
        binding.tvReserveDate.setText(carpoolToday);

        carpoolObserve();

        Log.d(">>nav","onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(">>nav","onResume");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void carpoolObserve(){
        carpoolViewModel.getCarpools().observe(getViewLifecycleOwner(),carpoolList ->{

            List<CarpoolAllDetailRes> list = new ArrayList<>();
            for (CarpoolAllDetailRes temp: carpoolList) {
                if(!temp.isType()) list.add(temp);
            }
            for (CarpoolAllDetailRes carpool:carpoolList){
                String carpoolTime=carpool.getTime(); //카풀시간
                String[] datetime=carpoolTime.split("T");
                String today=datetime[0];
                String time=datetime[1].substring(0,5);

                if(today!=null&binding.tvReserveDate.getText()!=null&&today.equals(binding.tvReserveDate.getText())){
                    String occupantList=carpool.getOccupants().substring(1,carpool.getOccupants().length()-1);
                    String[] occupants=occupantList.split(", ");
                    List<String> occuList = new ArrayList<>(Arrays.asList(occupants));

                    if(occuList.contains(userId)){
                        //퇴근
                        if (carpool.isType()){
                            binding.constraintLayoutOut.setVisibility(View.VISIBLE);
                            binding.tvNoReserve.setVisibility(View.INVISIBLE);
                            binding.tvReserveOutTime.setText(time);
                            binding.tvReserveOutDestinationLoc.setText(carpool.getLocation());
                            binding.tvReserveOutAll.setText(""+carpool.getQuota());
                            binding.tvReserveOutCount.setText(""+occupants.length);
                            binding.tvReserveOutButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("carpoolNo", carpool.getCarpoolNo());
                                    Navigation.findNavController(view).navigate(R.id.action_navigation_myReservation_to_carpoolDetailFragment2, bundle);
                                }
                            });
                        }
                        //출근
                        else {
                            binding.constraintLayoutGo.setVisibility(View.VISIBLE);
                            binding.tvNoReserve.setVisibility(View.INVISIBLE);
                            binding.tvReserveGoTime.setText(time);
                            binding.tvReserveGoDepartLoc.setText(carpool.getLocation());
                            binding.tvReserveGoAll.setText(""+carpool.getQuota());
                            binding.tvReserveGoCount.setText(""+occupants.length);
                            binding.tvReserveGoButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("carpoolNo", carpool.getCarpoolNo());
                                    Navigation.findNavController(view).navigate(R.id.action_navigation_myReservation_to_carpoolDetailFragment2, bundle);
                                }
                            });
                        }
                    }
//                    }

                }
            }

        } );
    }
}