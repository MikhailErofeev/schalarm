package com.example.schalarm_android_app.alarm;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by FFX20413 on 23.08.2014.
 */
public class AlarmManager {

    public static final String ALARM_THREAD_NAME = "alarm-thread";
    private final Map<Task, Thread> task2thread;

    public AlarmManager() {
        task2thread = new HashMap<>();
    }

    public void startTask(Task task) {
        shutdownTask(task);
        Thread thread = new Thread(task, ALARM_THREAD_NAME + "_" + task.hashCode());
        thread.start();
        task2thread.put(task, thread);
    }

    public void shutdownTask(Task task) {
        Thread thread = task2thread.get(task);
        if (thread != null) {
            thread.interrupt();
        }
    }
}
