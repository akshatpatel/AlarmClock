package com.example.sumeetjain.demo;

/**
 * Created by sumeetjain on 01/04/15.
 */
public class AlarmStore
{
        public int state;
        public int uniqueID;
        public String name;

        public void setID(int id)
        {
                this.uniqueID=id;
        }
        public int getId()
        {
                return uniqueID;
        }
        public void setName(String name)
        {
               this.name=name;
        }
        public String getName()
        {
               return name;
        }
        public int getState()
        {
                return state;
        }
        public void setState(int state)
        {
                this.state = state;
        }
        public int getUniqueID()
        {
                return uniqueID;
        }
        public void setUniqueID(int uniqueID)
        {
                this.uniqueID = uniqueID;
        }
}

// public int Min;
// public int Hr;
//public AlarmStore(int alarmId,String alarmName, int alarmMin, int alarmHr)
//{
//  this.uniqueID=alarmId;
// this.name=alarmName;
//this.Hr=alarmHr;
//this.Min=alarmHr;
//}
// public static AlarmStore alarmStore(int alarmId, String alarmName,int alarmMin, int alarmHr)
//{
//  return new AlarmStore(alarmId,alarmName,alarmMin,alarmHr);
//}
