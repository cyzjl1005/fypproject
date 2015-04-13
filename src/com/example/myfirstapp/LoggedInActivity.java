package com.example.myfirstapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class LoggedInActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logged_in);
		Intent intent = getIntent();
	    String username = intent.getStringExtra("username");
	}

	/** Called when the user clicks the Send button */
    public void sendMessage(View view) {
    	Intent intent = new Intent(this, DisplayMessageActivity.class);
    	EditText editText_x = (EditText) findViewById(R.id.edit_message);
    	EditText editText_y = (EditText) findViewById(R.id.edit_location);
    	String message_x = editText_x.getText().toString();
    	String message_y = editText_y.getText().toString();
    	
    	if(check)
    		intent.putExtra("message3", true);
    	else 
    		intent.putExtra("message3", false);
    	intent.putExtra("message1", message_x);
    	intent.putExtra("message2", message_y);
    	
    	startActivity(intent);
        // Do something in response to button
    }
    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        if(checked)
        	check = true;
        else 
        	check = false;
        	
    }
    public boolean check = false;

    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

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
