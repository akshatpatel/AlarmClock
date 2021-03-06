package com.example.sumeetjain.demo;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;


public class AlarmDisplayActivity extends Activity implements  DatePickerFragment.OnAlarmSetListener,TimePickerFramgment.OnAlarmSetListener {

    private static final String STATEHOUR = "HOUR";
    private static final String STATEMINUTE = "MINUTE";
    private static final String STATEYEAR = "YEAR";
    private static final String STATEMONTH = "MONTH";
    private static final String STATEDAY = "DAY";
    private static final String STATEURI = "URI";
    private static final String PREFSNAME ="SETTINGS";
    private static final String STATEMUSIC = "MUSIC";
    private static final String STATEBUTTON = "BUTTON";
    private static final String STATEVIBRATE = "VIBRATE";
    public static final String TIMETOSEND = "TIME";

    static String ringtonename;
    static int Hour;
    static int Minute;
    static int Year;
    static int Month;
    static int Day;
    static Uri ringtoneUri;
    static String musicname;
    static boolean buttonOn;
    static boolean buttonVibrate;

    private int contactId;
    Button saveValues;
    TextView ringtime, ringdate, ringname;
    ToggleButton buttonflag,buttonVflag;
    EditText contentValue;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.alarmdisplay);

            saveValues=(Button)findViewById(R.id.saveData);
            saveValues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAlarm();
            }
        });
            contentValue=(EditText)findViewById(R.id.ringcontent_text);
            ringdate = (TextView) this.findViewById(R.id.ringdate_blank);
            ringname = (TextView) this.findViewById(R.id.ringname);
            buttonflag = (ToggleButton) this.findViewById(R.id.Ring_set);
            buttonVflag = (ToggleButton) this.findViewById(R.id.Vibrate_set);

            if( getIntent().getIntExtra("ContactId", -1) > -1)
            {
                 contactId = getIntent().getIntExtra("ContactId", 0);
            }
                else
            {
                finish();
            }

            final Calendar c = Calendar.getInstance();
            Hour = c.get(Calendar.HOUR_OF_DAY);
            Minute = c.get(Calendar.MINUTE) + 1;
            if (Minute >= 60)
            {
                Hour++;
                Minute -= 60;
                if (Hour >= 24)
                {
                    Hour = 0;
                }
            }
            Year = c.get(Calendar.YEAR);
            Month = c.get(Calendar.MONTH);
            Day = c.get(Calendar.DAY_OF_MONTH);
            ringtoneUri = Uri.parse("content://settings/system/ringtone");
            musicname = getResources().getString(R.string.defaultRing);
            buttonOn = false;
            buttonVibrate = false;

            ringtime = (TextView) this.findViewById(R.id.ringtime_blank);
            ringtime.setText(String.valueOf(Hour) + ":" + String.valueOf(Minute));
            ringdate.setText(String.valueOf(Month + 1) + "/" + String.valueOf(Day) + "/" + String.valueOf(Year));
            ringname.setText(musicname);
            buttonflag.setChecked(buttonOn);
            buttonVflag.setChecked(buttonVibrate);

    }

        @Override
        protected void onResume ()
        {
                super.onResume();
        }

        @Override
        protected void onStop ()
        {
                super.onStop();
                SharedPreferences settings = getSharedPreferences(PREFSNAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt(STATEHOUR, Hour);
                editor.putInt(STATEMINUTE, Minute);
                editor.putInt(STATEYEAR, Year);
                editor.putInt(STATEMONTH, Month);
                editor.putInt(STATEDAY, Day);
                editor.putString(STATEURI, ringtoneUri.toString());
                editor.putString(STATEMUSIC, musicname);
                ToggleButton buttonflag = (ToggleButton) this.findViewById(R.id.Ring_set);
                ToggleButton buttonVflag = (ToggleButton) this.findViewById(R.id.Vibrate_set);
                editor.putBoolean(STATEBUTTON, buttonflag.isChecked());
                editor.putBoolean(STATEVIBRATE, buttonVflag.isChecked());
                editor.commit();
                //show how long will take before the alarm goes off.
                Calendar c = Calendar.getInstance();
                long now = c.getTimeInMillis();
                Calendar setTime = Calendar.getInstance();
                setTime.set(Year, Month, Day, Hour, Minute, 0);
                long set = setTime.getTimeInMillis();
                int hoursLeft = (int) Math.floor((set - now) / 1000 / 3600);
                long mod = (set - now) % (1000 * 3600);
                int minutesLeft = (int) Math.floor(mod / 1000 / 60);
                 if (minutesLeft == 0 && ((ToggleButton) this.findViewById(R.id.Ring_set)).isChecked() && now < set)
                 {
                        Toast.makeText(this, getResources().getString(R.string.notificationText1)+getResources().getString(R.string.notificationText6)+getResources().getString(R.string.notificationText5), Toast.LENGTH_SHORT).show();
                 }
                        else if (hoursLeft == 0 && ((ToggleButton) this.findViewById(R.id.Ring_set)).isChecked() && now < set)
                 {
                        Toast.makeText(this, getResources().getString(R.string.notificationText1)+String.valueOf(minutesLeft)+getResources().getString(R.string.notificationText5), Toast.LENGTH_SHORT).show();
                 }
                        else if (((ToggleButton) this.findViewById(R.id.Ring_set)).isChecked() && now < set)
                 {
                        Toast.makeText(this, getResources().getString(R.string.notificationText1)+String.valueOf(hoursLeft)+getResources().getString(R.string.notificationText4)+String.valueOf(minutesLeft)+getResources().getString(R.string.notificationText5), Toast.LENGTH_SHORT).show();
                 }
        }

    public void showTimePickerDialog(View v)
    {
                DialogFragment newFragment = new TimePickerFramgment();
                newFragment.show(getFragmentManager(), "timePicker");
    }

    private void setAlarm()
    {
                AlarmSave alarmSave = new AlarmSave();
                alarmSave.timeMinute = Minute;
                alarmSave.timeHour= Hour;
                alarmSave.id = contactId;

                alarmSave.alarmTone=musicname;
                alarmSave.name=ringname.getText().toString();
                alarmSave.name=contentValue.getText().toString();

                 alarmSave.conId=contactId;

                AlarmDBHelper alarmDBHelper = new AlarmDBHelper(getApplicationContext());
                alarmDBHelper.insertValues(alarmSave);
                Toast.makeText(getApplicationContext(), "values inserted" , Toast.LENGTH_SHORT).show();
    }

    public void showDatePickerDialog(View v)
    {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "datePicker");
    }

    public void onAlarmDateSet(int year, int monthOfYear, int dayOfMonth)
    {
        //Set AlarmClock
                Year=year;
                Month=monthOfYear;
                Day=dayOfMonth;
                ringdate.setText(String.valueOf(monthOfYear + 1) + "/" + String.valueOf(dayOfMonth) + "/" + String.valueOf(year));
    }

    public void onAlarmTimeSet(int hourOfDay, int minute)
    {
        //Set AlarmClock
                ringtime.setText(String.valueOf(hourOfDay) + ":" + String.valueOf(minute));
    }

    public void selectRingtone(View v)
    {
                Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, getResources().getString(R.string.ringselectTitle));
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE,RingtoneManager.TYPE_RINGTONE);
                this.startActivityForResult(intent , 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
            if (requestCode == 1 && resultCode == RESULT_OK)
            {
                     if(data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI) != null)
                     {
                            ringtoneUri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
                     }

                        if(!ringtoneUri.equals(Uri.parse("content://settings/system/ringtone")))
                          {
                        //set the selected ringtone name
                                String[] proj = {MediaStore.Audio.Media.DATA};
                                CursorLoader loader = new CursorLoader(this, ringtoneUri, proj, null, null, null);
                                Cursor cursor = loader.loadInBackground();
                                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
                                cursor.moveToFirst();
                                ringtonename =cursor.getString(column_index);

                                 int start;int end;
                                 start= ringtonename.lastIndexOf("/");
                                 end= ringtonename.lastIndexOf(".");
                                 musicname = ringtonename.substring(start+1,end);
                           }

                           else
                           {
                                 musicname = getResources().getString(R.string.defaultRing);
                           }

                           if (ringtoneUri != null)
                            {
                                TextView activity_edittext = (TextView)this.findViewById(R.id.ringname);
                                activity_edittext.setText(musicname);
                            }
            }
    }

    public void setVibrate(View view)
    {
                boolean on = ((ToggleButton) view).isChecked();
                buttonVibrate=on;
    }

    public void setRing(View view)
    {
               boolean on = ((ToggleButton) view).isChecked();
               Calendar setTime = Calendar.getInstance();
               setTime.set(Year, Month, Day, Hour, Minute,0);
               Calendar nowTime = Calendar.getInstance();
               long now=nowTime.getTimeInMillis();
               long set=setTime.getTimeInMillis();
               boolean setORnot=(now < set);
               Intent toService = new Intent(this,AlarmService.class);

               if (on && setORnot)
               {
            // turn on the alarm
                    toService.putExtra(TIMETOSEND, setTime.getTimeInMillis());
                    this.startService(toService);
                }
               else
               {
                    // turn off the alarm
                     this.stopService(toService);
                }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
                getMenuInflater().inflate(R.menu.menu_main, menu);
                return true;
    }

    @Override
     public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
                int id = item.getItemId();

        //noinspection SimplifiableIfStatement
                 if (id == R.id.action_settings)
                 {
                        return true;
                 }
        return super.onOptionsItemSelected(item);
    }
}








