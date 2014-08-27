package com.example.schalarm_android_app.activities.elements;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.*;

/**
 * Created by FFX20413 on 27.08.2014.
 */
public class SelectedCheckBoxElement extends LinearLayout {

    private TextView tagName;
    private CheckBox checkBox;
    private LinearLayout leftGravity;
    private LinearLayout rightGravity;

    public SelectedCheckBoxElement(Context context, String name) {
        super(context);
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
        setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT));

        leftGravity = new LinearLayout(context);
        rightGravity = new LinearLayout(context);
        rightGravity.setGravity(Gravity.RIGHT);
        leftGravity.setGravity(Gravity.LEFT);
        leftGravity.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 2f));

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

    public String getTagName() {
        return tagName.getText().toString();
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }
}