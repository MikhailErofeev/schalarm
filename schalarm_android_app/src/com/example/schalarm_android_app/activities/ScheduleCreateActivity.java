package com.example.schalarm_android_app.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import com.example.schalarm_android_app.R;
import com.example.schalarm_android_app.activities.callbacks.ScheduleCreateActivityCallBack;
import com.example.schalarm_android_app.activities.elements.*;
import com.example.schalarm_android_app.alarm.AlarmTask;
import com.example.schalarm_android_app.alarm.AlarmTaskService;
import com.example.schalarm_android_app.main_settings.widgets.OnOffWidget;
import com.example.schalarm_android_app.utils.InjectorApplication;
import com.example.schalarm_android_app.utils.MusicFinder;
import com.example.schalarm_android_app.utils.entitys.MusicTrack;
import com.github.mikhailerofeev.scholarm.api.services.QuestionsService;
import org.joda.time.DateTime;

import java.util.HashSet;
import java.util.List;
import java.util.Random;

/**
 * Created by FFX20413 on 22.08.2014.
 */
public class ScheduleCreateActivity extends Activity implements TrackSelectedListener {

    public static final String SCHEDULE_TIMER_KEY_FRAGMENT = "schedule_timer_key_fragment";
    public static final String SHOW_SELECTED_TAGS = "Show selected Tags";
    public static final String AVAILABLE_TRACK_FRAGMENT = "available_track_fragment";
    public static final int MAX_QUERY_COUNT = 10;

    private static MusicTrack selectedTrack;
    private static long timeToStartTask;
    private static int queryCount;

    private HashSet<String> selectedTags;

    private LinearLayout timerPluONOFFSwitchContainer;
    private OnOffWidget onOffWidget;
    private LinearLayout trackInfoLayout;

    private TextView scheduleTimer;
    private TextView authorNameView;
    private TextView trackNameView;

    private Button showTagButton;
    private Button selectTagButton;
    private Button generateRandomTrackButton;
    private Button selectFromAvailableButton;

    private SeekBar countOfQuerySeekBar;

