package com.example.schalarm_android_app.activities;

import android.app.Activity;
import android.os.Bundle;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
