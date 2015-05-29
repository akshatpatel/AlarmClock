package com.example.sumeetjain.demo;

import android.provider.BaseColumns;

/**
 * Created by sumeetjain on 30/03/15.
 */
public final class AlarmContract
{

    public AlarmContract()
    {

    }
        public static abstract class Alarm implements BaseColumns
        {
                public static final String TABLENAME = "alarm";
                public static final String COLUMNNAMEALARMNAME = "name";
                public static final String COLUMNNAMEALARMTIMEHOUR = "hour";
                public static final String COLUMNNAMEALARMTIMEMINUTE = "minute";
                public static final String COLUMNNAMEALARMREPEATDAYS = "days";
                public static final String COLUMNNAMEALARMREPEATWEEKLY = "weekly";
                public static final String COLUMNNAMEALARMTONE = "tone";
                public static final String COLUMNNAMEALARMENABLED = "isEnabled";
                public static final String COLUMNNAMECONTACTID= "id";


        }
}