    private Activity parentContext;
    private QuestionsService questionsService;
    private AlarmTaskService alarmTaskService;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        questionsService = InjectorApplication.get(QuestionsService.class);
        alarmTaskService = InjectorApplication.get(AlarmTaskService.class);
        parentContext = this;
        setContentView(R.layout.main_settings);
        startActionMode(new ScheduleCreateActivityCallBack(this));
        findAllElements();
        initInstanceElements();
        setElementsOnContainers();
        setListeners();
        updateAlarmTime();
    }

    private void setRandomMusic() {
        List<MusicTrack> allAvailableMusicTracks = MusicFinder.getAllAvailableMusicTracks(this);
        selectedTrack = allAvailableMusicTracks.get(new Random().nextInt(allAvailableMusicTracks.size()));
        refreshViewTrackInfo();
    }

    public void updateAlarmTime() {
        DateTime alarmTime = getAlarmTime();
        timeToStartTask = alarmTime.getMillis();
        alarmTaskService.shutdownTask();
        startUpdatedTaskIfOn();
    }

    public DateTime getAlarmTime() {
        String time = scheduleTimer.getText().toString();
        String[] hh2MM = time.split(":");
        int hours = Integer.parseInt(hh2MM[0]);
        int minutes = Integer.parseInt(hh2MM[1]);
        DateTime alarmTime = DateTime.now().withHourOfDay(hours).withMinuteOfHour(minutes).withSecondOfMinute(0);
        if (DateTime.now().isAfter(alarmTime)) {
            alarmTime = alarmTime.plusDays(1);
        }
        return alarmTime;
    }

    private void setElementsOnContainers() {
        timerPluONOFFSwitchContainer.addView(onOffWidget);
    }

    private void findAllElements() {
        timerPluONOFFSwitchContainer = (LinearLayout) findViewById(R.id.onOffSwitchContainer);
        showTagButton = (Button) findViewById(R.id.create_schedule_show_tag_button);
        selectTagButton = (Button) findViewById(R.id.create_schedule_select_tag_button);
        trackInfoLayout = (LinearLayout) findViewById(R.id.createScheduleTrackInfoLayout);
        scheduleTimer = (TextView) findViewById(R.id.create_schedule_timer);
        generateRandomTrackButton = (Button) findViewById(R.id.create_schedule_get_random_track);
        selectFromAvailableButton = (Button) findViewById(R.id.create_schedule_get_available_tracks);
        authorNameView = (TextView) findViewById(R.id.create_schedule_track_author_name_info);
        trackNameView = (TextView) findViewById(R.id.create_schedule_track_name_info);
        countOfQuerySeekBar = (SeekBar) findViewById(R.id.create_schedule_seek_bar);
    }

    private void initInstanceElements() {
        selectedTags = new HashSet<>();
        onOffWidget = new OnOffWidget(this);
    }

    private void setListeners() {
        setOnOffWidgetListener();
        setOnTrackLayoutClickListener();
        setOnScheduleTimerClickListener();
        setSelectTagListenerButton();
        setShowSelectedTagButton();
        setOnRandomSelectTrackButtonListener();
        setOnAvailableButtonListener();
        setOnSeekBarListener();
    }

    private void setOnSeekBarListener() {
        countOfQuerySeekBar.setMax(MAX_QUERY_COUNT);
        countOfQuerySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                queryCount = progress;
                // TODO
                System.out.println(progress);
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
        selectFromAvailableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<MusicTrack> allAvailableMusicTracks = MusicFinder.getAllAvailableMusicTracks(parentContext);
                getFragmentManager().beginTransaction().add(new AvailableTrackFragment(allAvailableMusicTracks), AVAILABLE_TRACK_FRAGMENT).commit();
            }
        });
    }

    private void setOnRandomSelectTrackButtonListener() {
        generateRandomTrackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRandomMusic();
            }
        });
    }

    private void setOnScheduleTimerClickListener() {
        scheduleTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().add(new TimerEditFragment(), SCHEDULE_TIMER_KEY_FRAGMENT).commit();
            }
        });
    }

    private void setOnTrackLayoutClickListener() {
        trackInfoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRandomMusic();
            }
        });
    }

    private void setOnOffWidgetListener() {
        onOffWidget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((OnOffWidget) v).isChecked()) {
                    startUpdatedTaskIfOn();
                } else {
                    alarmTaskService.shutdownTask();
                }
            }
        });
    }

    private void startUpdatedTaskIfOn() {
        if (onOffWidget.isChecked()) {
            AlarmTask task = new AlarmTask(this, selectedTags, null, timeToStartTask);
            alarmTaskService.startTask(task);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null && tagsSelectorActivityTmp.GET_SELECTED_TAG_REQUEST_CODE == resultCode) {
            selectedTags = (HashSet<String>) data.getSerializableExtra(tagsSelectorActivityTmp.class.getCanonicalName());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setSelectTagListenerButton() {
        final Activity parent = this;
        selectTagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(parent, tagsSelectorActivityTmp.class);
                intent.putExtra(tagsSelectorActivityTmp.SELECTED_TAGS_INTENT_KEY, selectedTags);
                startActivityForResult(intent, tagsSelectorActivityTmp.GET_SELECTED_TAG_REQUEST_CODE);
            }
        });
    }

    private void setShowSelectedTagButton() {
        showTagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().add(new SelectedTagFragmentShower(selectedTags), SHOW_SELECTED_TAGS).commit();
            }
        });
    }

    @Override
    public void trackHasSelected(MusicTrack musicTrack) {
        selectedTrack = musicTrack;
        refreshViewTrackInfo();
    }

    private void refreshViewTrackInfo() {
        trackNameView.setText(" Track name - " + selectedTrack.getTrackName());
        authorNameView.setText(" Track author - " + selectedTrack.getArtistName());
    }
}
