package com.example.schalarm_android_app.alarm;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import com.example.schalarm_android_app.activities.QueryShowerActivity;
import com.example.schalarm_android_app.utils.entitys.MusicTrack;
import org.joda.time.DateTime;

import java.io.IOException;
import java.util.Set;

/**
 * Created by FFX20413 on 23.08.2014.
 */
public class AlarmTask implements Runnable {

    private final MediaPlayer mediaPlayer;
    private final MusicTrack musicTrack;
    private final Set<String> tags;
    private final long taskStartTimeInMillis;
    private final Activity parent;


    public AlarmTask(Activity parent, Set<String> tags, MusicTrack musicTrack, long taskStartTimeInMillis) {
        this.mediaPlayer = new MediaPlayer();
        this.tags = tags;
        this.musicTrack = musicTrack;
        
        this.taskStartTimeInMillis = taskStartTimeInMillis;
        this.parent = parent;
    }

    @Override
    public void run() {
        try {
            while (DateTime.now().getMillis() < taskStartTimeInMillis) {
                long diff = taskStartTimeInMillis - DateTime.now().getMillis();
                Thread.sleep(diff);
            }
            startPlayMusic();
            runQueryActivity();
            //@todo add new task!
        } catch (InterruptedException ignored) {
            // look down
        }
    }

    private void runQueryActivity() {
        Intent intent = new Intent();
        intent.setClass(parent, QueryShowerActivity.class);
        parent.startActivity(intent);
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

    public void interrupt() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        Thread.currentThread().interrupt();
    }
}
