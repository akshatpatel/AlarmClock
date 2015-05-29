package com.example.sumeetjain.demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by sumeetjain on 30/03/15.
 */

public class AlarmListAdapater extends BaseAdapter
{
            private Context mContext;
            private List<AlarmStore> mAlarms;
            public AlarmListAdapater(Context context, List<AlarmStore> alarms)
            {
                     this.mContext = context;
                     mAlarms = alarms;
            }

    @Override
    public int getCount()
    {
            if (mAlarms != null)
            {
                    return mAlarms.size();
            }
        return 0;
    }

    @Override
    public Object getItem(int position)
    {
            if (mAlarms != null)
            {
                    return mAlarms.get(position);
            }
            return null;
    }

    @Override
    public long getItemId(int position)
    {
            if (mAlarms != null)
            {
                    return mAlarms.get(position).getUniqueID();
             }
             return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

            if (convertView == null)
            {
                    LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = inflater.inflate(R.layout.listofalarms, parent, false);
            }
            AlarmSave model = (AlarmSave) getItem(position);
            return null;
    }
}
