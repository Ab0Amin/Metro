package com.example.metro;

import androidx.appcompat.app.AppCompatActivity;

import com.uber.sdk.android.core.UberSdk;
import com.uber.sdk.android.rides.RideParameters;
import com.uber.sdk.android.rides.RideRequestButtonCallback;
import com.uber.sdk.core.auth.Scope;
import com.uber.sdk.rides.client.ServerTokenSession;
import com.uber.sdk.rides.client.SessionConfiguration;
import com.uber.sdk.android.rides.RideRequestButton;
import com.uber.sdk.rides.client.error.ApiError;

import android.os.Bundle;
import android.widget.RelativeLayout;

import java.util.Arrays;

public class uber_request extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uber_request);


    }
}