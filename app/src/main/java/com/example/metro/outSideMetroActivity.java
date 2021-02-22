package com.example.metro;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Notification;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.uber.sdk.android.core.UberSdk;
import com.uber.sdk.android.rides.RideParameters;
import com.uber.sdk.android.rides.RideRequestButton;
import com.uber.sdk.android.rides.RideRequestButtonCallback;
import com.uber.sdk.core.auth.Scope;
import com.uber.sdk.rides.client.ServerTokenSession;
import com.uber.sdk.rides.client.SessionConfiguration;
import com.uber.sdk.rides.client.error.ApiError;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.channels.FileLock;
import java.security.Permission;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static android.location.LocationManager.*;

public class outSideMetroActivity extends AppCompatActivity implements LocationListener {
    private TextView CurrentLocation, TargetLocation, NearsetStaion_C, NearsetStaion_T, _1trip, _2trip, _3trip;
    private LocationManager Loc;
    private SharedPreferences pre;
    private Geocoder geocoder;
    private String CurrentLocationName, TargetLocationName;
    private double Currentlat, Currentlong, Targetlat, Targetlong,distance1,distance2;
    private List<Address> CurrentinputAdress, TargetinputAdress;
    private Location CurrentLocation_LC, TargetLocation_LC;
    private MainActivity main = new MainActivity();
    private List<Location> MetroLocations = new ArrayList<>();
    private List<String> MetroStaions = new ArrayList<>();
    private byte index_nearFromCurrent, index_nearFRomTarget;
    private RadioButton Waking, uber;
    private RadioGroup radioGroup;
    private boolean resultData = true;
    private ScrollView dataView;
    MainActivity activity_for_outside;
    private DecimalFormat format;
    private Button result_BT, newTrip, Go1_BT, Go2_BT, MetroTripDetails_BT;
    RideRequestButton _1Uber, _2Uber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_side_metro);
        CurrentLocation = findViewById(R.id.tx_currentLocation);
        TargetLocation = findViewById(R.id.tx_targetLoacation);
        NearsetStaion_C = findViewById(R.id.Tx_nearstStationFromCusrrent);
        NearsetStaion_T = findViewById(R.id.Tx_nearstStationFromTarget);
        Waking = findViewById(R.id.rd_Waking);
        uber = findViewById(R.id.rd_uber);
        radioGroup =findViewById(R.id.radioGroup);
        geocoder = new Geocoder(this, Locale.getDefault());
        dataView = findViewById(R.id.SR_dataview);
        dataView.setVisibility(View.GONE);
        _1trip = findViewById(R.id.tx_fistDetails);
        _2trip = findViewById(R.id.tx_secDetails);
        _3trip = findViewById(R.id.tx_thirdDetails);
        result_BT = findViewById(R.id.button2);
        newTrip = findViewById(R.id.bt_newTrip);
        Go1_BT = findViewById(R.id.bt_showFromC_TO_FirstStation);
        Go2_BT = findViewById(R.id.bt_showFromL_TO_T);
        _1Uber = findViewById(R.id.bt_requestToFirstStation);
        _2Uber = findViewById(R.id.bt_requestToLastStation);
        MetroTripDetails_BT = findViewById(R.id.BT_metroroad);
        CurrentLocation_LC = new Location("");
        TargetLocation_LC = new Location("");
        pre = getPreferences(MODE_PRIVATE);
        format = new DecimalFormat("#.#");

        //metro locations
        for (int i = 0; i < main.HelwanLineStations.size(); i++) {
            Location location = new Location("");

            location.setLatitude(main.HelwanLineStations.get(i).getSlat());
            location.setLongitude(main.HelwanLineStations.get(i).getSlong());

            MetroLocations.add(location);
            MetroStaions.add(main.HelwanLineStations.get(i).getName());
        }
        for (int i = 0; i < main.GizaLineStations.size(); i++) {
            Location location = new Location("");

            location.setLatitude(main.GizaLineStations.get(i).getSlat());
            location.setLongitude(main.GizaLineStations.get(i).getSlong());
            MetroLocations.add(location);
            MetroStaions.add(main.GizaLineStations.get(i).getName());
        }
        for (int i = 0; i < main.AbasyaLineStations.size(); i++) {
            Location location = new Location("");

            location.setLatitude(main.AbasyaLineStations.get(i).getSlat());
            location.setLongitude(main.AbasyaLineStations.get(i).getSlong());
            MetroLocations.add(location);
            MetroStaions.add(main.AbasyaLineStations.get(i).getName());
        }
        CurrentLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                resultData = true;
            }
        });
        TargetLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                resultData = true;

            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (resultData == false){

                if (Waking.isChecked()) {
                    _1Uber.setVisibility(View.GONE);
                    _2Uber.setVisibility(View.GONE);
                    Go2_BT.setVisibility(View.VISIBLE);
                    Go1_BT.setVisibility(View.VISIBLE);
                    if (distance1 > 2.3) {
                        _1trip.setText("it is recommeded to take Uber or anyride as it is long distance\nDistance = " +
                                distance1 + "Km"+"\nEstimated time for Waking = " + Double.parseDouble(format.format(distance1 / 4.5 * 60)) + " min");
                    } else {
                        _1trip.setText("Distance = " + distance1 + " Km" + "\nEstimated time for Waking = " + Double.parseDouble(format.format(distance1 / 4.5 * 60)) + " min");
                    }
                    if (distance2 > 2.3) {
                        _3trip.setText("it is recommeded to take Uber or anyride as it is long distance\nDistance = " +
                                distance2 + "Km"+ "\nEstimated time for Waking = " + Double.parseDouble(format.format(distance2 / 4.5 * 60)) + " min");
                    } else {
                        _3trip.setText("Distance = " + distance2 + " Km" + "\nEstimated time for Waking  = " + Double.parseDouble(format.format(distance2 / 4.5 * 60)) + " min");
                    }
                    _2trip.setText("You will take Metro from " + MetroStaions.get(index_nearFromCurrent) + " Station to " + MetroStaions.get(index_nearFRomTarget) + " Station ");
                } else if (uber.isChecked()) {
                    _1Uber.setVisibility(View.VISIBLE);
                    _2Uber.setVisibility(View.VISIBLE);
                    Go1_BT.setVisibility(View.GONE);
                    Go2_BT.setVisibility(View.GONE);
                    MetroTripDetails_BT.setEnabled(true);

                    if (distance1 > 2.3) {
                        _1trip.setText("Distance = " +
                                distance1+ "Km" + "\nEstimated time for Uber = " + Double.parseDouble(format.format(distance1 / 15 * 60)) + " min");
                    } else {
                        _1trip.setText("it is recommeded to Walk as it is short distance\nDistance = " + distance1 + " Km" +
                                "\nEstimated time for Uber = " + Double.parseDouble(format.format(distance1 / 15 * 60)) + " min");
                    }
                    if (distance2 > 2.3) {
                        _3trip.setText("Distance = " +
                                distance2 + "Km"+ "\nEstimated time for Uber = " + Double.parseDouble(format.format(distance2 / 15 * 60)) + " min");
                    } else {
                        _3trip.setText("it is recommeded to Walk as it is short distance\nDistance = " + distance2 + " Km" +
                                "\nEstimated time for Uber  = " + Double.parseDouble(format.format(distance2 / 15 * 60)) + " min");
                    }
                    _2trip.setText("You will take Metro from " + MetroStaions.get(index_nearFromCurrent) + " Station to " + MetroStaions.get(index_nearFRomTarget) + " Station ");
                }
                }
            }
        });
        //uber request

        SessionConfiguration config = new SessionConfiguration.Builder()
                // mandatory
                .setClientId("r526x0x4hlfOsYjYWxBQR6KgulsmTfKo")
                // required for enhanced button features
                .setServerToken("")
                // required for implicit grant authentication
                //setRedirectUri().
                .setScopes(Arrays.asList(Scope.RIDE_WIDGETS)).
                        setEnvironment(SessionConfiguration.Environment.PRODUCTION).
                        build();
        UberSdk.initialize(config);


    }

    public byte GetNearsetStation(Location StationLocation, List<Location> StaionsLocations, List<String> names) {
        float minDis = Float.MAX_VALUE;
        float dis;
        byte index = 0;
        for (byte i = 0; i < StaionsLocations.size(); i++) {
            dis = StationLocation.distanceTo(StaionsLocations.get(i));
            if (dis < minDis) {
                minDis = dis;
                index = i;
            }
        }
        return index;
    }

    public void GetCurrentLocation(View view) {
        Loc = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            String[] per = {Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(this, per, 1);
        } else
            Loc.requestSingleUpdate(NETWORK_PROVIDER, this, null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                try {
                    Loc.requestSingleUpdate(NETWORK_PROVIDER, this, null);
                } catch (SecurityException e) {

                }
            } else {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
                // Toast.makeText(this, "you need to open location", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        List<Address> fromLocation = null;
        try {
            fromLocation = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(this, fromLocation.get(0).getFeatureName() + "  " + fromLocation.get(0).getSubAdminArea() + "   " + fromLocation.get(0).getAdminArea() + "  " + fromLocation.get(0).getCountryName(), Toast.LENGTH_SHORT).show();
    }

    public void map(View view) {
        if (resultData == true) {

            CurrentLocationName = CurrentLocation.getText().toString() + " Egypt";
            TargetLocationName = TargetLocation.getText().toString() + " Egypt";
            Geocoder geocoder = new Geocoder(this);
            try {
                CurrentinputAdress = geocoder.getFromLocationName(CurrentLocationName, 1);
                TargetinputAdress = geocoder.getFromLocationName(TargetLocationName, 1);

                if (CurrentinputAdress.size() < 1 || TargetinputAdress.size() < 1) {
                    Toast.makeText(this, "Please input a valid Location", Toast.LENGTH_SHORT).show();

                } else {
                    Currentlat = CurrentinputAdress.get(0).getLatitude();
                    Currentlong = CurrentinputAdress.get(0).getLongitude();
                    Targetlat = TargetinputAdress.get(0).getLatitude();
                    Targetlong = TargetinputAdress.get(0).getLongitude();
                    CurrentLocation_LC.setLatitude(Currentlat);
                    CurrentLocation_LC.setLongitude(Currentlong);
                    TargetLocation_LC.setLongitude(Targetlong);
                    TargetLocation_LC.setLatitude(Targetlat);

                    //near stations
                    index_nearFromCurrent = GetNearsetStation(CurrentLocation_LC, MetroLocations, MetroStaions);
                    index_nearFRomTarget = GetNearsetStation(TargetLocation_LC, MetroLocations, MetroStaions);
                    NearsetStaion_C.setText(MetroStaions.get(index_nearFromCurrent));
                    NearsetStaion_T.setText(MetroStaions.get(index_nearFRomTarget));
                     distance1 = Double.parseDouble(format.format((CurrentLocation_LC.distanceTo(MetroLocations.get(index_nearFromCurrent))) / 1000));
                     distance2 = Double.parseDouble(format.format((TargetLocation_LC.distanceTo(MetroLocations.get(index_nearFRomTarget))) / 1000));
                    if (Waking.isChecked()) {
                        _1Uber.setVisibility(View.GONE);
                        _2Uber.setVisibility(View.GONE);
                        Go2_BT.setVisibility(View.VISIBLE);
                        Go1_BT.setVisibility(View.VISIBLE);
                        if (distance1 > 2.3) {
                            _1trip.setText("it is recommeded to take Uber or anyride as it is long distance\nDistance = " +
                                    distance1 + "Km"+ "\nEstimated time for Waking = " + Double.parseDouble(format.format(distance1 / 4.5 * 60)) + " min");
                        } else {
                            _1trip.setText("Distance = " + distance1 + " Km" + "\nEstimated time for Waking = " + Double.parseDouble(format.format(distance1 / 4.5 * 60)) + " min");
                        }
                        if (distance2 > 2.3) {
                            _3trip.setText("it is recommeded to take Uber or anyride as it is long distance\nDistance = " +
                                    distance2 + "Km"+ "\nEstimated time for Waking = " + Double.parseDouble(format.format(distance2 / 4.5 * 60)) + " min");
                        } else {
                            _3trip.setText("Distance = " + distance2 + " Km" + "\nEstimated time for Waking  = " + Double.parseDouble(format.format(distance2 / 4.5 * 60)) + " min");
                        }
                        _2trip.setText("You will take Metro from " + MetroStaions.get(index_nearFromCurrent) + " Station to " + MetroStaions.get(index_nearFRomTarget) + " Station ");
                    } else if (uber.isChecked()) {
                        _1Uber.setVisibility(View.VISIBLE);
                        _2Uber.setVisibility(View.VISIBLE);
                        Go1_BT.setVisibility(View.GONE);
                        Go2_BT.setVisibility(View.GONE);
                        MetroTripDetails_BT.setEnabled(true);

                        if (distance1 > 2.3) {
                            _1trip.setText("Distance = " +
                                    distance1+ "Km" + "\nEstimated time for Uber = " + Double.parseDouble(format.format(distance1 / 15 * 60)) + " min");
                        } else {
                            _1trip.setText("it is recommeded to Walk as it is short distance\nDistance = " + distance1 + " Km" +
                                    "\nEstimated time for Uber = " + Double.parseDouble(format.format(distance1 / 15 * 60)) + " min");
                        }
                        if (distance2 > 2.3) {
                            _3trip.setText("Distance = " +
                                    distance2+ "Km" + "\nEstimated time for Uber = " + Double.parseDouble(format.format(distance2 / 15 * 60)) + " min");
                        } else {
                            _3trip.setText("it is recommeded to Walk as it is short distance\nDistance = " + distance2 + " Km" +
                                    "\nEstimated time for Uber  = " + Double.parseDouble(format.format(distance2 / 15 * 60)) + " min");
                        }
                        _2trip.setText("You will take Metro from " + MetroStaions.get(index_nearFromCurrent) + " Station to " + MetroStaions.get(index_nearFRomTarget) + " Station ");
                    }
                }
                resultData = false;
                dataView.setVisibility(View.VISIBLE);

                result_BT.setEnabled(false);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
        }
    }

    public void openMap(View view) {
        MetroTripDetails_BT.setEnabled(true);
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?saddr=" +
                        CurrentLocation_LC.getLatitude() + "," + CurrentLocation_LC.getLongitude() +
                        "&daddr=" +
                        MetroLocations.get(index_nearFromCurrent).getLatitude() + "," +
                        MetroLocations.get(index_nearFromCurrent).getLongitude()));
        startActivity(intent);
    }

    public void getMetroroad(View view) {
        Go2_BT.setEnabled(true);
        _2Uber.setEnabled(true);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("start", MetroStaions.get(index_nearFromCurrent));
        intent.putExtra("end", MetroStaions.get(index_nearFRomTarget));
        startActivity(intent);
    }

    public void openMap2(View view) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?saddr=" +
                        MetroLocations.get(index_nearFRomTarget).getLatitude() + "," +
                        MetroLocations.get(index_nearFRomTarget).getLongitude() +
                        "&daddr=" +
                        TargetLocation_LC.getLatitude() + "," + TargetLocation_LC.getLongitude()
                ));
        startActivity(intent);
    }

    public void newTrip(View view) {
        Go2_BT.setEnabled(false);
        MetroTripDetails_BT.setEnabled(false);
        result_BT.setEnabled(true);
        dataView.setVisibility(View.GONE);
        resultData = true;
        CurrentLocation.setText("");
        TargetLocation.setText("");
    }

    public void request(View view) {
        MetroTripDetails_BT.setEnabled(true);
        RideRequestButton requestButton = new RideRequestButton(this);
        RelativeLayout layout = findViewById(R.id.relative_layout);
        layout.addView(requestButton);
        Location location1 = CurrentLocation_LC;
        Location location2 = MetroLocations.get(index_nearFromCurrent);
        try {
            CurrentinputAdress = geocoder.getFromLocation(location1.getLatitude(), location1.getLongitude(), 1);
            TargetinputAdress = geocoder.getFromLocation(location2.getLatitude(), location2.getLongitude(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        RideParameters rideParams = new RideParameters.Builder()
                .setProductId("a1111c8c-c720-46c3-8534-2fcdd730040d")
                .setDropoffLocation(location1.getLatitude(), location1.getLongitude(), CurrentinputAdress.get(0).getCountryName(), CurrentinputAdress.get(0).toString())
                .setPickupLocation(location2.getLatitude(), location2.getLongitude(), TargetinputAdress.get(0).getCountryName(), TargetinputAdress.get(0).toString())
                .build();
        requestButton.setRideParameters(rideParams);

/* get data price and time
        ServerTokenSession session = new ServerTokenSession(config);
        requestButton.setSession(session);

        requestButton.loadRideInformation();
*/

        RideRequestButtonCallback callback = new RideRequestButtonCallback() {

            @Override
            public void onRideInformationLoaded() {
                // react to the displayed estimates
            }

            @Override
            public void onError(ApiError apiError) {
                // API error details: /docs/riders/references/api#section-errors
            }

            @Override
            public void onError(Throwable throwable) {
                // Unexpected error, very likely an IOException
            }
        };
        requestButton.setCallback(callback);
    }


    public void request2(View view) {

    }
}




