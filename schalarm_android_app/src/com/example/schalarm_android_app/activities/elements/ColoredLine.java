package com.example.schalarm_android_app.activities.elements;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by FFX20413 on 24.08.2014.
 */
public class ColoredLine extends View {

    public ColoredLine(Context context, int color, int height) {
        super(context);
        setBackgroundColor(color);
        setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, height));
    }
}
