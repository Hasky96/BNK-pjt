package com.example.carpoolapp.ui.splash;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.carpoolapp.R;
import com.example.carpoolapp.databinding.ActivitySplashBinding;
import com.example.carpoolapp.ui.user.LoginFragment;

public class SplashActivity extends AppCompatActivity {
    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String status=getIntent().getStringExtra("status");

        overridePendingTransition(R.anim.slide_page_back_down,R.anim.slide_page_back_up);

        if(status!=null && status.equals("logout")) {
            LoginFragment fragment=new LoginFragment();
            Bundle bundle=new Bundle();
            fragment.setArguments(bundle);
            //프래그먼트 이동
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.ConstraintFragment, fragment).commit();
        }
            else {
            SplashFragment fragment=new SplashFragment();
            Bundle bundle=new Bundle();
            fragment.setArguments(bundle);
            //프래그먼트 이동
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.ConstraintFragment, fragment).commit();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        String status=getIntent().getStringExtra("status");
        if (status!="logout"){
            finish();
        }
    }
}