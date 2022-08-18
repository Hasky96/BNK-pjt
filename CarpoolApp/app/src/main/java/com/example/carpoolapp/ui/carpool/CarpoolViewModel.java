package com.example.carpoolapp.ui.carpool;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CarpoolViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<String> date;
    private final MutableLiveData<String> time;
    private final MutableLiveData<String> location;
    private final MutableLiveData<Integer> count;
    private final MutableLiveData<String> info;
    private final MutableLiveData<Integer> chkdriver;

    public CarpoolViewModel() {
        mText = new MutableLiveData<>();
        date = new MutableLiveData<>();
        time = new MutableLiveData<>();
        location = new MutableLiveData<>();
        count = new MutableLiveData<>();
        info = new MutableLiveData<>();
        chkdriver = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }


    public LiveData<String> getDate() {
        return date;
    }

    public LiveData<String> getTime() {
        return time;
    }

    public LiveData<String> getLocation() {
        return location;
    }

    public LiveData<Integer> getCount() {
        return count;
    }

    public LiveData<String> getInfo() {
        return info;
    }

    public LiveData<Integer> getChkdriver() {
        return chkdriver;
    }

}