package com.example.sumeetjain.demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
/**
 * Created by sumeetjain on 18/05/15.
 *
 */
public class AlarmUsers extends ArrayAdapter<AlarmSave>
{

     public AlarmUsers(Context context, ArrayList<AlarmSave> users)
     {
             super(context, 0, users);
     }
     @Override
     public View getView(int position, View convertView, ViewGroup parent)
     {
             AlarmSave alarmSave = getItem(position);
             if(convertView == null)
             {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.alarm_list_item, parent, false);
             }
             TextView alarmID=(TextView) convertView.findViewById(R.id.alarmId);
             TextView alarmHour=(TextView) convertView.findViewById(R.id.alarmHour);
             TextView alarmMin=(TextView) convertView.findViewById(R.id.alarmMin);
             TextView alarmName=(TextView) convertView.findViewById(R.id.alarmName);
             TextView contactNumber=(TextView) convertView.findViewById(R.id.contatNumber);

             alarmID.setText(alarmSave.id + "");
             alarmName.setText(alarmSave.getName() + "");
             alarmHour.setText(alarmSave.timeHour + ":Hr");
             alarmMin.setText(alarmSave.timeMinute + ":Min");
             contactNumber.setText(ContactDBHelper.KEY_NAME + "");
             return convertView;
      }
}



