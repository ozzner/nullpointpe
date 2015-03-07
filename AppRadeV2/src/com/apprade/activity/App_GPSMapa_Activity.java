package com.apprade.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
//import android.support.v4.widget.SearchViewCompatIcs.MySearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import android.widget.Toast;

import com.apprade.R;
import com.apprade.R.color;
import com.apprade.adapter.Adapter_InfoWindow;
import com.apprade.adapter.Adapter_SpinnerItem;
import com.apprade.adapter.Adapter_SpinnerNavActionBar;
import com.apprade.dao.DAO_Calificacion;
import com.apprade.dao.DAO_Establecimiento;
import com.apprade.entity.Entity_Coordenadas;
import com.apprade.entity.Entity_Establecimiento;
import com.apprade.helper.Helper_GPS_Tracker;
import com.apprade.helper.Helper_JSONStatus;
import com.apprade.helper.Helper_SharedPreferences;
import com.apprade.helper.Helper_SubRoutines;
import com.apprade.helper.Helper_constants;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.internal.IGoogleMapDelegate;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.plus.Plus;

public class App_GPSMapa_Activity extends FragmentActivity implements
		OnMarkerClickListener, OnInfoWindowClickListener,
		ActionBar.OnNavigationListener, OnMapClickListener, OnQueryTextListener  {

	private static final String TAG_NO_HAY_COLA = "No hay cola";
	private static final String TAG_POCA_COLA = "Poca cola";
	private static final String TAG_COLA_MODERADA = "Cola moderada";
	private static final String TAG_ALTA_COLA = "Alta cola";
	private static GoogleMap map;
	private static GoogleMap mapAdd;
	private static MarkerOptions markerOptions = new MarkerOptions();
	private MenuItem refreshMenuItem;
	Helper_GPS_Tracker gps;
	private ArrayList<Adapter_SpinnerItem> arrAdpSpinner;
	private Adapter_SpinnerNavActionBar oAdpSpinner;
	private double latitude;
	private double longitude;
	private int usuarioID;
	private ActionBar actionBar;
	private AdView adView;
	private DAO_Establecimiento dao;
	public Entity_Establecimiento ettEst;
	private Helper_JSONStatus status;
	private Fragment_Calificar mFragment;
	private DAO_Calificacion oCalificar;
	private Helper_SubRoutines routine;
	private Helper_SharedPreferences oPrefe;
	private Adapter_InfoWindow oInfoWindow;
	private Helper_SubRoutines oRoutine;
	private String arrValue[];
	private CharSequence query;
	private static String mensaje;
	private static final String TAG_UPDATE = "update";
	private static final String AD_UNIT_ID = "ca-app-pub-0771856019955508/3441120220";
	private static final String TEST_DEVICE_ID = "B58F8443FD40945F763B77E7BC6B2A2F";
	private final String TAG = App_GPSMapa_Activity.class.getSimpleName();
	private static Marker myMarker = null;
	private static Map<Integer, String> map2_IdEs_Cola = new HashMap<Integer,String>();
	String arrParams[] = new String[4];
	String arrCategory[] = new String[2];
	String arrKeys[] = new String[10];
	String arrValues[] = new String[10];
	String[] arrNomEst = null;
	String[] arrDirEst = null;
	private int[] arrIdEstt;
	int arraymapas[] = new int[1000];
	String titulo;
	private LinearLayout lay_rates;
	private final double DISTANCE_MIN_TO_RATE = 10000;
	private SearchView searchView;

	/* SET GET */
	/**
	 * @return the arrValue
	 */
	public String[] getArrValue() {
		return arrValue;
	}

	/**
	 * @param sValue
	 *            the arrValue to set
	 */
	public void setArrValue(String[] sValue) {
		this.arrValue = sValue;
	}

	/**
	 * @return the mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * @param mensaje
	 *            the mensaje to set
	 */
	public void setMensaje(String mensaje) {
		App_GPSMapa_Activity.mensaje = mensaje;
	}

	/**
	 * @return the myMarker
	 */
	public Marker getMyMarker() {
		return myMarker;
	}

	/**
	 * @param myMarker
	 *            the myMarker to set
	 */
	public void setMyMarker(Marker myMarker) {
		App_GPSMapa_Activity.myMarker = myMarker;
	}

	/**
	 * BOB El constructor
	 */

	public App_GPSMapa_Activity() {
		super();
		dao = new DAO_Establecimiento();
		ettEst = new Entity_Establecimiento();
		status = new Helper_JSONStatus();
		oCalificar = new DAO_Calificacion();
		oRoutine = new Helper_SubRoutines();
		oPrefe = new Helper_SharedPreferences();
		oInfoWindow = new Adapter_InfoWindow();

	}

	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gps_mapa_v2);

		ImageView ivNoHayCola = (ImageView) findViewById(R.id.iv_no_hay_cola);
		ImageView ivPocaCola = (ImageView) findViewById(R.id.iv_poca_cola);
		ImageView ivColaModerada = (ImageView) findViewById(R.id.iv_cola_moderada);
		ImageView ivAltaCola = (ImageView) findViewById(R.id.iv_alta_cola);

		actionBar = getActionBar();
		getActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));  
		
		hideRates();
		loadSpinnerNav();
		
		if (oRoutine.isOnline(getApplicationContext())) 
