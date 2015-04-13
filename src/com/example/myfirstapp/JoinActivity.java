package com.example.myfirstapp;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class JoinActivity extends Activity {
	
	private Timer timer;
	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_join);
		timer = new Timer();
		handler = new Handler(){
			public void handleMessage(android.os.Message msg) 
			{
				switch(msg.what){
				case 1:
					URL url;
					HttpClientFYP httpClient2 = new HttpClientFYP();
					try {
						url = new URL("http://54.254.157.142/end.php");
						httpClient2.setURL(url);
					} catch (MalformedURLException e1) {
						// TODO Auto-generated catch block
						//textView.setText(e1.toString());
					}
					String message="";
					String message_return = "";
					timer.cancel();
					try {
						message_return = httpClient2.grab("message=" + URLEncoder.encode(message, "UTF-8"));
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(message_return.contentEquals("1")){
						Intent intent = new Intent(JoinActivity.this, MainActivity.class);
						/*String string = "disconnect from the server";
						TextView textView1 = (TextView) findViewById(R.id.message);
						textView1.setTextSize(20);
						textView1.setText(string);*/
						startActivity(intent);
					}
					else{
						String string = "disconnect from the server failed";
						TextView textView1 = (TextView) findViewById(R.id.message);
						textView1.setTextSize(20);
						textView1.setText(string);
					}
					break;
					
				}
			};
		};
		
		TextView textView = (TextView) findViewById(R.id.message);
		textView.setTextSize(20);
		Intent intent = getIntent();
		String message = intent.getStringExtra("message1");
		String message1 = intent.getStringExtra("message3");
		String message_send = message+" "+message1;
		Boolean flag = intent.getBooleanExtra("message2", false);
		
		String string = null;
		
		if(flag){
			string = "http://54.254.157.142/paidchannel.php";
		}
		else {
			string = "http://54.254.157.142/freechannel.php";
		}
	    URL url;
	
	    
	    HttpClientFYP httpClient = new HttpClientFYP();
	    try {
			url = new URL(string);
			httpClient.setURL(url);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			//textView.setText(e1.toString());
		}
	    
	    String message_return = "";
		try {
			message_return = httpClient.grab("message=" + URLEncoder.encode(message_send, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(message_return.contentEquals("1")){
			String printmessage= "you have joined in Channel :"+message;
			textView.setText(printmessage);
		}
		else{
			new AlertDialog.Builder(this)
			.setTitle("connect failed")
			.setMessage("please reconnect")
			.setPositiveButton("OK", null)
			.show();
		}
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				timer.schedule(new TimerTask() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						Message message = new Message();      
						message.what = 1;      
						handler.sendMessage(message);
						
						
					}
				}, 1000 * 30);
				// TODO Ato-generated method stub
				
			}
		}).start();
		
		Button button = (Button)findViewById(R.id.disconnect);
	    button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				URL url;
				HttpClientFYP httpClient2 = new HttpClientFYP();
				try {
					url = new URL("http://54.254.157.142/end.php");
					httpClient2.setURL(url);
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					//textView.setText(e1.toString());
				}
				String message="";
				String message_return = "";
				timer.cancel();
				try {
					message_return = httpClient2.grab("message=" + URLEncoder.encode(message, "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(message_return.contentEquals("1")){
					Intent intent = new Intent(JoinActivity.this, MainActivity.class);
					/*String string = "disconnect from the server";
					TextView textView1 = (TextView) findViewById(R.id.message);
					textView1.setTextSize(20);
					textView1.setText(string);*/
					startActivity(intent);
				}
				else{
					String string = "disconnect from the server failed";
					TextView textView1 = (TextView) findViewById(R.id.message);
					textView1.setTextSize(20);
					textView1.setText(string);
				}
				// TODO Auto-generated method stub
		    	/*Intent intent = new Intent(DisplayMessageActivity.this, JoinActivity.class);
		    	EditText editText_x = (EditText) findViewById(R.id.edit_channel);
		    	String message_x = editText_x.getText().toString();
		    	intent.putExtra("message1", message_x);

		    	
		    	startActivity(intent);*/
			}
		});
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.join, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_join, container,
					false);
			return rootView;
		}
	}
}