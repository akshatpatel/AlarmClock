package com.example.sumeetjain.demo;
import android.net.Uri;

import java.util.ArrayList;

/**
 * Created by sumeetjain on 30/03/15.
 */
public class AlarmSave
{
        public int id;
        public int timeHour;
        public int timeMinute;
        private boolean repeatingDays[];
        private boolean repeatWeekly;
        public String alarmTone;
        public String name;
        public boolean isEnabled;
        public static AlarmSave uniqueAlarmHolder;
        private ArrayList<AlarmStore> lAlarms;
        public int conId;

    public AlarmSave()
        {
                lAlarms = new ArrayList<AlarmStore>();
        }

        public void setId(int id)
        {
            this.id=id;
        }

        public String getName()
        {
             return this.name;
        }
       public void setName(String name)
       {
        this.name=name;
       }

        public  synchronized static AlarmSave getInstance()
        {
              if (uniqueAlarmHolder == null)
              {
                    uniqueAlarmHolder = new AlarmSave();
              }
        return uniqueAlarmHolder;
        }


        public void registerAlarm(int id)
        {
                 AlarmStore a = new AlarmStore();
                 a.setState(1);
                 a.setUniqueID(id);
                 lAlarms.add(a);
        }

        public void removeAlarm(int id,AlarmStore a)
        {
                AlarmStore newAlarm = new AlarmStore();
                a.setState(0);
                a.setUniqueID(id);
                lAlarms.remove(id);
                lAlarms.add(newAlarm);
        }

        public void replaceList(ArrayList<AlarmStore> newList)
        {
                lAlarms.clear();
                lAlarms.addAll(newList);
        }

        public ArrayList<AlarmStore> getAlarms()
        {
                return lAlarms;
        }

        public AlarmStore lastAlarmId()
        {
                return lAlarms.get(lAlarms.size()-1);
        }

        public boolean isRepeatWeekly()
        {
                return repeatWeekly;
        }

        public void setRepeatWeekly(boolean repeatWeekly)
        {
                this.repeatWeekly = repeatWeekly;
        }

    public int getConId() {
       return this.conId;

    }
}


/* */

