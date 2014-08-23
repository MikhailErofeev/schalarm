package com.example.schalarm_android_app.activities.elements;

import android.app.Activity;
import android.content.Context;
import android.widget.ListView;
import com.example.schalarm_android_app.activities.TagsSelectorActivity;
import com.github.mikhailerofeev.scholarm.api.entities.QuestionTheme;

import java.util.List;

/**
 * Created by FFX20413 on 23.08.2014.
 */
public class ThemeListView extends ListView {

    private TagSelectedListener tagSelectedListener;

    public ThemeListView(Activity context, TagSelectedListener listener, List<QuestionTheme> topTheme) {
        super(context);
        this.tagSelectedListener = listener;
        setAdapter(new ThemeSelectorAdapter(context, topTheme, tagSelectedListener));
    }
}
