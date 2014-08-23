package com.example.schalarm_android_app.activities.elements;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.TimePicker;
import com.example.schalarm_android_app.R;

/**
 * Created by FFX20413 on 23.08.2014.
 */

public class TimerEditFragment extends DialogFragment {

    private static final String TITLE = "Select Schedule Time";
    private static final String YES = "Save";
    private static final String NO = "Cancel";
    private TimePicker timePicker;

    public TimerEditFragment() {
        //..
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        timePicker = new TimePicker(getActivity());
        timePicker.setIs24HourView(true);
        timePicker.setCurrentHour(0);
        timePicker.setCurrentMinute(0);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(TITLE).setPositiveButton(YES, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                saveSelectedTime();
            }
        }).setNegativeButton(NO, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                removeTime();
            }
        });
        builder.setView(timePicker);
        return builder.create();
    }

    private void saveSelectedTime() {
        TextView timer = (TextView) getActivity().findViewById(R.id.create_schedule_timer);
        timer.setText(timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute());
    }

    private void removeTime() {
        timePicker.setCurrentHour(0);
        timePicker.setCurrentMinute(0);
    }

}