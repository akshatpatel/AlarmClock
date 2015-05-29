package com.example.sumeetjain.demo;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;


public class displayActivity extends Activity
{
        private AlarmDBHelper dbHelper;
        public SQLiteDatabase database;
        private ArrayList<String> result = new ArrayList<String>();
        private ArrayList<Long> idList = new ArrayList<Long>();
        private ArrayList<AlarmSave> alarms;

        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.listofalarms);
                dbHelper=new AlarmDBHelper(this);
                alarms = dbHelper.getAlarms();
                displayListView();
        }

       private void displayListView()
       {
                 AlarmUsers adapter=new AlarmUsers(this, alarms);
                 ListView listView = (ListView) findViewById(R.id.list);
                 listView.setAdapter(adapter);
       }
        @Override
         public boolean onCreateOptionsMenu(Menu menu)
        {
        // Inflate the menu; this adds items to the action bar if it is present.
                getMenuInflater().inflate(R.menu.menu_display, menu);
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

  /*if(cursor1 != null) {
           cursor1.moveToFirst();
            int getIndex = cursor1.getColumnIndex(AlarmContract.Alarm._ID);
            int getName = cursor1.getColumnIndex(AlarmContract.Alarm.COLUMNNAMEALARMNAME);
            int getTime = cursor1.getColumnIndex(AlarmContract.Alarm.COLUMNNAMEALARMTIMEMINUTE);
            int getRepeatDays = cursor1.getColumnIndex(AlarmContract.Alarm.COLUMNNAMEALARMREPEATDAYS);
            int getRepeatweekly = cursor1.getColumnIndex(AlarmContract.Alarm.COLUMNNAMEALARMREPEATWEEKLY);
            int getAlarmtone = cursor1.getColumnIndex(AlarmContract.Alarm.COLUMNNAMEALARMTONE);
           // int getContactId = cursor1.getColumnIndex(AlarmContract.Alarm.COLUMNNAMECONTACTID);
            if (cursor1.isFirst()) {
                do {
                    idList.add(cursor1.getLong(getIndex));
                    result.add(cursor1.getString(getName) + " | " + cursor1.getString(getTime) + " | " + cursor1.getString(getRepeatDays) + " | " + cursor1.getString(getRepeatweekly) + " | " + cursor1.getString(getAlarmtone) + " | " + "\n");
                }
                while (cursor1.moveToFirst());
            }
        }*/
//  final Cursor cursor= (Cursor) dbHelper.getAlarms();

