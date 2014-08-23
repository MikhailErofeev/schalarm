package com.example.schalarm_android_app.activities.elements;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.github.mikhailerofeev.scholarm.api.entities.QuestionTheme;

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
            view = new SelectElement(parent, topThemes.get(i).getName());
            SelectElement selectElement = (SelectElement) view;
            setMainThemeListener(selectElement);
            setCheckBocksForMainThemeListener(selectElement);
        }
        return view;
    }

    private void setCheckBocksForMainThemeListener(final SelectElement selectElement) {
        selectElement.getCheckBox().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (QuestionTheme topTheme : topThemes) {
                    if (topTheme.getName().equals(selectElement.getTag())) {
                        for (QuestionTheme questionTheme : topTheme.getChildren()) {
                            if (selectElement.isChecked()) {
                                tagSelectedListener.tagSelected(questionTheme.getName());
                            } else {
                                tagSelectedListener.tagUnselected(questionTheme.getName());
                            }
                        }
                    }
                }
            }
        });
    }

    private void setMainThemeListener(final SelectElement selectElement) {
        selectElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (QuestionTheme topTheme : topThemes) {
                    if (topTheme.getName().equals(selectElement.getTag())) {
                        parent.getFragmentManager().beginTransaction().show(new SubTagsSelectFragment(topTheme.getChildren()));
                        break;
                    }
                }
            }
        });
    }

    private static class SelectElement extends LinearLayout {

        private TextView tagName;
        private CheckBox checkBox;
        private LinearLayout leftGravity;
        private LinearLayout rightGravity;

        public SelectElement(Context context, String name) {
            super(context);
            setOrientation(HORIZONTAL);

            leftGravity = new LinearLayout(context);
            rightGravity = new LinearLayout(context);
            rightGravity.setGravity(Gravity.RIGHT);
            leftGravity.setGravity(Gravity.LEFT);


            tagName = new TextView(context);
            tagName.setText(name);
            checkBox = new CheckBox(context);

            leftGravity.addView(tagName);
            rightGravity.addView(checkBox);
            addView(leftGravity);
            addView(rightGravity);
        }

        public boolean isChecked() {
            return checkBox.isChecked();
        }

        public void setChecked(boolean value) {
            checkBox.setChecked(value);
        }

        public String getTag() {
            return tagName.getText().toString();
        }

        public CheckBox getCheckBox() {
            return checkBox;
        }
    }
}
