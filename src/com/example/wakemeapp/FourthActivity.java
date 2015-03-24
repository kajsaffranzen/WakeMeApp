package com.example.wakemeapp;

import java.util.Calendar;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

public class FourthActivity extends Activity {
  
	TextView txtView;
    TextView txtAlarmPrompt;
    Button buttonstartSetDialog;
    TimePickerDialog timePickerDialog;
    final static int RQS_1 = 1;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);
		Typeface tf = Typeface.createFromAsset(getAssets(),
                "font/Roboto-Thin.ttf");
    TextView tv = (TextView) findViewById(R.id.btnSet);
        tv.setTypeface(tf);	
	TextView tv2 = (TextView) findViewById(R.id.textView1);	       			
	   tv2.setTypeface(tf);
	TextView tv3 = (TextView) findViewById(R.id.textView2);	       			
		tv3.setTypeface(tf);      
        //Tar emot string från main
        SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String text = app_preferences.getString("key2", "null");
               
        
        
        //Skriver ut string
        TextView txtView = (TextView)findViewById(R.id.textView2);
        txtView.setText(text);
        
        txtAlarmPrompt = (TextView)findViewById(R.id.alarmprompt);     
        
        buttonstartSetDialog = (Button)findViewById(R.id.btnSet);
        buttonstartSetDialog.setOnClickListener(new OnClickListener(){
       
           
        @Override
        public void onClick(View v){
            txtAlarmPrompt.setText("");
            openTimePickerDialog(true);
        }});
    }
    
    private void openTimePickerDialog(boolean is24r){
        
        Calendar calendar = Calendar.getInstance();
   
        timePickerDialog = new TimePickerDialog(
                FourthActivity.this,
                onTimeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                is24r);
        timePickerDialog.setTitle("Sätt alarm");
   
        timePickerDialog.show();
}

//sŠtter alarmet
OnTimeSetListener onTimeSetListener = new OnTimeSetListener(){
   
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
       
        Calendar calNow = Calendar.getInstance();
        Calendar calSet = (Calendar) calNow.clone();
       
        calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calSet.set(Calendar.MINUTE, minute);
        calSet.set(Calendar.SECOND, 0);
        calSet.set(Calendar.MILLISECOND, 0);
       
        if(calSet.compareTo(calNow) <= 0) {
            //Today Set time passed, count to tomorrow
            calSet.add(Calendar.DATE, 1);
        }
   
        setAlarm(calSet);
    }};

private void setAlarm(Calendar targetCal){

    Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
    PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), RQS_1, intent, 0);
    AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
    alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);
    //FIXME
    //alarmManager.set(AlarmManager.RTC_WAKEUP, /*targetCal.getTimeInMillis()*/1000, pendingIntent);
       
    String alarm =  targetCal.get(Calendar.HOUR_OF_DAY)+"."+targetCal.get(Calendar.MINUTE);
    if(targetCal.get(Calendar.HOUR_OF_DAY) < 10 && targetCal.get(Calendar.MINUTE) < 10)
    {
    	alarm = "0" + targetCal.get(Calendar.HOUR_OF_DAY)+".0"+targetCal.get(Calendar.MINUTE);
    }
    else if (targetCal.get(Calendar.HOUR_OF_DAY) < 10)
    {
    	alarm = "0" + targetCal.get(Calendar.HOUR_OF_DAY)+"."+targetCal.get(Calendar.MINUTE);
    	
    }
    else if(targetCal.get(Calendar.MINUTE) < 10)
    {
    	alarm = targetCal.get(Calendar.HOUR_OF_DAY)+".0"+targetCal.get(Calendar.MINUTE);
    }
    //Skickar "targetCal" till FifthActivity om nŠr alarmet ringer
    SharedPreferences app_alarm =
            PreferenceManager.getDefaultSharedPreferences(FourthActivity.this);
    SharedPreferences.Editor edit1 = app_alarm.edit();
    edit1.putString("key2", alarm);
    edit1.commit();
   
    //rŠknar ut hur lŒng tid det Šr kvar tills alarmet ringer
    Calendar c = Calendar.getInstance();
    int hours = targetCal.get(Calendar.HOUR_OF_DAY)-c.get(Calendar.HOUR_OF_DAY);
    int minutes = targetCal.get(Calendar.MINUTE)-c.get(Calendar.MINUTE);
   
    if(hours < 0)
    {
        hours = 24 + (targetCal.get(Calendar.HOUR_OF_DAY)-c.get(Calendar.HOUR_OF_DAY));
       
        if(minutes < 0)
        {
            minutes = 60 + (targetCal.get(Calendar.MINUTE)-c.get(Calendar.MINUTE));
        }
    }
    else if(hours>0 && minutes <0 )
    {
    	hours = hours-1;
    	minutes = 60 +(targetCal.get(Calendar.MINUTE)-c.get(Calendar.MINUTE));
    }
    else if(minutes < 0 && hours <0)
    {
        hours = 23;
        minutes = 60 +(targetCal.get(Calendar.MINUTE)-c.get(Calendar.MINUTE));
    }
   
    String time = (Integer.toString(hours)
    + " h " + Integer.toString(minutes) + " min");
   
    //FIXME
    //Sparar en string (time)
    SharedPreferences app_preferences =
            PreferenceManager.getDefaultSharedPreferences(FourthActivity.this);
    SharedPreferences.Editor edit = app_preferences.edit();
    edit.putString("key3", time);
    edit.commit();
   
    //byter till SecondActivity där svårhetsgraden bestäms
    startActivity(new Intent(FourthActivity.this, SecondActivity.class));       
   
}
   
    public void goNext(View v){
        startActivity(new Intent(FourthActivity.this, SecondActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.fourth, menu);
        return true;
    }

}
