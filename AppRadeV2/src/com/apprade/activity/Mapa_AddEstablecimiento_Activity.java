package com.apprade.activity;

import java.io.IOException;
import java.util.List;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.apprade.R;
import com.apprade.helper.Helper_GPS_Tracker;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Mapa_AddEstablecimiento_Activity extends FragmentActivity implements OnInfoWindowClickListener, OnMapClickListener, OnMarkerClickListener, OnMarkerDragListener {
	
	//Logging 
	private static final String TAG = Mapa_AddEstablecimiento_Activity.class.getSimpleName();
	
	//Objects
	private Helper_GPS_Tracker gps;
	private GoogleMap addMap;
	private Context _context;
	private ActionBar actionBar;
	private static MarkerOptions markerOptions = new MarkerOptions();
	
	//Establisment values
	private double dLatitude;
	private double dLongitude;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mapa_add_est_);
		
		_context =  getApplicationContext();
		getActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent))); 
		
		setUpMap();
		setUpGps();
		
	}
	
	
	
	public void setUpMap(){
		
		addMap = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.mapAdd)).getMap();
		
		addMap.getUiSettings().setZoomControlsEnabled(true);
		addMap.setMyLocationEnabled(true);
		addMap.getUiSettings().setMyLocationButtonEnabled(true);
		addMap.setOnMarkerClickListener(this);
		addMap.setOnInfoWindowClickListener(this);
		addMap.setOnMapClickListener(this);
		addMap.setOnMarkerDragListener(this);
	}

	
	public void setUpGps(){
		gps = new Helper_GPS_Tracker(_context);

		if (gps.canGetLocation()) {

			dLatitude = gps.getLatitude();
			dLongitude = gps.getLongitude();

			CameraUpdate camera1 = CameraUpdateFactory.newLatLngZoom(
					new LatLng(dLatitude, dLongitude), 16f);
			addMap.animateCamera(camera1);

		} else {
			gps.showSettingsAlert();
		}
	}
	
	
	@Override
	public boolean onMarkerClick(Marker addMarkerClick) {
		
		
		
		
		return false;
	}

	@Override
	public void onMapClick(LatLng position) {
		
	    Marker addMarker = addMap.addMarker(markerOptions
					      .position(position)
					      .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_add_marker_64))
					      .draggable(true));
		
					      
		Address address = null;
		Geocoder coder = new Geocoder(_context);
		List<Address> list_address;
		
		try {
			list_address = coder.getFromLocation(position.latitude, position.longitude, 1);
		    address = list_address.get(0);
			
			addMarker.setTitle(address.getSubLocality());
			addMarker.setSnippet(address.getThoroughfare());
			
		} catch (IOException e) {
			Log.e(TAG, "Error: " + e.getMessage());
		}
		
		addMarker.showInfoWindow();

	}

	@Override
	public void onInfoWindowClick(Marker addMarkerInfwin) {
		
		Intent inPopupMap = new Intent(_context, Popup_AgregarEstablecimiento.class);
		inPopupMap.putExtra("distrito", addMarkerInfwin.getTitle());
		inPopupMap.putExtra("direccion", addMarkerInfwin.getSnippet());
		inPopupMap.putExtra("latitude", addMarkerInfwin.getPosition().latitude);
		inPopupMap.putExtra("longitude",  addMarkerInfwin.getPosition().longitude);
		
		startActivity(inPopupMap);
	}



	@Override
	public void onMarkerDrag(Marker arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onMarkerDragEnd(Marker addMarker) {
		
		Address address = null;
		Geocoder coder = new Geocoder(_context);
		List<Address> list_address;
		LatLng position = addMarker.getPosition();
		
		try {
			list_address = coder.getFromLocation(position.latitude, position.longitude, 1);
		    address = list_address.get(0);
			
			addMarker.setTitle(address.getSubLocality());
			addMarker.setSnippet(address.getThoroughfare());
			
		} catch (IOException e) {
			Log.e(TAG, "Error: " + e.getMessage());
		}
		
		addMarker.showInfoWindow();
	}



	@Override
	public void onMarkerDragStart(Marker arg0) {
		// TODO Auto-generated method stub
		
	}
	
	private void clearMap(){
		addMap.clear();
	}
}
