package com.example.schalarm_android_app.alarm;

/**
 * Created by FFX20413 on 23.08.2014.
 */
public class AlarmTaskService {

    public static final String ALARM_THREAD_NAME = "alarm-thread";
    private AlarmTask task;

    public AlarmTaskService() {
    }

    public void startTask(AlarmTask task) {
        shutdownTask();
        this.task = task;
        Thread taskThread = new Thread(task, ALARM_THREAD_NAME + "_" + task);
        taskThread.setDaemon(true);
        taskThread.start();
    }

    public void shutdownTask() {
        if (task != null) {
            task.interrupt();
            task = null;
        }
    }
}
