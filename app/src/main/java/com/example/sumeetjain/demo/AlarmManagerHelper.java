package com.example.sumeetjain.demo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

/**
 * Created by sumeetjain on 30/03/15.
 */

public class AlarmManagerHelper extends BroadcastReceiver
{
        AlarmSave objAlarmHolder = AlarmSave.getInstance();
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String TIMEHOUR = "timeHour";
        public static final String TIMEMINUTE = "timeMinute";
        public static final String TONE = "alarmTone";
        public Context context;

    @Override
    public void onReceive(Context context, Intent intent)
    {
            int key = intent.getIntExtra("key", 0);
            for (AlarmStore alarm : objAlarmHolder.getAlarms())
            {
                    if (alarm.getState() == 1 & alarm.getUniqueID() == key)
                    {
                            Log.v("alarm", String.valueOf(String.valueOf(alarm.getUniqueID())));
                            Toast.makeText(context, "your alarm id : " + String.valueOf(alarm.getUniqueID()), Toast.LENGTH_LONG).show();
                            Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                            vibrator.vibrate(2000);
                    }
                    else
                    {
                             Log.v("alarm", "canceled alarm number : " + String.valueOf(alarm.getUniqueID()));
                    }
            }

            setAlarms(context);
    }

    public static void setAlarms(Context context)
    {
                AlarmDBHelper dbHelper = new AlarmDBHelper(context);
                List<AlarmSave> alarms = dbHelper.getAlarms();
                for (AlarmSave alarm : alarms)
                {
                        if (alarm.isEnabled)
                        {
                                PendingIntent pIntent = createPendingIntent(context, alarm);
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(Calendar.HOUR_OF_DAY, alarm.timeHour);
                                calendar.set(Calendar.MINUTE, alarm.timeMinute);
                                calendar.set(Calendar.SECOND, 00);

                                     //Find next time to set
                                final int nowDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
                                final int nowHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                                final int nowMinute = Calendar.getInstance().get(Calendar.MINUTE);
                                boolean alarmSet = true;
                        }
                }
    }

    private static void setAlarm(Context context, Calendar calendar, PendingIntent pIntent)
    {
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                // AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                 if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT)
                 {
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);
                 }
                    else
                 {
                         alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);
                  }
    }

    public static void cancelAlarms(Context context)
    {
             AlarmDBHelper dbHelper = new AlarmDBHelper(context);
             List<AlarmSave> alarms =  dbHelper.getAlarms();
             if (alarms != null)
             {
                    for (AlarmSave alarm : alarms)
                    {
                           if (alarm.isEnabled)
                           {
                                PendingIntent pIntent = createPendingIntent(context, alarm);
                                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                                alarmManager.cancel(pIntent);
                            }
                    }
             }
    }

    private static PendingIntent createPendingIntent(Context context, AlarmSave model)
    {
              Intent intent = new Intent(context, AlarmService.class);
              intent.putExtra(ID, model.id);
              intent.putExtra(NAME, model.name);
              intent.putExtra(TIMEHOUR, model.timeHour);
              intent.putExtra(TIMEMINUTE, model.timeMinute);
              intent.putExtra(TONE, model.alarmTone.toString());
              return PendingIntent.getService(context, (int) model.id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
