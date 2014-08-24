package com.example.schalarm_android_app.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class StartActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent();
        intent.setClass(this, QueryShowerActivity.class);
        startActivity(intent);
//        startActivity(new Intent().setClass(this, ScheduleCreateActivity.class));
        finish();
    }
}
