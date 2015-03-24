package com.example.wakemeapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context arg0, Intent arg1) {
        //Toast.makeText(arg0, "Alrmet är satt", Toast.LENGTH_LONG).show();
       
        //Intent i = new Intent("android.media.action.IMAGE_CAPTURE");
        Intent i = new Intent(arg0, AlarmActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        arg0.startActivity(i);
       
    }
}
