package com.example.carpoolapp.ui.myReservation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class myReservationViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public myReservationViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is myreservation fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}