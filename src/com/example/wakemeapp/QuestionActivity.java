package com.example.wakemeapp;

import java.util.Random;

import android.media.MediaPlayer;
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
import android.widget.Toast;

public class QuestionActivity extends Activity {

	private static final int MAX = 3;
	private static final int MAXQ = 9;
	boolean rightAnswer = false;
			
	MediaPlayer alarmTune;
	
	//new random number generator 
	Random randNr = new Random();

	
	//frågor med svar. första alternativet är rätt i alla svar
	String e1 = "Vad heter Sveriges huvudstad?";
	String e2 = "Vad är 4 + 3 ?";
	String e3 = "Vilket är det enda kattdjur som lever i flock?";	
	String ae11 = "Stockholm";
	String ae12 = "Athen";
	String ae13 = "Norrköping";
	String ae21 = "7";
	String ae22 = "0";
	String ae23 = "8";
	String ae31 = "Lejon";
	String ae32 = "Nakenkatt";
	String ae33 = "Lodjur";
	
	//Frågor med svar MEDEL
	String m1 = "Vilket färg får man om man blandar gult och blått";
	String m2 = "Vad är 4 - 3 + 1 – 8?";
	String m3 = "Vilket är det enda kattdjur som lever i flock?";	
	String am11 = "Grönt";
	String am12 = "Lila";
	String am13 = "Turkos";
	String am21 = "-6";
	String am22 = "2";
	String am23 = "-4";
	String am31 = "Lejon";
	String am32 = "Nakenkatt";
	String am33 = "Lodjur";
	
	//Frågor med svar SVÅR
	String s1 = "Vilken artist medverkar i 'The social network'?";
	String s2 = "Vad är ((4 + 6) / 2)*2";
	String s3 = "Vilken är Sveriges nordligaste nationalpark?";	
	String as11 = "Justin Timberlake";
	String as12 = "Rihanna";
	String as13 = "Enrique Iglesias";
	String as21 = "10";
	String as22 = "9";
	String as23 = "8";
	String as31 = "Vadvetjåkka nationalpark";
	String as32 = "Vadvetinttja nationalpark";
	String as33 = "Vadjetåkja nationalpark";
	
	String[] EasyQuestions = {e1, e2, e3};
	String[] MediumQuestions = {m1, m2, m3};
	String[] HardQuestions = {s1, s2, s3};
	
	String[] EasyAnswers1 = {ae11, ae12, ae13};
	String[] EasyAnswers2 = {ae21, ae22, ae23};
	String[] EasyAnswers3 = {ae31, ae32, ae33};

	String[] MediumAnswers1 = {am11, am12, am13};
	String[] MediumAnswers2 = {am21, am22, am23};
	String[] MediumAnswers3 = {am31, am32, am33};
	
	String[] HardAnswers1 = {as11, as12, as13};
	String[] HardAnswers2 = {as21, as22, as23};
	String[] HardAnswers3 = {as31, as32, as33};
	
