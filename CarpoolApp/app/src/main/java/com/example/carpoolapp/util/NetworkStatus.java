package com.example.carpoolapp.util;

import android.content.Context;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkStatus {

		// 연결 상태를 정의
		public static final int TYPE_WIFI = 1;
		public static final int TYPE_MOBILE = 2;
		public static final int TYPE_NOT_CONNECTED = 3;

		public static int getConnectivityStatus(Context context){
			ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

			// 권한 필요
			NetworkInfo networkInfo = manager.getActiveNetworkInfo();
			if(networkInfo != null){
				// 3G나 LTE로 연결되었는지, 와이파이로 연결되었는지 구분
				int type = networkInfo.getType();
				// ConnectivityManager 객체의 getActiveNetworkInfo 메소드를 호출하면 NetworkInfo 객체가 반환되고 그 안에 상태 정보가 있음
				if(type == ConnectivityManager.TYPE_MOBILE){
					return TYPE_MOBILE;
				}else if(type == ConnectivityManager.TYPE_WIFI){
					return TYPE_WIFI;
				}

			}
			return TYPE_NOT_CONNECTED;

		}
}
