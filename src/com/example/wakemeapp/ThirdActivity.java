//DU SKA UPP OM HH:MM:SS


package com.example.wakemeapp;

//import com.example.alarm.R;

//import com.example.alarm.MainActivity;
//import com.example.alarm.SecondActivity;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class ThirdActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
       
        //Tar emot string från main
        SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String texten = app_preferences.getString("key3", "null");
		Typeface tf = Typeface.createFromAsset(getAssets(),
                "font/Roboto-Thin.ttf");
    TextView tv = (TextView) findViewById(R.id.btnNext);
        tv.setTypeface(tf);	
	TextView tv2 = (TextView) findViewById(R.id.textView2);	       			
	   tv2.setTypeface(tf);
	TextView tv3 = (TextView) findViewById(R.id.textView1);	       			
		tv3.setTypeface(tf);	      
        //Skriver ut string
          TextView txtView = (TextView)findViewById(R.id.textView2);
        txtView.setText(texten);
    }
   
    public void goNext(View v){
        startActivity(new Intent(ThirdActivity.this, FourthActivity.class));
    }
   

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.third, menu);
        return true;
    }

}
