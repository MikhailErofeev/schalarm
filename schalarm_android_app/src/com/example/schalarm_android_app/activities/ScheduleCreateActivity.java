package com.example.schalarm_android_app.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.schalarm_android_app.R;
import com.example.schalarm_android_app.activities.elements.TagsSelectorActivity;
import com.example.schalarm_android_app.activities.elements.TimerEditFragment;
import com.example.schalarm_android_app.alarm.AlarmTask;
import com.example.schalarm_android_app.alarm.AlarmTaskService;
import com.example.schalarm_android_app.main_settings.widgets.OnOffWidget;
import com.example.schalarm_android_app.main_settings.widgets.TagsSelectElement;
import com.example.schalarm_android_app.utils.InjectorApplication;
import com.example.schalarm_android_app.utils.MusicFinder;
import com.example.schalarm_android_app.utils.entitys.MusicTrack;
import com.github.mikhailerofeev.scholarm.api.services.QuestionsService;
import org.joda.time.DateTime;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Created by FFX20413 on 22.08.2014.
 */
public class ScheduleCreateActivity extends Activity {

    public static final int SELECT_TRACK_REQUEST_CODE = 10;
    public static final String TRACK_INFO_TAG = "track_info_tag";
    public static final String SELECT_TAGS = "Select Tags";
    public static final String SCHEDULE_TIMER_KEY_FRAGMENT = "schedule_timer_key_fragment";


    private static MusicTrack selectedTrack;
    private static long timeToStartTask;

    private HashSet<String> selectedTags;

    private LinearLayout timerPluONOFFSwitchContainer;
    private OnOffWidget onOffWidget;
    private LinearLayout tagContainer;
    private LinearLayout trackInfoLayout;
    private TagsSelectElement tagsSelectElement;
    private TextView scheduleTimer;

    private TextView songGeneratorTextView;
    private Button saveButton;
    private Button clearButton;

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
        findAllElements();
        initInstanceElements();
        setElementsOnContainers();
        setListeners();
        setRandomMusic();
        updateAlarmTime();
    }

    private void setRandomMusic() {
        List<MusicTrack> allAvailableMusicTracks = MusicFinder.getAllAvailableMusicTracks(this);
        selectedTrack = allAvailableMusicTracks.get(new Random().nextInt(allAvailableMusicTracks.size()));
        songGeneratorTextView.setText(selectedTrack.getTrackName() + " " + selectedTrack.getArtistName());
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
        saveButton = (Button) findViewById(R.id.saveButton);
        clearButton = (Button) findViewById(R.id.clearButton);
        trackInfoLayout = (LinearLayout) findViewById(R.id.createScheduleTrackInfoLayout);
        scheduleTimer = (TextView) findViewById(R.id.create_schedule_timer);
        songGeneratorTextView = (TextView) findViewById(R.id.createScheduleTrackInfoTrackName);
        tagContainer = (LinearLayout) findViewById(R.id.create_schedule_tag_container);
    }

    private void initInstanceElements() {
        selectedTags = new HashSet<>();
        onOffWidget = new OnOffWidget(this);
        tagsSelectElement = new TagsSelectElement(this);
        saveButton.setVisibility(View.INVISIBLE);
        clearButton.setVisibility(View.INVISIBLE);

        TextView title = new TextView(parentContext);
        title.setText("Tags..,");
        tagsSelectElement.addView(title);
        tagContainer.addView(tagsSelectElement);
    }

    private void setListeners() {
        setOnOffWidgetListener();
        setOnTrackLayoutClickListener();
        setOnScheduleTimerClickListener();
        setOnTagClickListener();
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
        if (data != null && TagsSelectorActivity.GET_SELECTED_TAG_REQUEST_CODE == resultCode) {
            selectedTags = (HashSet<String>) data.getSerializableExtra(TagsSelectorActivity.class.getCanonicalName());
            System.out.println(selectedTags);
            if (data != null && TagsSelectorActivity.GET_SELECTED_TAG_REQUEST_CODE == resultCode) {
                selectedTags = (HashSet<String>) data.getSerializableExtra(TagsSelectorActivity.class.getCanonicalName());
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void setOnTagClickListener() {
        final Activity parent = this;
        tagContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(parent, TagsSelectorActivity.class);
                startActivityForResult(intent, TagsSelectorActivity.GET_SELECTED_TAG_REQUEST_CODE);
                intent.setClass(parent, TagsSelectorActivity.class);
                intent.putExtra(TagsSelectorActivity.SELECTED_TAGS_INTENT_KEY, selectedTags);
                startActivityForResult(intent, TagsSelectorActivity.GET_SELECTED_TAG_REQUEST_CODE);
            }
        });
    }
}
