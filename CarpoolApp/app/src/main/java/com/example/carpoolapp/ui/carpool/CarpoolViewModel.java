package com.example.carpoolapp.ui.carpool;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CarpoolViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public CarpoolViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is carpool fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}