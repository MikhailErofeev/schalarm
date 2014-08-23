package com.example.schalarm_android_app.alarm;

import android.app.Application;

import javax.inject.Inject;

/**
 * @author m-erofeev
 * @since 23.08.14
 */
public class AlarmTaskManager {

    private final Application application;

    @Inject
    public AlarmTaskManager(Application application) {
        this.application = application;
    }

    public void saveAlarmTask(AlarmTask task) {

    }

    public AlarmTask restoreAlarmTask() {
        return null;
    }
}
