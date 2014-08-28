package activities.elements;

import activities.main_tab_fragments.faces.SettingsChangeListener;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TimePicker;
import com.example.schalarm_android_app.R;
import org.joda.time.DateTime;

/**
 * Created by FFX20413 on 23.08.2014.
 */

public class TimerEditFragment extends DialogFragment {

    private static final int POINT_TO_MORE_ACCURACY = 0;
    private static final int POINT_TO_FUTURE = 1;

    private TimePicker timePicker;
    private DateTime alarmTime;
    private SettingsChangeListener settingsChangeListener;

    public TimerEditFragment(SettingsChangeListener settingsChangeListener) {
        this.settingsChangeListener = settingsChangeListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        timePicker = new TimePicker(getActivity());
        timePicker.setIs24HourView(true);
        alarmTime = settingsChangeListener.getCurrentSettedTime();
        refreshTo(alarmTime);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.time_select_fragment_title);
        builder.setPositiveButton(R.string.ok_button, makePositive());
        builder.setNegativeButton(R.string.cancel_button, makeNegative());
        builder.setView(timePicker);
        return builder.create();
    }

    private DialogInterface.OnClickListener makePositive() {
        return new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                settingsChangeListener.timeHasChanged(convertTime(), timePicker.getCurrentHour(), timePicker.getCurrentMinute());
            }
        };
    }

    private DialogInterface.OnClickListener makeNegative() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                refreshTo(alarmTime);
            }
        };
    }

    public DateTime convertTime() {
        DateTime alarmTime = DateTime.now()
                .withHourOfDay(timePicker.getCurrentHour())
                .withMinuteOfHour(timePicker.getCurrentMinute())
                .withSecondOfMinute(POINT_TO_MORE_ACCURACY);
        if (DateTime.now().isAfter(alarmTime)) {
            alarmTime = alarmTime.plusDays(POINT_TO_FUTURE);
        }
        return alarmTime;
    }

    private void refreshTo(@Nullable DateTime alarmTime) {
        if (alarmTime != null) {
            timePicker.setCurrentHour(alarmTime.getHourOfDay());
            timePicker.setCurrentMinute(alarmTime.getMinuteOfHour());
        }
    }
}