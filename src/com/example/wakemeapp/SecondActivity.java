//STÄLL SVÅRIGHETSGRAD
//KOLLA SÅ ATT SEVERITY FUNGERAR

package com.example.wakemeapp;



import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends Activity {

	private final String TAG = "TAGGEN";
	public static String severity = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		Typeface tf = Typeface.createFromAsset(getAssets(),
                "font/Roboto-Thin.ttf");
    TextView tv = (TextView) findViewById(R.id.btnBack);
        tv.setTypeface(tf);	
	TextView tv2 = (TextView) findViewById(R.id.textView4);	       			
	   tv2.setTypeface(tf);
	TextView tv3 = (TextView) findViewById(R.id.btnEasy);	       			
		tv3.setTypeface(tf);
	TextView tv4 = (TextView) findViewById(R.id.btnMedium);	       			
		tv4.setTypeface(tf);
		TextView tv5 = (TextView) findViewById(R.id.btnHard);	       			
		tv5.setTypeface(tf);
		TextView tv6 = (TextView) findViewById(R.id.btnNext);	       			
		tv6.setTypeface(tf);
		//nästaknapp ej aktiverad
    	
		enableDisableButtons(false);
		
	}
	
	//FUNCTIONS-----------------------------
	
	public void onClick(View v) {}
	
	//Går tillbaka till anda aktiviteten
	public void goBack(View v)
	{
		startActivity(new Intent(SecondActivity.this, MainActivity.class));
	}
	
	//Svårighetsgrad lätt
	public void buttonEasy(View v)
	{
		Log.d(TAG, "Lätt tryckt");
		severity = "1";
		enableDisableButtons(true);
		//kod för att se till så endast en är intryckt
	TextView TVeasy = (TextView) findViewById(R.id.btnEasy);
		TVeasy.setSelected(true);
	TextView TVmed = (TextView) findViewById(R.id.btnMedium);
		TVmed.setSelected(false);
	TextView TVhard = (TextView) findViewById(R.id.btnHard);
		TVhard.setSelected(false);
		
		enableDisableButtons(true);
	}
	
	//Svårighetsgrad Medel
	public void buttonMedium(View v)
	{
		Log.d(TAG, "Medel tryckt");
		severity = "2";
		//setSelected(true);
		//kod för att se till så endast en är intryckt
	TextView TVeasy = (TextView) findViewById(R.id.btnEasy);
		TVeasy.setSelected(false);
	TextView TVmed = (TextView) findViewById(R.id.btnMedium);
		TVmed.setSelected(true);
	TextView TVhard = (TextView) findViewById(R.id.btnHard);
		TVhard.setSelected(false);
		enableDisableButtons(true);
	}
	
	//Svårighetsgrad Svår
	public void buttonHard(View v)
	{
		Log.d(TAG, "Svår tryckt");
		severity = "3";
		//kod för att se till så endast en är intryckt
	TextView TVeasy = (TextView) findViewById(R.id.btnEasy);
		TVeasy.setSelected(false);
	TextView TVmed = (TextView) findViewById(R.id.btnMedium);
		TVmed.setSelected(false);
	TextView TVhard = (TextView) findViewById(R.id.btnHard);
		TVhard.setSelected(true);
		enableDisableButtons(true);
	}
	
	//knapp till nästa
	public void goNext(View v)
	{
		Log.d(TAG, "nästa tryckt");
		Log.d(TAG, "" + severity);
		
		//spara svårighetsgraden
		SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(SecondActivity.this);
		SharedPreferences.Editor edit = app_preferences.edit();
		edit.putString("key", severity);
		edit.commit();
		
		//går vidare till nästa sida
		startActivity(new Intent(SecondActivity.this, ThirdActivity.class));
	}
	
	//sätter nästaknappen till true
	public void enableDisableButtons(Boolean truefalse)
	{ 
		Button b = (Button)findViewById(R.id.btnNext); 
		b.setEnabled(truefalse); 
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.second, menu);
		return true;
	}
}
