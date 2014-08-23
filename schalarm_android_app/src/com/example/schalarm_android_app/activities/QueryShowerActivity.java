package com.example.schalarm_android_app.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.schalarm_android_app.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by FFX20413 on 23.08.2014.
 */
public class QueryShowerActivity extends Activity {
    // TODO Сюда через апи по тегу вытащить коллекцию вопросов!! и колекцию ответов с 1 правильным, типа isTrue O_o
    private static Set<String> tags = new HashSet<String>() {{
        add("Programming");
    }};

    private TextView questionText;
    private LinearLayout answers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qa);
        questionText = (TextView) findViewById(R.id.question_text);
        questionText.setText("Custom text");
        answers = (LinearLayout) findViewById(R.id.answers_list);

        ArrayList<View> buttons = new ArrayList<>();
        Button exampleBtn = new Button(this.getApplicationContext());
        buttons.add(exampleBtn);
        Button closeBtn = new Button(this.getApplicationContext());
        buttons.add(exampleBtn);
        buttons.add(closeBtn);

        answers.addTouchables(buttons);
    }

    @Override
    public void onBackPressed() {

    }
}
