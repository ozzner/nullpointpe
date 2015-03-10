package com.apprade.activity;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.apprade.R;
import com.apprade.dao.DAO_Usuario;
import com.apprade.entity.Entity_Ranking;
import com.apprade.helper.Helper_SharedPreferences;
import com.apprade.helper.Helper_SubRoutines;
import com.apprade.helper.Helper_constants;
import com.facebook.HttpMethod;
/*facebook*/
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
/* google +*/
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.Validator.ValidationListener;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.mobsandgeeks.saripaar.annotation.TextRule;




public class Usuario_Login_Activity extends Activity implements ValidationListener, 
ConnectionCallbacks, OnConnectionFailedListener {
	

	private Validator validator;
	@TextRule(order = 2, minLength = 3, message = "Ingrese min 3 caracteres.")
	@Required(order = 1, message = "Este campo es requerido.")
	private EditText password, email;
	private Button btnLogin;
	private DAO_Usuario dao;
	public Entity_Ranking rank;
	private String sEmail = "", sPassword = "";
	private ActionBar actionBar;
	private ProgressDialog proDialogo;
	private Helper_SubRoutines oRoutine;
	private static final String TAG_VACIO = "";
	private static final String TAG = Usuario_Login_Activity.class.getSimpleName();
	private static final String MY_PACKAGE = "com.apprade";

	// private SharedPreferences mPrefs;

	
	
	/************* google + *************/
	private static final int RC_SIGN_IN = 0;

    private GoogleApiClient mGoogleApiClient;
    private boolean mIntentInProgress;
    private boolean mSignInClicked;
    private ConnectionResult mConnectionResult;
    private SignInButton btnGoogle;
	private String personName;
	private int personGender;
//	private String personUserID;
	private String personBirthday;
	private String sSex;
	
	/************ facebook **************/
	private LoginButton btn_facebook;
	UiLifecycleHelper uihelper;
	Intent data;
	
   
	
	
	public Usuario_Login_Activity() {
		super();
		oRoutine = new Helper_SubRoutines();
		rank = new Entity_Ranking();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usuario_login);
		dao = new DAO_Usuario(getApplicationContext());
		
		genHashKey();
		getActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));  
		
		/* Inicializing views */
		email = (EditText) findViewById(R.id.txtEmail);
		password = (EditText) findViewById(R.id.txtPassword);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnGoogle = (SignInButton) findViewById(R.id.btn_sign_in);

		btnGoogle = (SignInButton) findViewById(R.id.btn_sign_in);
		btn_facebook = (LoginButton)findViewById(R.id.authButton);
		btn_facebook.setReadPermissions(Arrays.asList("public_profile","email","user_birthday"));
		
	   
		
		/* Setting text */
		email.setText(getIntent().getStringExtra("correo"));
		password.setText(getIntent().getStringExtra("password"));

		// Initializing google plus api client
		mGoogleApiClient = new GoogleApiClient.Builder(this)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this).addApi(Plus.API)
				.addScope(Plus.SCOPE_PLUS_LOGIN).build();
		
		// Initializing facebook
		 checkLoginFacebookActive();
		 uihelper=new UiLifecycleHelper(Usuario_Login_Activity.this,callback);
	     uihelper.onCreate(savedInstanceState);

		validator = new Validator(this);
		validator.setValidationListener(this);

		btnGoogle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showMyToast("Login google+");
				mGoogleApiClient.connect();
			    mSignInClicked = true;
				signInWithGplus();
			}
		});
		
		
		
		// Initializing google plus api client
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();

			
		btnLogin.setOnClickListener( new OnClickListener() {			
			
			@Override
			public void onClick(View v) {
				validator.validate();
			}
		});
		
		
		btn_facebook.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
