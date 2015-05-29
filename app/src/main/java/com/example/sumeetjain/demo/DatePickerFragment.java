package com.example.sumeetjain.demo;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

/**
 * Created by sumeetjain on 25/03/15.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener
{
    //DatePicker pickerDate;
        OnAlarmSetListener mCallback;
        public interface OnAlarmSetListener
        {
                public void onAlarmDateSet(int year, int monthOfYear, int dayOfMonth);
        }
        @Override
         public void onAttach(Activity activity)
        {
                super.onAttach(activity);
                try
                    {
                         mCallback = (OnAlarmSetListener) activity;
                    }
                catch (ClassCastException e)
                    {
                        throw new ClassCastException(activity.toString()
                        + " must implement OnAlarmSetListener.onAlarmDateSet()");
                    }
        }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
               int year= AlarmDisplayActivity.Year;
               int month= AlarmDisplayActivity.Month;
               int day= AlarmDisplayActivity.Day;
               return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
    {
                mCallback.onAlarmDateSet(year, monthOfYear, dayOfMonth);
    }
}