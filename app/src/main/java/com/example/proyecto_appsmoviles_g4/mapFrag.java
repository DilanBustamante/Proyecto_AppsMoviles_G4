package com.example.proyecto_appsmoviles_g4;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.IOException;
import java.util.ArrayList;


import static android.content.Context.LOCATION_SERVICE;

public class mapFrag extends Fragment implements  OnMapReadyCallback, LocationListener, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerClickListener, View.OnClickListener{

    private GoogleMap mMap;
    private  LocationManager manager;
    private Marker marcadorGlo;
    private OnNewAddressListener adrdressListener;
    private MapView mapV;
    private ArrayList<LatLng> locaciones = new ArrayList<>();

    private Button myLocationButton;
    private Button continueButtonmap;
    private Marker me;

    private String keyWord = "";
    private Bundle extras;




    public mapFrag() {
        // Required empty public constructor
    }


    public static mapFrag newInstance() {
        mapFrag fragment = new mapFrag();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_map, container, false);
        mapV = (MapView) root.findViewById(R.id.mapV);
        mapV.onCreate(savedInstanceState);
        mapV.getMapAsync(this);

        myLocationButton = root.findViewById(R.id.myLocationButton);
        continueButtonmap = root.findViewById(R.id.continueButtonmap);

        myLocationButton.setOnClickListener(this);
        continueButtonmap.setOnClickListener(this);


        return root;

    }


    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);

        FragmentActivity fragmentAc = getActivity();
        manager = (LocationManager) fragmentAc.getSystemService(LOCATION_SERVICE);

        this.setInitialPos();

        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,2,this);

        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);
        mMap.setOnMarkerClickListener(this);

        if(keyWord.equals("keymapword")){
            continueButtonmap.setEnabled(false);
        }

    }


    @SuppressLint("MissingPermission")
    public void setInitialPos(){
        me = null;
        Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(location != null){
            this.updateLocation(location);
        }
        this.colocarMarcadoresLugareRegistrados();
  }



    @Override
    public void onLocationChanged(@NonNull Location location) {
        this.updateLocation(location);
    }




    public void updateLocation(Location location){
        LatLng myPos = new LatLng(location.getLatitude(),location.getLongitude());
        if(me == null){
            me = mMap.addMarker(new MarkerOptions().position(myPos).title("Ubicación actual").icon(BitmapDescriptorFactory.fromResource(R.drawable.me)));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myPos,17));
        }else{
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myPos,17));
        }
    }



    @Override
    public void onResume() {
        super.onResume();
        if (mapV != null) {
            mapV.onResume();
        }
    }

    @Override
    public void onPause() {
        if (mapV != null) {
            mapV.onPause();
        }
        super.onPause();
    }

    @Override
    public void onDestroy() {
        if (mapV != null) {
            try {
                mapV.onDestroy();
            } catch (NullPointerException e) {
                Log.e(">>>", "Error while attempting MapView.onDestroy(), ignoring exception", e);
            }
        }
        super.onDestroy();
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mapV != null) {
            mapV.onLowMemory();
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mapV != null) {
            mapV.onSaveInstanceState(outState);
        }
    }



    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    @Override
    public void onProviderEnabled(@NonNull String provider) {}

    @Override
    public void onProviderDisabled(@NonNull String provider) {}


    @Override
    public void onMapClick(LatLng latLng) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,14));
    }



    @Override
    public void onMapLongClick(LatLng latLng) {
        Marker p =  mMap.addMarker(new MarkerOptions().position(latLng).title("Nuevo lugar"));
        this.activarAddresAndSnippet(p);
        marcadorGlo = p;

        LatLng locacion = new LatLng(p.getPosition().latitude,p.getPosition().longitude);
        locaciones.add(locacion);
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        this.activarAddresAndSnippet(marker);
        marker.showInfoWindow();
        return true;
    }

     public void activarAddresAndSnippet(Marker p){
        String address = this.getAddressFromLatLng(p.getPosition()).toString();
        p.setSnippet(address);
    }


    private String getAddressFromLatLng( LatLng latLng ) {
        Geocoder geocoder = new Geocoder( getActivity() );
        String address = "";
        try {
            address = geocoder.getFromLocation( latLng.latitude, latLng.longitude, 1 ).get( 0 ).getAddressLine( 0 );
        } catch (IOException e ) {

        }
        return address;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.myLocationButton:
                if(me!=null) {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(me.getPosition(), 17));
                }else{
                    Toast.makeText(getActivity(),"Para ir a tu posición por favor activa tu GPS",Toast.LENGTH_LONG).show();
                }
            break;

            case R.id.continueButtonmap:
                 adrdressListener.onNewAddress(marcadorGlo.getSnippet().toString());
                inicioActivity activity = (inicioActivity) getActivity();
                activity.showFragment(activity.getvetFragment());
            break;
        }
    }






    public void colocarMarcadoresLugareRegistrados(){
        Marker placeLocation = null;

        for (int i = 0; i < locaciones.size(); i++){
            if(placeLocation == null) {
                placeLocation = mMap.addMarker(new MarkerOptions().position(locaciones.get(i)).title("Nuevo lugar").icon(BitmapDescriptorFactory.fromResource(R.drawable.ubicacion)));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(locaciones.get(i),17));
            }else{
                placeLocation = mMap.addMarker(new MarkerOptions().position(locaciones.get(i)).title("Nuevo lugar").icon(BitmapDescriptorFactory.fromResource(R.drawable.ubicacion)));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(locaciones.get(i),17));

            }
        }

    }



    public void setObserver(OnNewAddressListener observer){
        this.adrdressListener = observer;
    }

    public interface OnNewAddressListener{
        void onNewAddress (String address);
    }


}

