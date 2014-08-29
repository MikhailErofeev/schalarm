package application.activities.main_tab_fragments.tabs;

import application.activities.elements.*;
import application.activities.main_tab_fragments.faces.RuleChangeListener;
import application.alarm.AlarmRule;
import application.alarm.AlarmTask;
import application.alarm.AlarmTaskService;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.schalarm_android_app.R;
import org.joda.time.DateTime;
import application.utils.InjectorApplication;
import application.utils.MusicFinder;
import application.utils.SettingsSaver;
import application.utils.entitys.MusicTrack;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by FFX20413 on 27.08.2014.
 */
public class CreateAlarmsFragment extends Fragment implements RuleChangeListener {

    private static final int CANT_BE_LOWER = 1;

    public static final String ALARM_TIME_PICKER_FRAGMENT_KEY = "alarm_time_picker_fragment_key";
    public static final String AVAILABLE_TRACKS_FRAGMENT_KEY = "available_tracks_fragment_key";
    public static final String SELECTED_TAG_SHOWER_FRAGMENT_KEY = "selected_tag_shower_fragment_key";

    private AlarmRule alarmRule;

    private Switch onOffSwitch;
    private SeekBar countOfQuerySeekBar;

    private TextView scheduleTimer;
    private TextView authorNameView;
    private TextView trackNameView;
    private TextView seekBarProgressView;

    private Button showTagButton;
    private Button selectTagButton;
    private Button selectFromAvailableButton;

    private AlarmTaskService alarmTaskService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        alarmTaskService = InjectorApplication.get(AlarmTaskService.class);
        View mainPage = inflater.inflate(R.layout.create_alarm_layout, container, false);
        findAllElements(mainPage);
        setListeners();
        alarmRule = SettingsSaver.loadSettings(getActivity());
        if (alarmRule != null) {
            //   setDataFromSettings(setting);
        } else {
            alarmRule = new AlarmRule();
        }
        return mainPage;
    }

    @Override
    public void trackHasSelected(MusicTrack musicTrack) {
        alarmRule.setMusicTrack(musicTrack);
        refreshViewTrackInfo();
    }

    @Override
    public void timeHasChanged(DateTime time, int hours, int minutes) {
        String hour = hours < 10 ? 0 + String.valueOf(hours) : String.valueOf(hours);
        String minute = minutes < 10 ? 0 + String.valueOf(minutes) : String.valueOf(minutes);
        scheduleTimer.setText(hour + ":" + minute);
        alarmRule.setAlarmTime(time);
        updateAlarmTime();
    }

    @Override
    public DateTime getCurrentSettedTime() {
        return alarmRule.getAlarmTime();
    }

    @Override
    public void tagUnselected(String tag) {
        alarmRule.getSelectedTags().add(tag);
    }

    @Override
    public void tagsSelected(Set<String> tags) {
        alarmRule.getSelectedTags().addAll(tags);
    }

    @Override
    public void tagSelected(String tag) {
        alarmRule.getSelectedTags().add(tag);
    }

    public void updateAlarmTime() {
        if (alarmRule.getAlarmTime() != null) {
            alarmTaskService.shutdownTask();
            startUpdatedTaskIfOn();
        }
    }

    @Override
    public void onDetach() {
        if (onOffSwitch.isChecked()) {
            SettingsSaver.saveSettings(getActivity(), alarmRule);
        } else {
            SettingsSaver.removeSettings(getActivity());
        }
        super.onDetach();
    }

    private void setOnSeekBarListener() {
        countOfQuerySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress < CANT_BE_LOWER) {
                    onProgressChanged(seekBar, CANT_BE_LOWER, fromUser);
                }
                alarmRule.setQueryCount(progress);
                seekBarProgressView.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void setOnAvailableButtonListener() {
        List<MusicTrack> allAvailableMusicTracks = MusicFinder.getAllAvailableMusicTracks(getActivity());
        final AvailableTrackFragment availableTrackFragment = new AvailableTrackFragment(this, allAvailableMusicTracks);
        selectFromAvailableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                        .beginTransaction()
                        .add(availableTrackFragment, AVAILABLE_TRACKS_FRAGMENT_KEY)
                        .commit();
            }
        });
    }

    private void setOnScheduleTimerClickListener() {
        final TimerEditFragment timerEditFragment = new TimerEditFragment(this);
        scheduleTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                        .beginTransaction()
                        .add(timerEditFragment, ALARM_TIME_PICKER_FRAGMENT_KEY)
                        .commit();
            }
        });
    }

    private void setOnOffWidgetListener() {
        onOffSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((Switch) v).isChecked()) {
                    startUpdatedTaskIfOn();
                } else {
                    alarmTaskService.shutdownTask();
                }
            }
        });
    }

    private void startUpdatedTaskIfOn() {
        if (onOffSwitch.isChecked()) {
            AlarmTask task = new AlarmTask(getActivity(), alarmRule);
            alarmTaskService.startTask(task);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null && TagsSelectorActivity.GET_SELECTED_TAG_REQUEST_CODE == resultCode) {
            alarmRule.setSelectedTags((HashSet<String>) data.getSerializableExtra(TagsSelectorActivity.class.getCanonicalName()));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setSelectTagListenerButton() {
        selectTagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), TagsSelectorActivity.class);
                intent.putExtra(TagsSelectorActivity.SELECTED_TAGS_INTENT_KEY, alarmRule.getSelectedTags());
                startActivityForResult(intent, TagsSelectorActivity.GET_SELECTED_TAG_REQUEST_CODE);
            }
        });
    }

    private void setShowSelectedTagButton() {
        showTagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                        .beginTransaction()
                        .add(new SelectedTagFragmentShower(alarmRule.getSelectedTags()), SELECTED_TAG_SHOWER_FRAGMENT_KEY)
                        .commit();
            }
        });
    }

    private void refreshViewTrackInfo() {
        trackNameView.setText(alarmRule.getMusicTrack().getTrackName());
        authorNameView.setText(alarmRule.getMusicTrack().getArtistName());
    }

    private void findAllElements(View parent) {
        seekBarProgressView = (TextView) parent.findViewById(R.id.create_alarm_question_count_seek_bar_result);
        onOffSwitch = (Switch) parent.findViewById(R.id.create_alarm_on_off_switch);
        showTagButton = (Button) parent.findViewById(R.id.create_alarm_show_tag_button);
        selectTagButton = (Button) parent.findViewById(R.id.create_alarm_select_tag_button);
        scheduleTimer = (TextView) parent.findViewById(R.id.create_alarm_timer);
        selectFromAvailableButton = (Button) parent.findViewById(R.id.create_alarm_get_available_tracks);
        authorNameView = (TextView) parent.findViewById(R.id.create_alarm_track_author_name_info);
        trackNameView = (TextView) parent.findViewById(R.id.create_alarm_track_name_info);
        countOfQuerySeekBar = (SeekBar) parent.findViewById(R.id.create_alarm_seek_bar);
    }

    private void setListeners() {
        setOnOffWidgetListener();
        setOnScheduleTimerClickListener();
        setSelectTagListenerButton();
        setShowSelectedTagButton();
        setOnAvailableButtonListener();
        setOnSeekBarListener();
    }
}