//			    signOutFromGplus();
//			    revokeGplusAccess();
//				myToast("Exiting...");
				}
			});
		

		
		}//End onCreated

	private void checkLoginFacebookActive() {
		
		if (Session.getActiveSession() != null) 
		 {
		  Session.getActiveSession().closeAndClearTokenInformation();
//		  session.closeAndClearTokenInformation();
		  Log.e(TAG, "Session is not null " + Session.getActiveSession());
		 }
		Session.setActiveSession(null);
	}

	private void genHashKey() {

		  try {
		 	 	PackageInfo info = getPackageManager().getPackageInfo(MY_PACKAGE, 
			     	     	 PackageManager.GET_SIGNATURES);
			  	for (Signature signature : info.signatures) 
				{
			     	 MessageDigest md = MessageDigest.getInstance("SHA");
			     	 md.update(signature.toByteArray());
			    	    Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
				       }
				}

				catch (Exception e) 
				{
				   e.printStackTrace();	
				}		
	}

	public void showMyToast(String text) {
		Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT)
				.show();
	}

	protected boolean validarCampos() {
		boolean esError = false;

		sEmail = email.getText().toString();
		sPassword = password.getText().toString();

		if (sEmail.compareTo("") == 0) {
			email.setError("Debes ingresar un Correo");

		} else if (sPassword.compareTo("") == 0) {
			password.setError("Debes ingresar un Password");

		} else {
			esError = true;
		}

		return esError;

	}

	protected void showDialogo() {
		
		proDialogo = new ProgressDialog(Usuario_Login_Activity.this);
		proDialogo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		proDialogo.setCancelable(false);
		proDialogo.setMessage("Conectando...");
		proDialogo.show();
		
	}

	protected void llamarMapa() {

		Intent mapa = new Intent(getApplicationContext(),
				App_GPSMapa_Activity.class);
		Log.e("oUserID - login", dao.oUsuario.getUsuarioID() + "");
		mapa.putExtra("user_id", dao.oUsuario.getUsuarioID());
		mapa.putExtra("user", dao.oUsuario.getNombre());
		startActivity(mapa);
		finish();

	}

	class TaskHttpMethodAsync extends AsyncTask<String, Void, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
			boolean bRequest = false;

			sEmail = email.getText().toString();
			sPassword = password.getText().toString();

			if (dao.loginUsuario(sEmail, sPassword, 1))
				bRequest = true;

			return bRequest;
		}

		@Override
		protected void onPreExecute() {

			showDialogo();

			proDialogo.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					TaskHttpMethodAsync.this.cancel(true);
				}
			});
			proDialogo.setProgress(0);
			// pDialog.show();
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			proDialogo.dismiss();

			if (result) {
				llamarMapa();
				Helper_SharedPreferences oShared = new Helper_SharedPreferences();
				// oShared.getAllLoginDataStored(getApplicationContext());

				oShared.storeLogin(dao.oUsuario.getNombre(),
						dao.oUsuario.getEmail(), dao.oUsuario.getUsuarioID(),
						1, getApplicationContext());

			} else {
				Toast.makeText(
						getApplicationContext(),
						dao.oJsonStatus.getMessage() + ". "
								+ dao.oJsonStatus.getInfo() + ".",
						Toast.LENGTH_SHORT).show();
				password.setText(TAG_VACIO);
				email.setText(TAG_VACIO);
			}
		}

		@Override
		protected void onCancelled() {
		}

	}// End ClassAsync

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.login_menu, menu);

		return true;
	}


	public boolean onOptionsItemSelected(MenuItem item) {


		actionBar = getActionBar();
		switch (item.getItemId()) {

		case R.id.log_login_action:
			actionBar.setSubtitle("Login");
			validator.validate();
			break;

		case R.id.log_about_action:

			actionBar.setSubtitle("Acerca de");
			LoadInfo();

			break;

		case R.id.log_registro_action:

			Intent i = new Intent(getApplicationContext(),
					Usuario_Registro_Activity.class);
			startActivity(i);
			finish();
			break;

		default:
			break;
		}

		return true;
	}

	private void LoadInfo() {
		final View v;
		AlertDialog.Builder adInfo = new AlertDialog.Builder(
				Usuario_Login_Activity.this);

		LayoutInflater layInfo = this.getLayoutInflater();
		v = layInfo.inflate(R.layout.dialog_custom_about, null);
		adInfo.setView(v);

		adInfo.setNeutralButton("Okay", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();

			}
		});

		adInfo.show();
	}

	public void tvRegistrar_onClick(View v) {

		Intent i = new Intent(this, Usuario_Registro_Activity.class);
		startActivity(i);
		finish();
	}

	@Override
	public void onValidationSucceeded() {

		if (oRoutine.isOnline(getApplicationContext()))
			new TaskHttpMethodAsync().execute();
		else
			Toast.makeText(getApplicationContext(),
					"Necesita tener conexión a Internet.", Toast.LENGTH_SHORT)
					.show();

	}

	@Override
	public void onValidationFailed(View failedView, Rule<?> failedRule) {

		String message = failedRule.getFailureMessage();

		if (failedView instanceof EditText) {
			failedView.requestFocus();
			((EditText) failedView).setError(message);
		} else {
			Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
		}
	}


	

	

	public void myToast(String text) {
		Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
	}

	
	
	/******************* AsyncTask *****************/

	class googlePlusRegisterAsync extends AsyncTask<String, Void, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
			boolean bRequest = false;

			if (!dao.loginUsuario(sEmail, sPassword, 2)) {
				bRequest = dao.registarUsuario(sEmail, sSex, personName,
						sPassword, personBirthday);
			} else {
				bRequest = true;
			}
			
			return bRequest;
		}

		@Override
		protected void onPreExecute() {
			showDialogo();
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			proDialogo.dismiss();

			if (result) {
				Helper_SharedPreferences oShared = new Helper_SharedPreferences();
				// oShared.getAllLoginDataStored(getApplicationContext());

				oShared.storeLogin(dao.oUsuario.getNombre(),
						dao.oUsuario.getEmail(), dao.oUsuario.getUsuarioID(),
						1, getApplicationContext());
				llamarMapa();
			} else {
				myToast("Error. " + dao.oJsonStatus.getInfo());
			}

		}

}

	
	
	
	/******************* facebook *****************/
	
    private Session.StatusCallback callback = new Session.StatusCallback(){
    	 
      public void call(Session session, SessionState state, Exception exception) {
//    	  session.closeAndClearTokenInformation();
    	  onSessionChanged(session,state,exception);
      };
    };
    
    

	private void onSessionChanged(Session session, SessionState sessionState, Exception e) {

        if(sessionState.isOpened())
        {
        	
        	if (!Helper_constants.bIsLoginFacebook) {
        		
        		Helper_constants.bIsLoginFacebook = true;
        		 new Request(
               		    session,
               		    "/me",
               		    null,
               		    HttpMethod.GET,
               		    new Request.Callback() {
               		        public void onCompleted(Response response) {
               		        	
               		        	JSONObject data = response.getGraphObject().getInnerJSONObject();
               		        	
               		        	try {
               		        		
     								sPassword = data.get("id").toString();
     								personName = data.getString("name");
     								sEmail = data.getString("email");
     								String sSexo = data.getString("gender");
     								personBirthday = data.getString("birthday");
     								
     								if (sSexo.equals("male")) {
     									sSex = "M";
     								}else{
     									sSex = "F";
     								}
     								
     								new facebookRegisterAsync().execute();
     								
     								
     							} catch (JSONException e) {
     								e.printStackTrace();
     							}
               		        	
               		            Log.e(TAG, data.toString());
               		        }
               		    }
               		).executeAsync();	
			}
        	
          	
            
          	          	 
           
        }
        else if(sessionState.isClosed())
        {
        	Helper_constants.bIsLoginFacebook = false;
            Toast.makeText(this,"Usuario desconectado.",Toast.LENGTH_LONG).show();
        }
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	 uihelper.onPause();
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uihelper.onSaveInstanceState(outState);
    }
    
    
    @Override
    protected void onResume() {
        super.onResume();

        Session session = Session.getActiveSession();
        if (session != null &&
                (session.isOpened() || session.isClosed()) ) {
        	onSessionChanged(session, session.getState(), null);
        }

        uihelper.onResume();
        
    }
    
    /******************* AsyncTask Facebook *****************/
	class facebookRegisterAsync extends AsyncTask<String, Void, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
			boolean bRequest = false;

			if (!dao.loginUsuario(sEmail, sPassword, 2)) {
				bRequest = dao.registarUsuario(sEmail, sSex, personName,
						sPassword, personBirthday);
			} else {
				bRequest = true;
			}
			
			return bRequest;
		}

		@Override
		protected void onPreExecute() {
			try {
				proDialogo.dismiss();
			} catch (Exception e) {
			}
			
//			showDialogo();
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
//			proDialogo.dismiss();

			if (result) {
				
				Helper_SharedPreferences oShared = new Helper_SharedPreferences();
				// oShared.getAllLoginDataStored(getApplicationContext());
				 Toast.makeText(getApplicationContext(),"Usuario conectado.",Toast.LENGTH_LONG).show();
				oShared.storeLogin(dao.oUsuario.getNombre(),
						dao.oUsuario.getEmail(), dao.oUsuario.getUsuarioID(),
						1, getApplicationContext());
				Helper_constants.bIsLoginFacebook = true;
				llamarMapa();
			} else {
				myToast("Error. " + dao.oJsonStatus.getInfo());
			}

		}

}
    
    
    
    
    
	/******************* Google+ *****************/
	@Override
	public void onConnectionFailed(ConnectionResult result) {

		 if (!result.hasResolution()) {
	            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
	                    0).show();
	            return;
	        }
	     
	        if (!mIntentInProgress) {
	            // Store the ConnectionResult for later usage
	            mConnectionResult = result;
	     
	            if (mSignInClicked) {
	                // The user has already clicked 'sign-in' so we attempt to
	                // resolve all
	                // errors until the user is signed in, or they cancel.
	                resolveSignInError();
	            }
	        }
	}

	@Override
	public void onConnected(Bundle arg0) {
		mSignInClicked = false;
		myToast("User conected!");

		try {
			if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
				
				Person currentPerson = Plus.PeopleApi
						.getCurrentPerson(mGoogleApiClient);
				personName = currentPerson.getDisplayName();
				sPassword = currentPerson.getId();
				personGender = currentPerson.getGender();
//				personUserID = currentPerson.getId();
				personBirthday = currentPerson.getBirthday();
				sEmail = Plus.AccountApi.getAccountName(mGoogleApiClient);

				if (personGender == 0) {
					sSex = "M";
				} else {
					sSex = "F";
				}

				if (personBirthday == null) {
					personBirthday = oRoutine
							.getCurrentTime(Helper_SubRoutines.TAG_FORMAT_DATE_MM);
				}

				Log.e(TAG, "Name: " + personName + ", email: " + sEmail
						+ ", cumpleaños " + personBirthday + ", gender: "
						+ personGender);

				new googlePlusRegisterAsync().execute();

				// by default the profile url gives 50x50 px image only
				// we can replace the value with whatever dimension we want by
				// replacing sz=X

			} else {
				myToast("No hay información.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        
	}

	
	@Override
	public void onConnectionSuspended(int arg0) {
	       mGoogleApiClient.connect();
	}

	   private void signOutFromGplus() {
		   
	        if (mGoogleApiClient.isConnected()) {
	            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
	            mGoogleApiClient.disconnect();
	            mGoogleApiClient.connect();
	        }
	        
	    }
	

	
    protected void onStart() {
        super.onStart();
//        mGoogleApiClient.connect();
    }
 
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }
 
	
    @Override
    protected void onActivityResult(int requestCode, int responseCode,
            Intent intent) {
    	/*Facebook*/
//    	Session.getActiveSession().onActivityResult(this, requestCode, responseCode, intent);
        uihelper.onActivityResult(requestCode,responseCode,intent);
        
        /*Google+*/
        if (requestCode == RC_SIGN_IN) {
            if (responseCode != RESULT_OK) {
                mSignInClicked = false;
            }
     
            mIntentInProgress = false;
     
            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }
    }
     

    private void signInWithGplus() {
    	  
        if (!mGoogleApiClient.isConnecting()) {
             mSignInClicked = true;
             resolveSignInError();
        }
    }
	
	
    private void resolveSignInError() {
    	
        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
            } catch (SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }
	
	
    private void revokeGplusAccess() {
    	
        Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
        Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient)
               .setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status arg0) {
                        Log.e(TAG, "User access revoked!");
                        mGoogleApiClient.connect();
                    }
 
      });
        
        if (mGoogleApiClient.isConnected()) {
        	
       
        }
    }
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	signOutFromGplus();
        uihelper.onDestroy();
    }
	
}	
	
	
	
	
	
	
	
	










