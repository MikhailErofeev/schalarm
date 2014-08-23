package com.example.schalarm_android_app.activities.elements;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.schalarm_android_app.activities.TagsSelectorActivity;
import com.github.mikhailerofeev.scholarm.api.entities.QuestionTheme;
import com.github.mikhailerofeev.scholarm.api.services.QuestionsService;

import java.util.List;

/**
 * Created by FFX20413 on 23.08.2014.
 */
public class ThemeSelectorAdapter extends BaseAdapter {

    private List<QuestionTheme> topThemes;
    private Activity parent;
    private TagSelectedListener tagSelectedListener;

    public ThemeSelectorAdapter(Activity parent, List<QuestionTheme> topThemes, TagSelectedListener tagSelectedListener) {
        this.topThemes = topThemes;
        this.parent = parent;
        this.tagSelectedListener = tagSelectedListener;
    }

    @Override
    public int getCount() {
        return topThemes.size();
    }

    @Override
    public Object getItem(int i) {
        return topThemes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup group) {
        if (view == null) {
            view = new TextView(parent);
            ((TextView) view).setText(topThemes.get(i).getName());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tagSelectedListener.tagSelected(((TextView) view).getText().toString());
                }
            });
        }
        return view;
    }
}
