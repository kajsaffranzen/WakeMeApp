//SVARAT RÄTT PÅ FRÅGA BARA ETT OK

package com.example.wakemeapp;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class LastActivity extends Activity {

	//MediaPlayer alarmTune;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_last);
		Typeface tf = Typeface.createFromAsset(getAssets(),
                "font/Roboto-Thin.ttf");
    TextView tv = (TextView) findViewById(R.id.btnNext);
        tv.setTypeface(tf);	
	TextView tv2 = (TextView) findViewById(R.id.textView1);	       			
	   tv2.setTypeface(tf);
	TextView tv3 = (TextView) findViewById(R.id.textView2);	       			
		tv3.setTypeface(tf);  
		
		
		

	}
	
	public void onClick(View v) {}
	
	//knapp till nästa
	public void goNext(View v)
	{
		startActivity(new Intent(LastActivity.this, MainActivity.class));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present
		getMenuInflater().inflate(R.menu.last, menu);
		
		return true;
	}

}
