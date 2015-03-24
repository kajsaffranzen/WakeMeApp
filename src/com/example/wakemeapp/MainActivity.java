package com.example.wakemeapp;

import java.text.SimpleDateFormat;
import java.util.Calendar;



//import com.example.alarm.R;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

public class MainActivity extends Activity {
   
    TextView txtView;
    TextView txtAlarmPrompt;
    Button buttonstartSetDialog;
    TimePickerDialog timePickerDialog;
    final static int RQS_1 = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       
        //FIXME
       
         Typeface tf = Typeface.createFromAsset(getAssets(),
         "font/Roboto-Thin.ttf");
         TextView tv = (TextView) findViewById(R.id.btnSet);
         tv.setTypeface(tf);   
        TextView tv2 = (TextView)findViewById(R.id.textView1);                      
         tv2.setTypeface(tf);
 		TextView tv3 = (TextView) findViewById(R.id.clock);	       			
		tv3.setTypeface(tf);      
         Calendar c = Calendar.getInstance();
         System.out.println("Current time => "+c.getTime());

         SimpleDateFormat df = new SimpleDateFormat("HH:mm");
         String formattedDate = df.format(c.getTime());
         // formattedDate have current date/time

         // Now we display formattedDate value in TextView
         TextView txtView = (TextView)findViewById(R.id.clock);
         txtView.setText(formattedDate);    
       
        txtAlarmPrompt = (TextView)findViewById(R.id.alarmprompt);
       
        buttonstartSetDialog = (Button)findViewById(R.id.btnSet);
        buttonstartSetDialog.setOnClickListener(new OnClickListener(){
       
           
        @Override
        public void onClick(View v){
            txtAlarmPrompt.setText("");
            openTimePickerDialog(true);
        }});
    }
   
        //Poppen dŠr alarmet sŠtts
        private void openTimePickerDialog(boolean is24r){
       
            Calendar calendar = Calendar.getInstance();
       
            timePickerDialog = new TimePickerDialog(
                    MainActivity.this,
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
                PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
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
                PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putString("key3", time);
        edit.commit();
       
        //byter till SecondActivity där svårhetsgraden bestäms
        startActivity(new Intent(MainActivity.this, SecondActivity.class));       
       
    }
       
    public void onClick(View v) {}
   
    //informationsknappen.
    public void infoBtn(View v){
        Log.d("TAGGEN", "infoknapp tryckt");
        new AlertDialog.Builder(this)
        .setTitle("Information!")//menytexten lŠngst upp
        .setMessage("WakeMeApp är väckarklockan som ser till att du aldrig mer försover dig. \n" +
        		"\nStäll först in tiden när du vill gå upp. \nStäll sedan hur svårväckt du är." +
        		"\n\nUtifrån det anpassas väckarklockan efter dig, så att du verkligen ska vakna på morgonen. " )
        
        .setNeutralButton("OK", null)
        .show();
    }   

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
