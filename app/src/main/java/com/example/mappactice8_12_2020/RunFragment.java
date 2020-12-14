package com.example.mappactice8_12_2020;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.camera2.params.LensShadingMap;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static android.app.Activity.RESULT_OK;

public class RunFragment extends Fragment {

    public static final String EXTRA_LENGTH = "length";
    public static final String EXTRA_TIME = "time";
    public static final String EXTRA_SPEED = "speed";

    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    private GoogleMap map;
    private ArrayList<LatLng> arrayList;
    private LatLng latLng;
    private Chronometer mChronometer;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_run, container, false);

        arrayList = new ArrayList<LatLng>();
        mChronometer = (Chronometer) view.findViewById(R.id.chronometer1);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                LatLng bishkekFocus = new LatLng(42.871318, 74.568512);
                map.moveCamera(CameraUpdateFactory.newLatLng(bishkekFocus));
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(bishkekFocus, 9));

                LocationService.locationCallback = new LocationCallback() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);

                        if (locationResult != null && locationResult.getLastLocation() != null) {
                            double latitude = locationResult.getLastLocation().getLatitude();
                            double longitude = locationResult.getLastLocation().getLongitude();

                            latLng = new LatLng(latitude, longitude);

                            arrayList.add(latLng);
                            polyline();
                        }
                    }
                };
            }
        });

        view.findViewById(R.id.buttonStartLocationUpdates).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION_PERMISSION);
                } else {

                    arrayList.clear();

                    startLocationService();

                    mChronometer.setBase(SystemClock.elapsedRealtime());
                    mChronometer.start();

                }
            }
        });

        view.findViewById(R.id.buttonStopLocationUpdates).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                stopLocationService();

                float currentTime = (SystemClock.elapsedRealtime() - mChronometer.getBase());

                @SuppressLint("DefaultLocale")
                String length = String.format("%.2f км", calculateDistance2(arrayList));
                @SuppressLint("DefaultLocale")
                String speed = String.format("%.2f км/час", conventer(currentTime));
                String time = makeReadable((int) currentTime);
                mChronometer.stop();


                Log.d("result", "   length: " + length + ";     speed: " + speed + ";     time: " + time);
            }
        });

        return view;
    }

    public float conventer(float timeInMillisecToHours) {
        float HH = ((timeInMillisecToHours / (1000 * 60 * 60)) % 24);
        return calculateDistance2(arrayList) / HH;
    }

    public static String makeReadable(int milliseconds) {
        int SS = 0;
        int MM = 0;
        int HH = 0;

        SS = (int) (milliseconds / 1000) % 60;
        MM = (int) ((milliseconds / (1000 * 60)) % 60);
        HH = (int) ((milliseconds / (1000 * 60 * 60)) % 24);

        String time = String.format(Locale.getDefault(), "%d:%02d:%02d", HH, MM, SS);

        return time;
    }

    private void polyline() {
        map.clear();
        PolylineOptions polylineOptions = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
        for (int i = 0; i < arrayList.size(); i++) {
            LatLng point = arrayList.get(i);
            polylineOptions.add(point);
        }
        map.addMarker(new MarkerOptions().position(latLng).title("New Location"));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
        Polyline polyline = map.addPolyline(polylineOptions);
    }

    @SuppressLint("DefaultLocale")
    private static float calculateDistance2(ArrayList<LatLng> points) {
        float tempTotalDistance = 0;
        for (int i = 0; i < points.size() - 1; i++) {
            LatLng pointA = points.get(i);
            LatLng pointB = points.get(i + 1);
            float[] results = new float[3];
            Location.distanceBetween(pointA.latitude, pointA.longitude, pointB.latitude, pointB.longitude, results);
            tempTotalDistance += results[0];
        }

        return tempTotalDistance / 1000;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationService();
            } else {
//                Toast.makeText(getContext(), "Permission denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isLocationServiceRunning() {
        ActivityManager activityManager =
                (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager != null) {
            for (ActivityManager.RunningServiceInfo service :
                    activityManager.getRunningServices(Integer.MAX_VALUE)) {
                if (LocationService.class.getName().equals(service.service.getClassName())) {
                    if (service.foreground) {
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }

    private void startLocationService() {
        if (!isLocationServiceRunning()) {
            Intent intent = new Intent(getActivity().getApplicationContext(), LocationService.class);
            intent.setAction(Constants.ACTION_START_LOCATION_SERVICE);
            getActivity().startService(intent);
        }
    }

    private void stopLocationService() {
        if (isLocationServiceRunning()) {
            Intent intent = new Intent(getActivity().getApplicationContext(), LocationService.class);
            intent.setAction(Constants.ACTION_STOP_LOCATION_SERVICE);
            getActivity().startService(intent);
        }
    }
}