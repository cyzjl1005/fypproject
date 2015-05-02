package com.example.myfirstapp;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap.OnMapLoadedCallback;

import android.app.AlertDialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.MapFragment;


public class MainActivity extends ActionBarActivity implements ConnectionCallbacks, OnConnectionFailedListener{

	private GoogleApiClient mGoogleApiClient;
	private static final int MSG_SUCCESS = 1;
	private static final int MSG_FAILURE = 0;
	private TextView mLatitudeText;
	private TextView mLongitudeText;
	
	private Button btn;
	private Thread mthread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("wcyz666", "error");
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Log.i("wcyz666", "error1");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        
    	mLatitudeText=(TextView) findViewById(R.id.textView1);
    	mLongitudeText=(TextView) findViewById(R.id.textView2);
        btn=(Button)findViewById(R.id.button1);
        btn.setOnClickListener(new OnClickListener() {  
            
            public void onClick(View view) {  
                mthread=new Thread(runnable);  
                mthread.start();  
                    
            }  
        });
//        MapFragment mapFragment = (MapFragment) getFragmentManager()
//       	    .findFragmentById(R.id.map);
        

           mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
           
           
        
    }
    
    @Override
    protected void onStart() {
        super.onStart();
         // more about this later
            mGoogleApiClient.connect();
        
    }
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }
    
    @Override
    public void onConnected(Bundle connectionHint) {
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        mLatitudeText.setText("123");
        if (mLastLocation != null) {

            mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
            mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
        } else {
        	mLatitudeText.setText("lat:failed");
        	mLongitudeText.setText("lon:failed");
        }
    }
    
    @Override
    public void onConnectionSuspended(int cause) {
        // The connection has been interrupted.
        // Disable any UI components that depend on Google APIs
        // until onConnected() is called.
    	mLatitudeText.setText("234");
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // This callback is important for handling errors that
        // may occur while attempting to connect with Google.
        //
        // More about this in the next section.
    	mLatitudeText.setText("345");
        
    }

    
    Runnable runnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			EditText edit_username = (EditText) findViewById(R.id.username);
	    	EditText edit_password = (EditText) findViewById(R.id.password);
	    	String username = edit_username.getText().toString();
	    	String password = edit_password.getText().toString();
	    	String logininfo = username+" "+password;
	    	String string="http://54.254.157.142/login.php";
	    	URL url;
	    	HttpClientFYP httpClient = new HttpClientFYP();
	    	String message_return = "";
		    try {
				url = new URL(string);
				httpClient.setURL(url);
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				//textView.setText(e1.toString());
			}
		    
		   
			try {
				message_return = httpClient.grab("message=" + URLEncoder.encode(logininfo, "UTF-8"));
				Log.i("bcd", message_return);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
			if(message_return.contentEquals("1")){
				mHandler.obtainMessage(MSG_SUCCESS).sendToTarget();
			}
			else{
				mHandler.obtainMessage(MSG_FAILURE).sendToTarget();
			}
		}
		
    	
    };
    
    private Handler mHandler = new Handler(){
    	 @Override
         public void handleMessage(Message msg) {
             switch (msg.what) {
             // if success, start intent
             case MSG_SUCCESS:
            	 EditText edit_username = (EditText) findViewById(R.id.username);
            	 String username = edit_username.getText().toString();
            	 Intent intent = new Intent(MainActivity.this, LoggedInActivity.class);
            	 intent.putExtra("username", username);
     			 startActivity(intent);
                 break;
             // or fail
             case MSG_FAILURE:
            	 new AlertDialog.Builder(MainActivity.this)
     			.setTitle("login failed")
     			.setMessage("your user ID or password is wrong")
     			.setPositiveButton("OK", null)
     			.show();
                 break;
             }
         }
    	
    };
    
    
    
    /** Called when the user clicks the Send button */
/*    public void login(View view) {
    	Intent intent = new Intent(this, LoggedInActivity.class);
    	EditText edit_username = (EditText) findViewById(R.id.username);
    	EditText edit_password = (EditText) findViewById(R.id.password);
    	String username = edit_username.getText().toString();
    	String password = edit_password.getText().toString();
    	String logininfo = username+" "+password;
    	String string="http://54.254.157.142/login.php";
    	URL url;
    	HttpClientFYP httpClient = new HttpClientFYP();
    	String message_return = "";
	    try {
			url = new URL(string);
			httpClient.setURL(url);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			//textView.setText(e1.toString());
		}
	    
	    
	   
		try {
			message_return = httpClient.grab("message=" + URLEncoder.encode(logininfo, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		if(message_return.contentEquals("1")){
			intent.putExtra("username", username);
			startActivity(intent);
		}
		else{
			new AlertDialog.Builder(this)
			.setTitle("login failed")
			.setMessage("your user ID or password is wrong")
			.setPositiveButton("OK", null)
			.show();
			
		}
    }*/

    
    


/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	 // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	// Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_search:
                openSearch();
                return true;
            case R.id.action_settings:
                openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void  openSearch(){
        //your code here
    	TextView search= new TextView(this);
    	search.setText("opensearch");
    	setContentView(search);
    }

    private void openSettings(){
         //your code here
    	TextView settings= new TextView(this);
    	settings.setText("opensettings");
    	setContentView(settings);
    }*/
        
}
    
