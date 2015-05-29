package com.example.sumeetjain.demo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;


public class AlarmDetailsActivity extends ActionBarActivity
{

        private Button saveValues;
        TimePicker timePicker;
        public AlarmDBHelper dbHelper = new AlarmDBHelper(this);
        public AlarmSave alarmDetails;
        private EditText edtName;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_alarm_details);
            edtName = (EditText) findViewById(R.id.alarm_details_name);
            long id = getIntent().getExtras().getLong("id");

             if (id == -1)
             {
                   alarmDetails = new AlarmSave();
             }
             else
             {
                   alarmDetails = dbHelper.getAlarm(id);
                   timePicker.setCurrentMinute(alarmDetails.timeMinute);
                   timePicker.setCurrentHour(alarmDetails.timeHour);
                   edtName.setText(alarmDetails.name);
             }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_alarm_details, menu);
            return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
            int id = item.getItemId();
            if (id == R.id.action_settings)
            {
                    return true;
            }
            return super.onOptionsItemSelected(item);
    }
}