//			loadAdView();
		
	

		ivNoHayCola.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				arrParams[0] = String.valueOf(oInfoWindow.getIdEst());
				arrParams[1] = TAG_NO_HAY_COLA;
				
				 if (isDistanceOk() > 0) {
					 if (!enviarCalificacion())
							chkTimeCalificacion();
						else {
							hideRates();
							exeAsyncTask(arrParams);
						}
				}
				
			}
		});

		ivPocaCola.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				arrParams[0] = String.valueOf(oInfoWindow.getIdEst());
				arrParams[1] = TAG_POCA_COLA;

				 if (isDistanceOk() > 0) {
					 if (!enviarCalificacion())
							chkTimeCalificacion();
						else {
							hideRates();
							exeAsyncTask(arrParams);
						}
				}

			}
		});

		ivColaModerada.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				arrParams[0] = String.valueOf(oInfoWindow.getIdEst());
				arrParams[1] = TAG_COLA_MODERADA;

				 if (isDistanceOk() > 0) {
					 if (!enviarCalificacion())
							chkTimeCalificacion();
						else {
							hideRates();
							exeAsyncTask(arrParams);
						}
				}

			}
		});

		ivAltaCola.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				arrParams[0] = String.valueOf(oInfoWindow.getIdEst());
				arrParams[1] = TAG_ALTA_COLA;

				 if (isDistanceOk() > 0) {
					 if (!enviarCalificacion())
							chkTimeCalificacion();
						else {
							hideRates();
							exeAsyncTask(arrParams);
						}
				}

			}
		});

		try {

			Bundle oBundle = getIntent().getExtras();
			usuarioID = oBundle.getInt("user_id");
			String user = oBundle.getString("user");
			actionBar.setTitle(user);

		} catch (Exception e) {

			ArrayList<String> datos = new ArrayList<String>();

			Helper_SharedPreferences oShared = new Helper_SharedPreferences();
			datos = oShared.getAllLoginDataStored(getApplicationContext());
			usuarioID = Integer.parseInt(datos.get(2));
			String sName = datos.get(0).toString();
			actionBar.setTitle(sName);
		}
		setUpMapIfNeeded();

	} // End onCreate

	
	  @Override
	  public void onResume() {
	    super.onResume();
	    if (adView != null) {
	      adView.resume();
	    }
	  }

	  @Override
	  public void onPause() {
	    if (adView != null) {
	      adView.pause();
	    }
	    super.onPause();
	  }

	  /** Called before the activity is destroyed. */
	  @Override
	  public void onDestroy() {
	    if (adView != null) {
	      adView.destroy();
	    }
	    super.onDestroy();
	  }
	
	private void loadAdView() {
		
		 	adView = new AdView(this);
		    adView.setAdSize(AdSize.SMART_BANNER);
		    adView.setAdUnitId(AD_UNIT_ID);
		    adView.setBackgroundColor(Color.parseColor("#000000"));
		    
		    RelativeLayout layout = (RelativeLayout) findViewById(R.id.lay_mapa);
		    layout.addView(adView);
		
		    AdRequest adRequest = new AdRequest.Builder()
	        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
	        .addTestDevice(TEST_DEVICE_ID)
	        .build();
		
		    adView.loadAd(adRequest);
	}

	protected boolean enviarCalificacion() {
		boolean bMsj = false;

		String currentTime = oRoutine
				.getCurrentTime(Helper_SubRoutines.TAG_FORMAT_SHORT);
		/* KEYS - VALUES */
		arrKeys[0] = "currentTime";
		arrValues[0] = currentTime;
		arrKeys[1] = "establishmentID";
		arrValues[1] = String.valueOf(arrParams[0]);
		arrKeys[2] = "userID";
		arrValues[2] = String.valueOf(usuarioID);

		Log.e("CALIFICACION", "Cal:" + arrValues[1] + ":" + usuarioID + ":"
				+ arrKeys[0]);

		/* Valida si existe la preferencia almacenada (False) */
		if (!oPrefe.checkMyCustomPreference("Cal" + arrValues[1] + usuarioID,
				getApplicationContext(), arrKeys[0])) {

			/* Inserto mi preferencia personalizada */
			oPrefe.storeMyCustomPreferences(arrKeys, arrValues, "Cal"
					+ arrValues[1] + usuarioID, getApplicationContext());

			bMsj = true;

		} else {
			/* Si esta almacenada, obtengo los datos */
			String sValue[] = new String[arrKeys.length];
			for (int i = 0; i < arrKeys.length; i++) {

				sValue[i] = oPrefe.getAnyValueToMyCustomPreferences(
						getApplicationContext(), "Cal" + arrValues[1]
								+ usuarioID, arrKeys[i]);
				Log.e("for_get_any_sha", sValue[i]);
			}
			setArrValue(sValue);

		}

		return bMsj;
	}

	private void chkTimeCalificacion() {

		String sMySharedPref = "Cal" + arrValues[1] + usuarioID;
		String sCurrentTime = oRoutine
				.getCurrentTime(Helper_SubRoutines.TAG_FORMAT_SHORT);
		String sValue[] = new String[arrKeys.length];
		String sMsj = "";

		sValue = getArrValue();
		/* Valido que los datos sean del mismo establecimiento y usuario */
		if (sValue[1].equals(arrValues[1]) && sValue[2].equals(arrValues[2])) {
			Log.e("VAL_EST_USU", "OK");
			/*
			 * Obtengo la diferencia en minutos (tiempo_alamcenado -
			 * tiempo_actual)
			 */

			int min_dif = oRoutine.dateDiferent(sValue[0], sCurrentTime,
					"minutos");

			Log.e("VAL_MIN", "OK-" + min_dif);
			/* Evaluo cuanto tiempo ha pasado */
			if (min_dif < 5) {
				sMsj = "En " + (5 - min_dif)
						+ " min podrá volver a calificar este establecimiento.";
				oRoutine.showToast(getApplicationContext(), sMsj);
			} else {
				oPrefe.updateMyCurretTime(sMySharedPref,
						getApplicationContext(), sCurrentTime);
				getMyMarker().hideInfoWindow();
				exeAsyncTask(arrParams);
			}

		} else {
			/* Actualizo mi preferencia personalizada con otro establecimiento */
			oPrefe.storeMyCustomPreferences(arrKeys, arrValues, "Cal"
					+ arrValues[1] + usuarioID, getApplicationContext());
			Log.e("STORE NEW", "Cal" + arrValues[1] + usuarioID);
		}
	}

	public void showMyToast(String message){
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
	};
	
	
	
	private int isDistanceOk(){
		int iReport = -1;
		
		if (myMarker != null) {
			
			if (gps.canGetLocation()) {
				double latGPS = gps.getLatitude();
				double lonGPS = gps.getLongitude();
				
				LatLng position = myMarker.getPosition();
				double latEst = position.latitude;
				double lonEst = position.longitude;
				
				double dDistance = oRoutine.distanceBetweenPositions(latGPS, lonGPS, latEst, lonEst);
				
				if (dDistance < DISTANCE_MIN_TO_RATE ) {
					iReport = 1;
					Log.e(TAG,"Distance " + dDistance);
				}else{
					showMyToast("Puede calificar si está a " 
								+ DISTANCE_MIN_TO_RATE + " m. del establecimiento");
					iReport = -2;
					Log.e(TAG,"Distance " + dDistance);
				}
				
			}else{
				iReport = 0;
				showMyToast("Debe habilitar GPS/wifi");
			}
			
		}
		
		return iReport;
	}
	private void showRates() {
		
	    lay_rates = (LinearLayout)findViewById(R.id.lay_rates);
		lay_rates.setVisibility(View.GONE);
		lay_rates.setVisibility(View.VISIBLE);
//
//		mFragment = (Fragment_Calificar) (getSupportFragmentManager()
//				.findFragmentById(R.id.fragment_calificar));
//		FragmentManager fm = getSupportFragmentManager();
//		fm.beginTransaction().show(mFragment).commit();
//
//		setMyMarker(marker);
	}

	private void hideRates() {

	    lay_rates = (LinearLayout)findViewById(R.id.lay_rates);
		lay_rates.setVisibility(View.INVISIBLE);
		
//		mFragment = (Fragment_Calificar) (getSupportFragmentManager()
//				.findFragmentById(R.id.fragment_calificar));
//		FragmentManager fm = getSupportFragmentManager();
//		fm.beginTransaction().hide(mFragment).commit();

	}


	private void setUpMapIfNeeded() {

		try {

			map = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();
			
			map.getUiSettings().setZoomControlsEnabled(true);
			map.setMyLocationEnabled(true);
			map.getUiSettings().setMyLocationButtonEnabled(false);
			map.setOnMarkerClickListener(this);
			map.setOnInfoWindowClickListener(this);
			map.setOnMapClickListener(this);
			
			mapAdd = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();
		
			
			gps = new Helper_GPS_Tracker(App_GPSMapa_Activity.this);

			if (gps.canGetLocation()) {

				latitude = gps.getLatitude();
				longitude = gps.getLongitude();

				CameraUpdate camera1 = CameraUpdateFactory.newLatLngZoom(
						new LatLng(latitude, longitude), 12f);
				map.animateCamera(camera1);

			} else {
				gps.showSettingsAlert();
			}
			
		} catch (Exception e) {

		}

	}

	public void setUpMap(final float lat, final float lon, final String nom,
			final String dir, final int idEst) {

		new Thread(new Runnable() {

			@Override
			public void run() {

				runOnUiThread(new Runnable() {
					@Override
					public void run() {

						map.addMarker(markerOptions
								.position(new LatLng(lat, lon))
								.title(nom)
								.snippet(dir + idEst)
								.alpha(0.8f)
								.icon(BitmapDescriptorFactory
										.fromResource(R.drawable.ic_map)));

					}
				});
			}

		}).start();
	}

	public void runAsyncGetLasRate(final int establishmentID) {

		new Thread(new Runnable() {
			String sCola = "", sFecha = "Nada";

			@Override
			public void run() {

				boolean bResult = false;
				bResult = oCalificar.obtenerUltimaCalificacionPorEstabID(String
						.valueOf(establishmentID));

				if (bResult) {
					sCola = oCalificar.oCali.getCola();
					sFecha = oCalificar.oCali.getFecha();

					int iMin = oRoutine.dateDiferent(
							oRoutine.getCurrentTime(Helper_SubRoutines.TAG_FORMAT_SHORT),
							sFecha, Helper_SubRoutines.TAG_MINUTOS);


					/* Evaluo cuanto tiempo ha pasado */
					if (iMin > 15) {

						switch (sCola) {

						case "No hay cola":
							// TODO no hace nada
							break;
						default:
							updateRating("No hay cola", establishmentID);
							break;
						}

					} else if (iMin >= 10 && iMin <= 15) {

						switch (sCola) {
						case "Alta cola":
							updateRating("Poca cola", establishmentID);
							break;
						case "Cola moderada":
							updateRating("No hay cola", establishmentID);
							break;
						default:
							updateRating("No hay cola", establishmentID);
							break;
						}

					} else if (iMin >= 5 && iMin < 10) {

						switch (sCola) {
						case "Alta cola":
							updateRating("Cola moderada", establishmentID);
							break;
						case "Cola moderada":
							updateRating("Poca cola", establishmentID);
							break;
						case "Poca cola":
							updateRating("No hay cola", establishmentID);
							break;
						default:
							Log.e(TAG_UPDATE, "Entre 5 y 10");
							break;
						}
					} else {
						Log.e(TAG_UPDATE, "Menor de 5 min" + iMin);
						// TODO
					}

				} else {
					Log.e(TAG_UPDATE, "NO HAY CALIFICACIONES");
					sCola = oCalificar.oJsonStatus.getInfo();
				}


			}

		}).start();
	}

	public void updateRating(final String cola, final int establishmentID) {

		new Thread(new Runnable() {

			@Override
			public void run() {

				boolean bResult = oCalificar.registrarCalificacion(
						String.valueOf(usuarioID),
						String.valueOf(establishmentID), cola);

				if (bResult)
					map2_IdEs_Cola.put(oInfoWindow.getIdEst(), cola);

				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						//TODO
					}
					
					
				});

			}
		}).start();

	}



	public void exeAsyncTask(String... args) {
		
		CalificarAsync task = new CalificarAsync();
		task.execute(args);
		getMyMarker().hideInfoWindow();
		hideRates();
		actionBar.show();
	}

	class CalificarAsync extends AsyncTask<String, Void, Boolean> {

		boolean bOk = false;

		@Override
		protected Boolean doInBackground(String... params) {

			if (oCalificar.registrarCalificacion(usuarioID + "", params[0],
					params[1]))
				bOk = true;
			return bOk;
			
		}


		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);

			if (result) {
				map2_IdEs_Cola.put(oInfoWindow.getIdEst(), arrParams[1]);
				oRoutine.showToast(getApplicationContext(),
						oCalificar.oJsonStatus.getMessage());
				hideRates();
			} else {
				actionBar.setSubtitle("¡Error!");
			}
		}

	}

	/**
	 * AsynTask class listar establecimiento
	 */


	class EstablecimientoAsync extends AsyncTask<String, Void, Boolean> {

		List<Entity_Establecimiento> lista_establecimiento = new ArrayList<Entity_Establecimiento>();

		@Override
		protected void onPreExecute() {
			
			lista_establecimiento.clear();
			App_GPSMapa_Activity oApp = new App_GPSMapa_Activity();
			String sMensaje = oApp.getMensaje();

			try {

				if (sMensaje.equals("update")) {
					refreshMenuItem
							.setActionView(R.layout.action_progressbar_refresh);
					refreshMenuItem.expandActionView();
				}

			} catch (Exception e) {
			}

		}

		@Override
		protected Boolean doInBackground(String... params) {
			boolean bRequest = false;
			float lat = 0, lon = 0;

			try {
				int iCat = Integer.parseInt(params[0]);
				
				if (iCat > 0) {
					lista_establecimiento = dao
							.listarEstablecimientoPorCategoriaID(params[0]);
				}else{
					lista_establecimiento = dao
							.listarEstablecimientoPorNombre(params[1]);
				}
				

				bRequest = status.getError_status();

				if (!bRequest) {
					
					List<Entity_Coordenadas> lista_coordenadas = new ArrayList<Entity_Coordenadas>();
					arrNomEst = new String[lista_establecimiento.size()];
					arrDirEst = new String[lista_establecimiento.size()];
					arrIdEstt = new int[lista_establecimiento.size()];
					
					map2_IdEs_Cola = DAO_Establecimiento.getMap_IdEs_Cola();

					int a = 0;
					for (Entity_Establecimiento esta : lista_establecimiento) {
						lista_coordenadas = esta.getCoordenadas();

						arrNomEst[a] = esta.getNombre();
						arrDirEst[a] = esta.getDireccion();
						arrIdEstt[a] = esta.getEstablecimientoID();
						a++;
					}

					int c = 0;
					for (Entity_Coordenadas coor : lista_coordenadas) {

						lat = coor.getLatitud();
						lon = coor.getLongitud();
						
						setUpMap(lat, lon, arrNomEst[c], arrDirEst[c] , arrIdEstt[c]);
						c++;
					}
				}

			} catch (Exception e) {
				bRequest = true; // Hubo error
			}
			return bRequest;
		}

		
		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);

			try {
			} catch (Exception e) {
				Log.e("TAG-PROGGRESS", "No se pudo cerrar el dialogo");
			}

			if (!result) {

				try {
					refreshMenuItem.collapseActionView();
					refreshMenuItem.setActionView(null);
				} catch (Exception e) {

				}

				actionBar.setSubtitle("Ok!");
			} else {

				try {
					refreshMenuItem.collapseActionView();
					refreshMenuItem.setActionView(null);
				} catch (Exception e) {

				}

				actionBar.setSubtitle("Error!");
				myToast("No se encontró ningún establecimiento.", 1000);
			}
		}

	} // End Async


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.mapa_menu, menu);
		
	        // Associate searchable configuration with the SearchView
	        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	         searchView = (SearchView) menu.findItem(R.id.action_search)
	                .getActionView();
	        
	        searchView.setSearchableInfo(searchManager
	                .getSearchableInfo(getComponentName()));
	        searchView.setSubmitButtonEnabled(true);
	        searchView.setOnQueryTextListener(this);

	        
	        int id = searchView.getContext()
	                   .getResources()
	                   .getIdentifier("android:id/search_src_text", null, null);
			TextView textView = (TextView) searchView.findViewById(id);
			textView.setTextColor(Color.BLACK);
			
