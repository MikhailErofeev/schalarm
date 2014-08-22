package com.example.schalarm_android_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.example.schalarm_android_app.main_settings.ScheduleCreateActivity;
import com.github.mikhailerofeev.scholarm.local.stuff.GuiceModule;
import com.google.inject.Guice;

public class StartActivity extends Activity {

    static {
        Guice.createInjector(new GuiceModule());
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent().setClass(this, ScheduleCreateActivity.class));
        setContentView(R.layout.main);
    }
}
