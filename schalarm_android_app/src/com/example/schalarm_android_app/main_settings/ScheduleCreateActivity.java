package com.example.schalarm_android_app.main_settings;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.SubMenu;
import android.view.View;
import android.widget.*;
import com.example.schalarm_android_app.R;
import com.example.schalarm_android_app.main_settings.widgets.*;
import com.github.mikhailerofeev.scholarm.api.services.QuestionsService;
import com.github.mikhailerofeev.scholarm.local.services.QuestionsServiceImpl;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by FFX20413 on 22.08.2014.
 */
public class ScheduleCreateActivity extends Activity {

    @Inject
    private QuestionsService questionsService;

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

    private LinearLayout timerPluONOFFSwitchContainer;
    private OnOffWidget onOffWidget;
    private LinearLayout tagContainer;

    private TagsSelectElement tagsSelectElement;
    private Button saveButton;
    private Button clearButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }

    private void initInstanceElements() {
        onOffWidget = new OnOffWidget(this);
        tagsSelectElement = new TagsSelectElement(this);
    }

    private void setListeners() {
        setTagContainerListener();
    }

    private void setTagContainerListener() {
        tagContainer.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                for (int i = 0; i < 10; i++) {
                    SubMenu subMenu = menu.addSubMenu("Tag " + i);
                    for (int j = 0; j < 10; j++) {
                        subMenu.add("subTag " + i);
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
