package com.example.sumeetjain.demo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by sumeetjain on 30/03/15.
 */
public final class AlarmDBHelper extends SQLiteOpenHelper
{
    ContactStore contactStore;
    public static final int DATABASEVERSION = 1;
    public static final String DATABASENAME = "alarmclock.db";
    private static final String SQLCREATEALARM =
                    "CREATE TABLE " + AlarmContract.Alarm.TABLENAME + " (" +
                    AlarmContract.Alarm._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    AlarmContract.Alarm.COLUMNNAMEALARMNAME + " TEXT," +
                    AlarmContract.Alarm.COLUMNNAMEALARMTIMEHOUR + " INTEGER," +
                    AlarmContract.Alarm.COLUMNNAMEALARMTIMEMINUTE + " INTEGER," +
                    AlarmContract.Alarm.COLUMNNAMEALARMREPEATDAYS + " TEXT," +
                    AlarmContract.Alarm.COLUMNNAMEALARMREPEATWEEKLY + " BOOLEAN," +
                    AlarmContract.Alarm.COLUMNNAMEALARMTONE + " TEXT," +
                    AlarmContract.Alarm.COLUMNNAMEALARMENABLED + " BOOLEAN, " + AlarmContract.Alarm.COLUMNNAMECONTACTID + " INTERGER NOT NULL ,FOREIGN KEY (" + AlarmContract.Alarm.COLUMNNAMECONTACTID + ") REFERENCES " + ContactDBHelper.Contacttable + " (" + ContactDBHelper.P_id + "));";

    private static final String SQL_DELETE_ALARM ="DROP TABLE IF EXISTS " + AlarmContract.Alarm.TABLENAME;

    public AlarmDBHelper(Context context)
    {
            super(context, DATABASENAME, null, DATABASEVERSION);
    }

    public AlarmDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
            super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
            db.execSQL(SQLCREATEALARM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
//            db.execSQL(SQL_DELETE_ALARM);
            onCreate(db);
    }
    private AlarmSave populateModel(Cursor c)
    {
            AlarmSave model = new AlarmSave();
            model.id = c.getInt(c.getColumnIndex(AlarmContract.Alarm._ID));
            model.name = c.getString(c.getColumnIndex(AlarmContract.Alarm.COLUMNNAMEALARMNAME));
            model.timeHour = c.getInt(c.getColumnIndex(AlarmContract.Alarm.COLUMNNAMEALARMTIMEHOUR));
            model.timeMinute = c.getInt(c.getColumnIndex(AlarmContract.Alarm.COLUMNNAMEALARMTIMEMINUTE));
            model.id = c.getInt(c.getColumnIndex(AlarmContract.Alarm.COLUMNNAMECONTACTID));
            return model;
    }

    public AlarmSave getAlarm(long id)
    {
            SQLiteDatabase db = this.getReadableDatabase();
            String select = "SELECT * FROM " + AlarmContract.Alarm.TABLENAME + " WHERE " + AlarmContract.Alarm._ID + " = " + id;
            Cursor c = db.rawQuery(select, null);
            if (c.moveToNext())
            {
                    return populateModel(c);
            }
            return null;
    }

    public ArrayList<AlarmSave> getAlarms()
    {
            SQLiteDatabase db = this.getReadableDatabase();
            String select = "SELECT * FROM " + AlarmContract.Alarm.TABLENAME + " WHERE " + AlarmContract.Alarm.COLUMNNAMECONTACTID + " = " + ContactDBHelper.P_id;
        //String select = "SELECT * FROM " + AlarmContract.Alarm.TABLENAME + " WHERE " + AlarmContract.Alarm._ID + " = " + AlarmContract.Alarm.COLUMNNAMECONTACTID;
            Cursor c = db.rawQuery(select, null);
            ArrayList<AlarmSave> alarmList = new ArrayList<AlarmSave>();
            while (c.moveToNext())
            {
                    alarmList.add(populateModel(c));
            }
            if (!alarmList.isEmpty())
            {
                    return alarmList;
            }

            return null;
    }

    public int deleteAlarm(long id)
    {
            return getWritableDatabase().delete(AlarmContract.Alarm.TABLENAME, AlarmContract.Alarm._ID + " = ?", new String[]{String.valueOf(id)});
    }

    public void insertValues(AlarmSave model)
    {
            SQLiteDatabase db = this.getWritableDatabase();
            db.isOpen();
            ContentValues values = new ContentValues();
             values.put(AlarmContract.Alarm.COLUMNNAMECONTACTID, model.conId);
            values.put(AlarmContract.Alarm.COLUMNNAMEALARMNAME, model.name);
            values.put(AlarmContract.Alarm.COLUMNNAMEALARMTIMEHOUR, model.timeHour);
            values.put(AlarmContract.Alarm.COLUMNNAMEALARMTIMEMINUTE, model.timeMinute);
            values.put(AlarmContract.Alarm.COLUMNNAMEALARMTONE, model.alarmTone != null ? model.alarmTone.toString() : "");
            values.put(ContactDBHelper.P_id, model.conId);
            db.insert(AlarmContract.Alarm.TABLENAME, null, values);
            db.close();
    }

    public void ContentValues(AlarmSave model)
    {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(AlarmContract.Alarm.COLUMNNAMEALARMNAME, model.name);
            values.put(AlarmContract.Alarm.COLUMNNAMEALARMTIMEHOUR, model.timeHour);
            values.put(AlarmContract.Alarm.COLUMNNAMEALARMTIMEMINUTE, model.timeMinute);
            values.put(AlarmContract.Alarm.COLUMNNAMEALARMTONE, model.alarmTone != null ? model.alarmTone.toString() : "");
            values.put(AlarmContract.Alarm.COLUMNNAMEALARMENABLED, model.isEnabled);
            values.put(AlarmContract.Alarm.COLUMNNAMECONTACTID, model.id);
            db.insert(AlarmContract.Alarm.TABLENAME, null, values);
            db.close();
            return;
    }
}

//AlarmDBHelper alarmDBHelper=

   // public long createAlarm(AlarmSave model) {
     //   ContentValues values;
       // values = new ContentValues(model);
        //return getWritableDatabase().insert(AlarmContract.Alarm.TABLENAME, null, values);
        //}
        // public long updateAlarm(AlarmSave model) {
        //   ContentValues values = ContentValues(model);
        // return getWritableDatabase().update(AlarmContract.Alarm.TABLENAME, values, AlarmContract.Alarm._ID + " = ?", new String[]{String.valueOf(model.id)});

//}

// public boolean insertValues(AlarmSave model) {
//   boolean createSuccessful = false;
// SQLiteDatabase db=this.getWritableDatabase();
//ContentValues values = new ContentValues();
//values.put(AlarmContract.Alarm.COLUMNNAMEALARMNAME, model.name);
//values.put(AlarmContract.Alarm.COLUMNNAMEALARMTIMEHOUR, model.timeHour);
//values.put(AlarmContract.Alarm.COLUMNNAMEALARMTIMEMINUTE, model.timeMinute);
//values.put(AlarmContract.Alarm.COLUMNNAMEALARMTONE, model.alarmTone != null ? model.alarmTone.toString() : "");
//values.put(AlarmContract.Alarm.COLUMNNAMEALARMENABLED, model.isEnabled);
// values.put(AlarmContract.Alarm.COLUMNNAMECONTACTID, model.id);
// db.insert(AlarmContract.Alarm.TABLENAME,null,values);
//db.close();
//return createSuccessful;
//}



















