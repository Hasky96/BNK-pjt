package com.example.carpoolapp.model;

import com.google.gson.annotations.SerializedName;

public class CarpoolMapRequest {
    private String query;

    public CarpoolMapRequest(String query) {
        this.query = query;
    }
}
