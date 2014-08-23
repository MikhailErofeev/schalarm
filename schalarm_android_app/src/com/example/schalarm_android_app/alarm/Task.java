package com.example.schalarm_android_app.alarm;

import android.media.MediaPlayer;
import com.example.schalarm_android_app.utils.entitys.MusicTrack;

import java.io.IOException;
import java.util.Set;

/**
 * Created by FFX20413 on 23.08.2014.
 */
public class Task extends Thread {

    private MediaPlayer mediaPlayer;
    private MusicTrack musicTrack;
    private Set<String> tags;
    private final long taskStartTime;

    public Task(Set<String> tags, String threadName, MusicTrack musicTrack, long taskStartTime) {
        super(threadName);
        mediaPlayer = new MediaPlayer();
        this.tags = tags;
        this.musicTrack = musicTrack;
        this.taskStartTime = taskStartTime;
    }

    @Override
    public void run() {
        try {
            sleep(taskStartTime);
            startPlayMusic();
            runQueryActivity();
        } catch (InterruptedException ignored) {
            // look down
        }
    }

    private void runQueryActivity() {
        // TODO serialize name and push to Activity
    }

    private void startPlayMusic() {
        try {
            mediaPlayer.setDataSource(musicTrack.getFilePath());
            mediaPlayer.setLooping(true);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void interrupt() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        super.interrupt();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        Task task = (Task) o;

        if (taskStartTime != task.taskStartTime) return false;
        if (tags != null ? !tags.equals(task.tags) : task.tags != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tags != null ? tags.hashCode() : 0;
        result = 31 * result + (int) (taskStartTime ^ (taskStartTime >>> 32));
        return result;
    }
}
