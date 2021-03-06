package com.example.sumeetjain.demo;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import java.util.Calendar;

/**
 * Created by sumeetjain on 25/03/15.
 */
public class AlarmService extends Service
{
    @SuppressLint("NewApi")

    @Override

     public IBinder onBind(Intent intent)
     {
            return null;
     }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent i = new Intent(this, MyAlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, i, 0);
            Calendar test = Calendar.getInstance();
            int hour = test.MINUTE;
            int minute = test.HOUR_OF_DAY;
            alarmManager.set(AlarmManager.RTC_WAKEUP, intent.getLongExtra(AlarmDisplayActivity.TIMETOSEND, 0), pendingIntent);
            NotificationCompat.Builder notebuilder = new NotificationCompat.Builder(this);
            notebuilder.setSmallIcon(R.drawable.notification_bar);
            notebuilder.setContentTitle(getResources().getString(R.string.app_name));
        //set the ringing time
            notebuilder.setContentText(getResources().getString(R.string.notificationText1) + String.valueOf(AlarmDisplayActivity.Hour) + getResources().getString(R.string.notificationText2) + String.valueOf(AlarmDisplayActivity.Minute) + getResources().getString(R.string.notificationText3));
            Intent resultIntent = new Intent(this, AddingContactActivity.class);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addParentStack(AddingContactActivity.class);
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent =
                                               stackBuilder.getPendingIntent(
                                               0,
                                               PendingIntent.FLAG_UPDATE_CURRENT
                                               );
            //set notification to react with touch
            notebuilder.setContentIntent(resultPendingIntent);
            //set expanded zone of notification to react with touch
            notebuilder.addAction(R.drawable.notify_button, getResources().getString(R.string.notify_button), resultPendingIntent);
            //notebuilder.addAction(R.drawable.ic_launcher, getResources().getString(R.string.notify_button), resultPendingIntent);
            Notification note = notebuilder.build();
            this.startForeground(1, note);
            return START_STICKY;
     }

    @Override
    public void onDestroy()
    {
            this.stopForeground(true);
       // super.onDestroy();
    }
}