	String[] rightArray = {ae11, ae21, ae31, am11, am21, am31, as11, as21, as31};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question);
		Typeface tf = Typeface.createFromAsset(getAssets(),
	            "font/Roboto-Thin.ttf");
		TextView tv = (TextView) findViewById(R.id.textView1);
	    tv.setTypeface(tf);	
	    TextView tv2 = (TextView) findViewById(R.id.btnAnswer1);	       			
	   tv2.setTypeface(tf);
	   TextView tv3 = (TextView) findViewById(R.id.btnAnswer2);	       			
		tv3.setTypeface(tf);
		   TextView tv4 = (TextView) findViewById(R.id.btnAnswer3);	       			
			tv4.setTypeface(tf);			
		
		
		alarmTune = MediaPlayer.create(QuestionActivity.this, R.raw.alarm_sound);
        alarmTune.start();
		
		TextView QuestionField = (TextView)findViewById(R.id.textView1);
		Button button1 = (Button)findViewById(R.id.btnAnswer1);
		Button button2 = (Button)findViewById(R.id.btnAnswer2);
		Button button3 = (Button)findViewById(R.id.btnAnswer3);
		
		
		//test: ta emot string från third activity
		SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
		String severity = app_preferences.getString("key", "null");
		
		int Sev = Integer.parseInt(severity);
		
		Log.d("TAGGEN", "severity = " + Sev);
		
		int tal;
		
		
		switch(Sev){
		
		case 1:
				
				tal = randQuestion(EasyQuestions, MAX, QuestionField);
				
				if(tal == 0)
					putButtons(EasyAnswers1, MAX, button1, button2, button3);
				if(tal==1)
					putButtons(EasyAnswers2, MAX, button1, button2, button3);
				if(tal == 2)
					putButtons(EasyAnswers3, MAX, button1, button2, button3);
			
			break;
		
			
		case 2:
				rightAnswer = false;
				
				tal = randQuestion(MediumQuestions, MAX, QuestionField);
				
				if(tal == 0)
					putButtons(MediumAnswers1, MAX, button1, button2, button3);
				if(tal==1)
					putButtons(MediumAnswers2, MAX, button1, button2, button3);
				if(tal == 2)
					putButtons(MediumAnswers3, MAX, button1, button2, button3);
			 
			 break;
			
		case 3:
				rightAnswer = false;
				
				tal = randQuestion(HardQuestions, MAX, QuestionField);
				
				if(tal == 0)
					putButtons(HardAnswers1, MAX, button1, button2, button3);
				if(tal==1)
					putButtons(HardAnswers2, MAX, button1, button2, button3);
				if(tal == 2)
					putButtons(HardAnswers3, MAX, button1, button2, button3);

			
			break;
		}
	}
	
	public void onClick(View v) {}
	
	//slumpa fram fråg och sätt i TextField, returnera vilken fråga: 1,2 eller 3
	int randQuestion(String[] A, int grad, TextView Q)
	{
		//slumpa ett tal mellan 1 och 3
		int altNr = randNr.nextInt(3);
		
		Q.setText(A[altNr]);
		
		Log.d("TAG", "altNr (fragan) ="+ altNr);
		
		return altNr;
	}
	
	//skriver ut knappar
	public void putButtons(String[] Alts, int s, Button b1, Button b2, Button b3)
	{
		//int used, used2;	
		boolean used[] = {false, false, false};
		
		int altNr = randNr.nextInt(3);
		
		b1.setText("" + Alts[altNr]);
		used[altNr] = true;
		Log.d("TAG", "altNr (button1) ="+ altNr);
		
		//slumpa fram nytt nummer som inte redan är använt 
		do{
			
			altNr = randNr.nextInt(3);
			Log.d("TAG", "altNr (button2) ="+ altNr);
			
		}while(used[altNr] == true);
		
		Log.d("TAG", "altNr (button2) ="+ altNr);
		
		b2.setText("" + Alts[altNr]); //sätt knapp 2
		used[altNr] = true;
		
		//slumpa fram nytt nummer som inte redan är använt för button3
		do{
			
			altNr = randNr.nextInt(3);
			Log.d("TAG", "altNr (button3) ="+ altNr);
			
		}while(used[altNr] == true);
		
		b3.setText("" + Alts[altNr]);
		
		Log.d("TAG", "altNr (button3 loop) ="+ altNr);
	}
	
	
	
	//Test if button is true/false-->rightAnswer gets the value
	public void testButton(View v)
	{
		boolean right = false;
		
		Button b = (Button)v;
		String buttonText = (String) b.getText();
		
		Log.d("TAG", "String = " + buttonText);
		
		for(int i = 0; i < MAXQ; i++)
		{
			Log.d("TAG", "Loop");
			if( rightArray[i].equals(buttonText)){
				Log.d("TAG", "RÄTT KNAPP!!!");
				right = true;
				
				break;
			}
		}
		
		if(right){
			alarmTune.stop();
			
			startActivity(new Intent(QuestionActivity.this, LastActivity.class));
			finish();
			b.setEnabled(false);
			b.setSelected(true);
		}
			
		else{
			b.setSelected(true);
			alarmTune.stop();
			finish();
			startActivity(new Intent(QuestionActivity.this, QuestionActivity.class));
			//Toast.makeText(this, "Fel svar, försök igen!", Toast.LENGTH_LONG).show();
			 
		}
			
			
		
			
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.question, menu);
		return true;
	}

}
