package com.example.sumeetjain.demo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by sumeetjain on 25/03/15.
 */
public class MyAlarmReceiver extends BroadcastReceiver
{
        @Override
        public void onReceive(Context context, Intent intent)
        {
                Intent i = new Intent();
                i.setClassName("com.example.sumeetjain.demo", "com.example.sumeetjain.demo.ringActivity");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
        }
}
