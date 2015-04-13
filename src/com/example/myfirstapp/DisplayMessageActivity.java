package com.example.myfirstapp;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.R.bool;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class DisplayMessageActivity extends ActionBarActivity {
	
	//private WebView webView;
	private SimpleAdapter adapter;
	private ListView listView;
    private List<Map<String, String>> data= null;
    public boolean flag;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Get the message from the intent
	    setContentView(R.layout.activity_display_message);
	    Intent intent = getIntent();
	    final String message_x = intent.getStringExtra("message1");
	    final String message_y = intent.getStringExtra("message2");
	    flag = intent.getBooleanExtra("message3", false);
	    String message = message_x+" "+message_y;
	    
	    String string= null;
	    
	    Button button = (Button)findViewById(R.id.button_sendchannel);
	    button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
		    	Intent intent = new Intent(DisplayMessageActivity.this, JoinActivity.class);
		    	EditText editText_x = (EditText) findViewById(R.id.edit_channel);
		    	String message = editText_x.getText().toString();
		    	message= message;
		    	String message1	=message_x+" "+message_y;
		    	intent.putExtra("message1", message);
		    	intent.putExtra("message3", message1);
		    	
		    	if(flag)
		    		intent.putExtra("message2", true);
		    	else 
		    		intent.putExtra("message2", false);
		    	

		    	
		    	startActivity(intent);
			}
		});
	   
	    // Create the text view
	    //TextView textView = (TextView) findViewById(R.id.channel);
	   // textView.setTextSize(10);
		if(flag){
			string = "http://54.254.157.142/paid.php";
		}
		else {
			string = "http://54.254.157.142/free.php";
		}
	    URL url;
		//String message_1="";
	   /* webView = (WebView) findViewById(R.id.webview1);
	    webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
	    webView.clearHistory();
        webView.clearFormData();
        webView.clearCache(true);
	    webView.loadUrl(string);*/
	    
	    
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
			message_return = httpClient.grab("message=" + URLEncoder.encode(message, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		data = MyData.getDataSource(message_return);
        listView = (ListView)this.findViewById(R.id.listview);
        adapter = new SimpleAdapter(this, data, R.layout.listitem, new String[]{"channel","interference"}, new int[]{R.id.channel_item,R.id.interference_item});
        listView.setAdapter(adapter);
		//textView.setText(message_return);
	    //HttpClientFYP.cookie
	    // Set the text view as the activity layout

	    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
			View rootView = inflater.inflate(R.layout.fragment_display_message,
					container, false);
			return rootView;
		}
	}
}
