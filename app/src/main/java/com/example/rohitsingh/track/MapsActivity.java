package com.example.rohitsingh.track;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Service;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.sql.Time;
import java.util.List;
import java.util.Locale;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback  {

    private GoogleMap mMap;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Button b_1;
    Button restart;
    TextView tv_1;
    public double latitude;
    public double longitude;
    TextView adrs ;
    TextView address;
    TextView address2;
    TextView address3;
    double lon;
    double lat;
    String time;




    public class User{
        public double Lat;
        public double Lon;
        public String time;
        public String ip;

        public double getLat() {
            return Lat;
        }

        public void setLat(double lat) {
            Lat = lat;
        }

        public double getLon() {
            return Lon;
        }

        public void setLon(double lon) {
            Lon = lon;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public User(double lat, double lon, String time, String ip) {
            Lat = lat;
            Lon = lon;
            this.time = time;
            this.ip = ip;
        }

        public User() {
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        b_1 = (Button)findViewById(R.id.b_1);
        restart=(Button)findViewById(R.id.restart);
        tv_1 = (TextView)findViewById(R.id.tv_1);
        address=(TextView)findViewById(R.id.address);
        address2=(TextView)findViewById(R.id.address2);
        address3=(TextView)findViewById(R.id.address3);


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();


                final DatabaseReference mchildlat =  databaseReference.child("User 1").child("Lat");
                mchildlat.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                          lat = dataSnapshot.getValue(double.class);
                        address2.setText("latitude\n"+ lat);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("loadPost:onCancelled", databaseError.toException());
                        Toast.makeText(MapsActivity.this,"no work",Toast.LENGTH_LONG).show();

                    }


                });
        final DatabaseReference mchildlon =  databaseReference.child("User 1").child("Lon");
        mchildlon.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                 lon = dataSnapshot.getValue(double.class);

                address3.setText("longitude "+ lon);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("loadPost:onCancelled", databaseError.toException());
                Toast.makeText(MapsActivity.this,"no work",Toast.LENGTH_LONG).show();

            }


        });

        final DatabaseReference mchildtime =  databaseReference.child("User 1").child("Time");
        mchildtime.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                 time = dataSnapshot.getValue(String.class);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("loadPost:onCancelled", databaseError.toException());
                Toast.makeText(MapsActivity.this,"no work",Toast.LENGTH_LONG).show();

            }


        });

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = getBaseContext().getPackageManager()
                        .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });


    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);

        final Marker[] marker = new Marker[1];


        /*mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                 latitude = latLng.latitude;
                 longitude = latLng.longitude;

                if (marker[0] != null) {
                    marker[0].remove();
                }

                tv_1.setText("lat= "+latitude+"  lon= "+longitude);
               marker[0] = mMap.addMarker(new MarkerOptions().position(new LatLng(latitude,longitude)).title("My Location").draggable(true).visible(true));
                LatLng sydney = new LatLng(latitude, longitude);
                mMap.addMarker(new MarkerOptions().position(sydney).title(time));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

                try {
                    diplay_address(latitude,longitude);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });*/

        b_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (marker[0] != null) {
                    marker[0].remove();
                }
                LatLng sydney = new LatLng(lat, lon);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

                marker[0] = mMap.addMarker(new MarkerOptions()
                        .position(
                                new LatLng(lat,lon)).title(time).visible(true));

            }
        });


    }

    private void diplay_address(double latitude, double longitude) throws IOException {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            String address = addresses.get(0).getAddressLine(0);
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            adrs=(TextView) findViewById(R.id.address);

            adrs.setText(String.format("%s\n%s\n%s", address, city, state));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}


