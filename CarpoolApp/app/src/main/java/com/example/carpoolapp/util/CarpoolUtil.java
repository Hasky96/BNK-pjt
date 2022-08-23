package com.example.carpoolapp.util;

import android.util.Log;

import com.example.carpoolapp.model.CarpoolDetailRes;

public class CarpoolUtil {

	public static boolean isUserInCarpool(CarpoolDetailRes carpoolDetailRes, String userId){

		String trimOccupants = carpoolDetailRes.getOccupants().substring(1,carpoolDetailRes.getOccupants().length()-1);
		for ( String occu : trimOccupants.split(", ")) {
			if( occu.equals(userId) ){
				Log.d(">>", "util "+ occu + "// " + userId );
				return true;
			}
		}

		return false;
	}
}
