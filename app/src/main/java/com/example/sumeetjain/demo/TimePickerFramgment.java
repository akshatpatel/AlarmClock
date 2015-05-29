package com.example.sumeetjain.demo;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;
/**
 * Created by sumeetjain on 25/03/15.
 */
public class TimePickerFramgment extends DialogFragment implements TimePickerDialog.OnTimeSetListener
{
  //  TimePicker pickerTime;
        OnAlarmSetListener mCallback;
        public interface OnAlarmSetListener
        {
                public void onAlarmTimeSet(int hourOfDay, int minute);
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
                        + " must implement OnAlarmSetListener.onAlarmTimeSet()");
            }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
            int minute= AlarmDisplayActivity.Minute;
            int hour= AlarmDisplayActivity.Hour;
            return new TimePickerDialog(getActivity(), this, hour, minute,
            DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
    {
            mCallback.onAlarmTimeSet(hourOfDay, minute);
    }
}