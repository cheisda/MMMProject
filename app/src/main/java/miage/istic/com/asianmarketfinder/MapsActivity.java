package miage.istic.com.asianmarketfinder;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import miage.istic.com.asianmarketfinder.database.sto_tag.Sto_tag;
import miage.istic.com.asianmarketfinder.database.store.Store;
import miage.istic.com.asianmarketfinder.database.store.StoreDAO;
import miage.istic.com.asianmarketfinder.database.tag.Tag;
import miage.istic.com.asianmarketfinder.fragments.TagListFragment;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String city;
    private LocationManager locationGPS;
    private LocationListener locationListener;
    private String placesSearchStr;
    private StringBuilder placesBuilder;
    private final DatabaseReference mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
    private HashMap<Marker, String> markerStringHashMap = new HashMap<>();
    private Bundle saveInstance;

    public HashMap<Marker, String> getMarkerStringHashMap() {
        return markerStringHashMap;
    }

    public LocationManager getLocationGPS() {
        return locationGPS;
    }

    public void setLocationGPS(LocationManager locationGPS) {
        this.locationGPS = locationGPS;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.saveInstance = savedInstanceState;
        setContentView(R.layout.activity_main);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationGPS = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        criteria.setPowerRequirement(Criteria.POWER_LOW); // Chose your desired power consumption level.
        criteria.setAccuracy(Criteria.ACCURACY_FINE); // Choose your accuracy requirement.
        criteria.setSpeedRequired(true); // Chose if speed for first location fix is required.
        criteria.setAltitudeRequired(false); // Choose if you use altitude.
        criteria.setBearingRequired(false); // Choose if you use bearing.
        criteria.setCostAllowed(false); // Choose if this provider can waste money :-)

        String provider = LocationManager.NETWORK_PROVIDER;

// Returns last known location, this is the fastest way to get a location fix.
        Location fastLocation = locationGPS.getLastKnownLocation(provider);
        System.out.println(fastLocation);

        if (fastLocation != null) {
            Geocoder gcd = new Geocoder(this, Locale.getDefault());
            List<Address> addresses;
            try {
                addresses = gcd.getFromLocation(fastLocation.getLatitude(),
                        fastLocation.getLongitude(), 1);
                if (addresses.size() > 0) {
                    System.out.println(addresses.get(0).getLocality());
                    String cityName = addresses.get(0).getLocality();
                    this.setCity(cityName);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        locationListener = new MyLocationListener(this);
        locationGPS.requestLocationUpdates(
                locationGPS.getBestProvider(criteria, true), 0, 0, locationListener);
/*
        if (locationGPS.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            System.out.println("yayay");


        } else {
            *//*Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(myIntent);*//*
        }*/


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
        final Context mContext = this;

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                MapsActivity ma = (MapsActivity) mContext;
                final String id = ma.getMarkerStringHashMap().get(marker);
                System.out.println(id);

                TagListFragment tagListFragment = new TagListFragment();
                Bundle args = new Bundle();
                args.putString("id_store", id);
                tagListFragment.setArguments(args);

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.tag_list, tagListFragment);
                transaction.addToBackStack(null);
                transaction.commit();
/*                mFirebaseDatabaseReference.child("sto_tag").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        System.out.println(dataSnapshot.getChildren());
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Sto_tag sto_tag = snapshot.getValue(Sto_tag.class);

                            System.out.println("tre: " + sto_tag.getSto_id());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });*/


                return false;
            }
        });
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                LinearLayout info = new LinearLayout(mContext);
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(mContext);
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());

                TextView snippet = new TextView(mContext);
                snippet.setTextColor(Color.GRAY);
                snippet.setText(marker.getSnippet());

                info.addView(title);
                info.addView(snippet);

                return info;
            }
        });
    }

    public void addMarkersToMap(String data) {
        try {
            mMap.clear();
            System.out.println("pppp: " + data);
            LatLngBounds.Builder b = new LatLngBounds.Builder();
            Boolean set = false;

            JSONObject json = new JSONObject(data.toString());
            JSONArray resultsArray = json.getJSONArray("results");
            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject results = new JSONObject(resultsArray.get(i).toString());
                JSONObject geometry = new JSONObject(results.get("geometry").toString());
                if (results.has("geometry")) {
                    if (geometry.has("location")) {
                        JSONObject location = new JSONObject(geometry.get("location").toString());
                        if (location.has("lat") && location.has("lng")) {
                            String id = (String) results.get("id");
                            Double lng = (Double) location.get("lng");
                            Double lat = (Double) location.get("lat");
                            LatLng pos = new LatLng(lat, lng);
                            b = b.include(pos);
                            set = true;
                            String name = (String) results.get("name");
                            Double rating = results.has("rating") ? (Double) results.get("rating") : 0.00;
                            JSONObject opening = results.has("opening_hours") ? new JSONObject(results.get("opening_hours").toString()) : new JSONObject("{'open_now': false}");
                            String open = ((boolean) opening.get("open_now")) ? "oui" : "non";

                            Store store = new Store(id, name, lat, lng, "");
                            this.addToFirebase("store", id, store);
                            Marker marker = mMap.addMarker(new MarkerOptions()
                                    .position(pos)
                                    .title(name)
                                    .snippet("Rating: " + rating.toString() + "\n"
                                            + "Ouvert: " + open));
                            this.getMarkerStringHashMap().put(marker, id);
                            Log.e("jsontest", "val: " + lat.toString());
                        }
                    }

                }

            }
            if (set) {
                LatLngBounds bounds = b.build();
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 150));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private class GetPlaces extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            placesBuilder = new StringBuilder();
            for (String placeSearchURL : params) {
                URL requestUrl = null;
                try {
                    requestUrl = new URL(placeSearchURL);
                    HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
                    connection.setRequestMethod("GET");
                    connection.connect();
                    int responseCode = connection.getResponseCode();

                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader reader = null;
                        InputStream inputStream = connection.getInputStream();
                        if (inputStream == null) {
                            return "";
                        }
                        reader = new BufferedReader(new InputStreamReader(inputStream));

                        String line;
                        while ((line = reader.readLine()) != null) {
                            System.out.println(line);
                            placesBuilder.append(line + "\n");
                        }

                        if (placesBuilder.length() == 0) {
                            return "";
                        }

                        //Log.d("test", placesBuilder.toString());
                    } else {
                        Log.i("test", "Unsuccessful HTTP Response Code: " + responseCode);
                    }
                } catch (MalformedURLException e) {
                    Log.e("test", "Error processing Places API URL", e);
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    Log.e("test", "Error connecting to Places API", e);
                }

            }
            System.out.println("a: " + placesBuilder.toString());
            return placesBuilder.toString();
        }

        protected void onPostExecute(String result) {
            // you can call a method of your activity
            // example you can generate a list of all
            // your markers and passed as param of method
            // to your activity.
            addMarkersToMap(result);
        }
    }

    public void setCity(String city) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        this.city = city;
        placesSearchStr = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=asian+supermarkets+or+asian+restaurants+in+" + city + "&key=AIzaSyC4AaXJEEJdIMUmuJ3m8yy-TcHmMK2RhFs";
        new GetPlaces().execute(placesSearchStr);

    }

    private void addToFirebase(final String table, final String id, final Object obj) {
        mFirebaseDatabaseReference.child(table).child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    mFirebaseDatabaseReference.child(table).child(id).setValue(obj);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
