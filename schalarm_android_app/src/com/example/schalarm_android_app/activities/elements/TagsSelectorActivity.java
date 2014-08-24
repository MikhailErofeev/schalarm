package com.example.schalarm_android_app.activities.elements;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import com.example.schalarm_android_app.activities.callbacks.TagSelectActivityCallBack;
import com.example.schalarm_android_app.utils.InjectorApplication;
import com.github.mikhailerofeev.scholarm.api.services.QuestionsService;

import java.util.HashSet;

/**
 * Created by FFX20413 on 23.08.2014.
 */
public class TagsSelectorActivity extends Activity implements TagSelectedListener {

    public final static int GET_SELECTED_TAG_REQUEST_CODE = 0x25;
    public static final String SELECTED_TAGS_INTENT_KEY = "selected_tags_intent_key";

    private LinearLayout mainLay;
    private HashSet<String> selectedTags;
    private HashSet<String> selectedTagsFromScheduleCreator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mainLay = new LinearLayout(this);
        startActionMode(new TagSelectActivityCallBack(this));
        selectedTags = new HashSet<>();
        Intent intent = getIntent();
        if (intent != null) {
            selectedTagsFromScheduleCreator = (HashSet<String>) intent.getSerializableExtra(SELECTED_TAGS_INTENT_KEY);
        } else {
            selectedTagsFromScheduleCreator = new HashSet<>();
        }
        QuestionsService questionsService = InjectorApplication.get(QuestionsService.class);
        mainLay.addView(new ThemeListView(this, this, selectedTagsFromScheduleCreator, questionsService.getTopLevelThemes()));
        setContentView(mainLay);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void tagUnselected(String tag) {
        selectedTags.remove(tag);
    }

    @Override
    public void tagSelected(String tag) {
        selectedTags.add(tag);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(TagsSelectorActivity.class.getCanonicalName(), selectedTags);
        if (selectedTags != null && !selectedTags.isEmpty()) {
            intent.putExtra(TagsSelectorActivity.class.getCanonicalName(), selectedTags);
        } else {
            intent.putExtra(TagsSelectorActivity.class.getCanonicalName(), selectedTagsFromScheduleCreator);
        }
        setResult(GET_SELECTED_TAG_REQUEST_CODE, intent);
        finish();
        super.onBackPressed();
    }
}
