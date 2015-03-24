//NU RINGER ALARMET

package com.example.wakemeapp;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class AlarmActivity extends Activity {

    MediaPlayer alarmTune;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
		Typeface tf = Typeface.createFromAsset(getAssets(),
                "font/Roboto-Thin.ttf");
    TextView tv = (TextView) findViewById(R.id.btnNext);
        tv.setTypeface(tf);	
	TextView tv2 = (TextView) findViewById(R.id.textView1);	       			
	   tv2.setTypeface(tf);
	TextView tv3 = (TextView) findViewById(R.id.textView2);	       			
		tv3.setTypeface(tf);      
        //ljudet går igång 
        alarmTune = MediaPlayer.create(AlarmActivity.this, R.raw.alarm_sound);
        alarmTune.start();
        
        Calendar c = Calendar.getInstance();
         System.out.println("Current time => "+c.getTime());

         SimpleDateFormat df = new SimpleDateFormat("HH:mm");
         String formattedDate = df.format(c.getTime());
         // formattedDate have current date/time

         // Now we display formattedDate value in TextView
         TextView txtView = (TextView)findViewById(R.id.textView2);
         txtView.setText(formattedDate);
    }
   
    public void goNext(View v){
    	alarmTune.stop();
        startActivity(new Intent(AlarmActivity.this, QuestionActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.alarm, menu);
        return true;
    }

}