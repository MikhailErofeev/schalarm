package com.example.schalarm_android_app.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.*;
import com.example.schalarm_android_app.R;
import com.example.schalarm_android_app.alarm.AlarmManager;
import com.example.schalarm_android_app.alarm.Task;
import com.example.schalarm_android_app.utils.entitys.MusicTrack;
import com.example.schalarm_android_app.main_settings.widgets.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by FFX20413 on 22.08.2014.
 */
public class ScheduleCreateActivity extends Activity {

    private static final Set<String> programmersTags = new HashSet<String>() {{
        add("Java");
        add("MySQL");
    }};

    private static final Set<String> physicsSubTag = new HashSet<String>() {{
        add("Thermodynamics");
        add("Optics");
    }};

    private final static HashMap<String, Set<String>> tags = new HashMap<String, Set<String>>() {{
        put("Programmers", programmersTags);
        put("Physics", physicsSubTag);
    }};
    public static final int SELECT_TRACK_REQUEST_CODE = 10;
    public static final String TRACK_INFO_TAG = "track_info_tag";

    private static Set<String> selectedTags = new HashSet<String>() {{
        add("Programmers");
    }};

    private static MusicTrack selectedTrack;
    private static long timeToStartTask;

    private LinearLayout timerPluONOFFSwitchContainer;
    private OnOffWidget onOffWidget;
    private LinearLayout tagContainer;
    private LinearLayout trackInfoLayout;
    private TagsSelectElement tagsSelectElement;

    private Button saveButton;
    private Button clearButton;

    private Activity parentContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentContext = this;
        setContentView(R.layout.main_settings);
        findAllElements();
        initInstanceElements();
        setElementsOnContainers();
        setListeners();
    }

    private void setElementsOnContainers() {
        timerPluONOFFSwitchContainer.addView(onOffWidget);
    }

    private void findAllElements() {
        timerPluONOFFSwitchContainer = (LinearLayout) findViewById(R.id.onOffSwitchContainer);
        saveButton = (Button) findViewById(R.id.saveButton);
        clearButton = (Button) findViewById(R.id.clearButton);
        tagContainer = (LinearLayout) findViewById(R.id.create_schedule_tag_container);
        trackInfoLayout = (LinearLayout) findViewById(R.id.createScheduleTrackInfoLayout);
    }

    private void initInstanceElements() {
        onOffWidget = new OnOffWidget(this);
        tagsSelectElement = new TagsSelectElement(this);
        saveButton.setVisibility(View.INVISIBLE);
        clearButton.setVisibility(View.INVISIBLE);

        TextView title = new TextView(parentContext);
        title.setText("Tags..,");

        tagsSelectElement.addView(title);
    }

    private void setListeners() {
        setTagContainerListener();
        setSaveButtonClickListener();
        setOnOffWidgetListener();
        setOnTrackLayoutClickListener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            switch (requestCode) {
                case SELECT_TRACK_REQUEST_CODE: {
                    setSelectedTrack(data);
                }
                break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setSelectedTrack(Intent data) {
        selectedTrack = (MusicTrack) data.getSerializableExtra(TRACK_INFO_TAG);
    }

    private void setOnTrackLayoutClickListener() {
        trackInfoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent requester = new Intent();
                requester.setClass(parentContext, SelectTrackActivity.class);
                startActivityForResult(requester, SELECT_TRACK_REQUEST_CODE);
            }
        });
    }

    private void setOnOffWidgetListener() {
        final Task task = new Task(selectedTags, "name", selectedTrack, timeToStartTask);
        onOffWidget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onOffWidget.isActivated()) {
                    AlarmManager.startTask(task);
                } else {
                    AlarmManager.shutdownTask(task);
                }
            }
        });
    }

    private void setSaveButtonClickListener() {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // tags
        return super.onOptionsItemSelected(item);
    }

    private void setTagContainerListener() {
        tagContainer.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

                for (Map.Entry<String, Set<String>> entry : tags.entrySet()) {
                    SubMenu subMenu = menu.addSubMenu(entry.getKey());
                    for (String subTag : entry.getValue()) {
                        subMenu.add(subTag);
                    }
                }
            }
        });

        tagContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openContextMenu(v);
            }
        });
    }
}
