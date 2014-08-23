package com.example.schalarm_android_app.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.example.schalarm_android_app.R;

public class StartActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent().setClass(this, ScheduleCreateActivity.class));
        setContentView(R.layout.main);
    }
}
