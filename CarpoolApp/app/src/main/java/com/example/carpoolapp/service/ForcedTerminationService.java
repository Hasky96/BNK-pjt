package com.example.carpoolapp.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class ForcedTerminationService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) { //핸들링 하는 부분
        SharedPreferences preferences=getSharedPreferences("User", Context.MODE_PRIVATE);
        boolean autoLogin=preferences.getBoolean("autoLogin",false);
        if (!autoLogin){
            SharedPreferences.Editor editor=preferences.edit();
            editor.clear();
            editor.commit();
        }
        stopSelf(); //서비스 종료
    }
}