//	        query = searchView.getQuery();
//	        myToast(String.valueOf(query), 500);
	        return super.onCreateOptionsMenu(menu);
	}




	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		actionBar = getActionBar();

		switch (item.getItemId()) {
		
		case R.id.action_search:
			myToast(String.valueOf(query), 1000);

		case R.id.cargar_establ_acc:

			if (!oRoutine.isOnline(getApplicationContext()))
				Toast.makeText(getApplicationContext(),
						"Necesita tener conexión a Internet.",
						Toast.LENGTH_SHORT).show();
			else {

				setMensaje(TAG_UPDATE);

				try {
					refreshMenuItem = item;
					hideRates();
					myMarker.hideInfoWindow();
					map.clear();

					new EstablecimientoAsync().execute(arrCategory);

				} catch (Exception e) {
					map.clear();
					new EstablecimientoAsync().execute(arrCategory);
				}
			}

			break;

		case R.id.about_acc:
			LoadInfo();
			break;

		case R.id.logout_acc:
			logout();
			break;
			
		case R.id.action_add_map:
			addMap();
			break;
			

		default:
			break;
		}

		return true;
	}

	private void addMap() {
		Intent inAddMap = new Intent(getApplicationContext(), Mapa_AddEstablecimiento_Activity.class);
		startActivity(inAddMap);
	}

	private void LoadInfo() {
		final View v;
		
		AlertDialog.Builder adInfo = new AlertDialog.Builder(
				App_GPSMapa_Activity.this);

		LayoutInflater layInfo = this.getLayoutInflater();
		v = layInfo.inflate(R.layout.dialog_custom_info, null);
		adInfo.setView(v);

		adInfo.setNeutralButton("Ok", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();

			}
		});

		adInfo.show();
	}

	@Override
	public void onInfoWindowClick(final Marker marker) {
		
		map.setInfoWindowAdapter(new Adapter_InfoWindow(getLayoutInflater()));
				
		Intent intent = new Intent(getApplicationContext(),
				Usuario_Comentar_Activity.class);
		
		intent.putExtra("establecimientoID", oInfoWindow.getIdEst());
		intent.putExtra("nomEstablecimiento", oInfoWindow.getNombre());
		intent.putExtra("direccion", oInfoWindow.getDireccion());
		intent.putExtra("usuarioID", usuarioID);
		intent.putExtra("cola", oInfoWindow.getCola());
		
		startActivity(intent);

	}


	@Override
	public final boolean onMarkerClick(final Marker arg0) {
//		actionBar.hide();

	    map.setInfoWindowAdapter(new Adapter_InfoWindow(getLayoutInflater()));
		
		String sAll = arg0.getSnippet();
		String sIdEst = sAll.substring(sAll.length() - 4);
		String sDirec = sAll.substring(0, sAll.length() - 4);
		
		oInfoWindow.setDireccion(sDirec);
		oInfoWindow.setNombre(arg0.getTitle());
		oInfoWindow.setCola(map2_IdEs_Cola.get(Integer.parseInt(sIdEst)));
		
		runAsyncGetLasRate(Integer.parseInt(sIdEst));
		
		setMyMarker(arg0);
		showRates();
	   
		return false;
	}

	

	private void loadSpinnerNav() {

		actionBar = getActionBar();

		actionBar.setDisplayShowTitleEnabled(false);

		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

		arrAdpSpinner = new ArrayList<Adapter_SpinnerItem>();
		arrAdpSpinner.add(new Adapter_SpinnerItem(R.drawable.ic_action_place,
				"Seleccione..."));
		arrAdpSpinner.add(new Adapter_SpinnerItem(R.drawable.fast_foods,
				"Fast foods"));
		arrAdpSpinner.add(new Adapter_SpinnerItem(R.drawable.cines, "Cines"));
		arrAdpSpinner.add(new Adapter_SpinnerItem(R.drawable.cafes, "Cafes"));
		arrAdpSpinner.add(new Adapter_SpinnerItem(R.drawable.restaurantes,
				"Restaurantes"));
		arrAdpSpinner.add(new Adapter_SpinnerItem(R.drawable.banco, "Bancos"));
		arrAdpSpinner.add(new Adapter_SpinnerItem(R.drawable.organismos,
				"Organizaciones"));

		oAdpSpinner = new Adapter_SpinnerNavActionBar(getApplicationContext(),
				arrAdpSpinner);
		actionBar.setListNavigationCallbacks(oAdpSpinner, this);
	}
	


	
	public void logout() {
		final View v;

		AlertDialog.Builder alertDialog = new AlertDialog.Builder(
				App_GPSMapa_Activity.this);

		LayoutInflater layInflater = this.getLayoutInflater();
		v = layInflater.inflate(R.layout.dialog_custom_logout, null);
		alertDialog.setView(v);

		/* When positive (yes/ok) is clicked */
		alertDialog.setPositiveButton("No",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
						
						
					}
				});

		/* When negative (No/cancel) button is clicked */
		alertDialog.setNegativeButton("Si",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						actionBar.setSubtitle("Chau");
						Helper_SharedPreferences oShared = new Helper_SharedPreferences();
						oShared.storeStatus(0, getApplicationContext());// 0 =>
																		// Inicia
																		// desde
						Intent i = new Intent(getApplicationContext(),
								Usuario_Login_Activity.class);
						startActivity(i);
						Helper_constants.bIsLoginFacebook = false;

						finish();

					}
				});
		alertDialog.show();
	}

	
	
	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {

		String sCatagoria = new String();
		sCatagoria = arrAdpSpinner.get(itemPosition).getCategory();

		if (!sCatagoria.equals("-- Seleccione --")) {
			map.clear();
			setMensaje(TAG_UPDATE);
		}

		if (!oRoutine.isOnline(getApplicationContext()))
			Toast.makeText(getApplicationContext(),
					"Necesita tener conexión a Internet.", Toast.LENGTH_SHORT)
					.show();
		else {

			switch (sCatagoria) {
			case "Fast foods":
				arrCategory[0] = String.valueOf(1);
				new EstablecimientoAsync().execute(arrCategory);
				break;

			case "Cines":
				arrCategory[0] = String.valueOf(2);
				new EstablecimientoAsync().execute(arrCategory);
				break;

			case "Cafes":
				arrCategory[0] = String.valueOf(3);
				new EstablecimientoAsync().execute(arrCategory);
				break;

			case "Restaurantes":
				arrCategory[0] = String.valueOf(4);
				new EstablecimientoAsync().execute(arrCategory);
				break;

			case "Bancos":
				arrCategory[0] = String.valueOf(5);
				new EstablecimientoAsync().execute(arrCategory);
				break;

			case "Organizaciones":
				arrCategory[0] = String.valueOf(6);
				new EstablecimientoAsync().execute(arrCategory);
				break;

			default:
				break;
			}
		}

		return false;
	}

	public void myToast(String text,int duration){
		
		 Toast toast = Toast.makeText(getApplicationContext(), text, duration);
	     toast.show();

	}
	
	@Override
	public void onMapClick(LatLng position) {
		hideRates();
		
		try {
			mapAdd.clear();
		} catch (Exception e) {
		}
		
		mapAdd.addMarker(markerOptions
					      .position(position)
					      .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_add_marker_64))
					      .draggable(true));
		

	
//		Toast.makeText(getApplicationContext(),"Click Map!", Toast.LENGTH_LONG).show();
	}

	/************ SEARCH *************/
	@Override
	public boolean onQueryTextSubmit(String query) {

			myToast("Buscando... " + query,1000);
//			finish();
			if (!query.isEmpty()) {
				map.clear();
				setMensaje(TAG_UPDATE);
			    searchView.clearFocus();
			    searchView.setQuery("", false);
			    searchView.setFocusable(false);
			    searchView.onActionViewCollapsed();
			
//				    searchMenuItem.collapseActionView();
//				    isSearchFragmentVisible(false);
				
				arrCategory[0] = String.valueOf(-1);
				arrCategory[1] = query;
				new EstablecimientoAsync().execute(arrCategory);
			}
			
		return false;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		//TODO if is necesary.
		return false;
	}
	
	
	class SearchAsync extends AsyncTask<Void, Boolean, Boolean>{

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
		}
		
		@Override
		protected Boolean doInBackground(Void... params) {
			boolean b = false;
			
			return b;
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
				
			
		}
		
	}
	

}
