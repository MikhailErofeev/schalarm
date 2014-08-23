package com.example.schalarm_android_app.alarm;

/**
 * Created by FFX20413 on 23.08.2014.
 */
public class AlarmManager {

    public static void startTask(Task task) {
        if (!isTaskRun(task)) {
            task.start();
        }
    }

    public static boolean isTaskRun(Task task) {
        return Thread.getAllStackTraces().containsKey(task);
    }

    public static void shutdownTask(Task task) {
        if (isTaskRun(task)) {
            for (Thread thread : Thread.getAllStackTraces().keySet()) {
                if (thread.equals(task)) {
                    thread.interrupt();
                }
            }
        }
    }
}
