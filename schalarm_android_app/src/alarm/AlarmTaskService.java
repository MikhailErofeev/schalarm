package alarm;

import com.google.inject.Singleton;

/**
 * Created by FFX20413 on 23.08.2014.
 */
@Singleton
public class AlarmTaskService {

    public static final String ALARM_THREAD_NAME = "alarm-thread";
    private AlarmTask task;

    public void startTask(AlarmTask task) {
        shutdownTask();
        this.task = task;
        Thread taskThread = new Thread(task, ALARM_THREAD_NAME + "_" + task);
        taskThread.setDaemon(true);
        taskThread.start();
    }

    public AlarmTask geAlarmTask() {
        return task;
    }

    public void shutdownTask() {
        if (task != null) {
            task.interrupt();
            task = null;
        }
    }
}
