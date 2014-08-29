package application.alarm;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import application.activities.QueryShowerActivity;
import application.utils.entitys.MusicTrack;
import org.joda.time.DateTime;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.Set;

/**
 * Created by FFX20413 on 23.08.2014.
 */
public class AlarmTask implements Runnable {

    private final MediaPlayer mediaPlayer;

    private final MusicTrack musicTrack;
    private final Set<String> tags;
    private final Activity parent;
    private final int minimalRightAnswers;
    private boolean isActive;
    private DateTime alarmTime;

    public AlarmTask(Activity parent, AlarmRule alarmRule) {
        this.minimalRightAnswers = alarmRule.getQueryCount();
        this.mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        this.tags = alarmRule.getSelectedTags();
        this.musicTrack = alarmRule.getMusicTrack();
        this.alarmTime = alarmRule.getAlarmTime();
        this.parent = parent;
        isActive = false;
    }

    @Override
    public void run() {
        try {
            while (DateTime.now().getMillis() < alarmTime.getMillis()) {
                long diff = alarmTime.getMillis() - DateTime.now().getMillis();
                Thread.sleep(diff / 2); //to better accuracy
            }
            isActive = true;
            startPlayMusic();
            runQuestionsActivity();
            //@todo add new task!
        } catch (InterruptedException ignored) {
            System.out.println(Thread.currentThread().getName() + " interrupted");
        }
    }

    public boolean isActive() {
        return isActive;
    }

    private void runQuestionsActivity() {
        Intent intent = new Intent();
        intent.setClass(parent, QueryShowerActivity.class);
        parent.startActivity(intent);
    }

    public int getMinimalRightAnswers() {
        return minimalRightAnswers;
    }

    private void startPlayMusic() {
        try {
            if (musicTrack != null) {
                mediaPlayer.setDataSource(musicTrack.getFilePath());
            } else {
                FileDescriptor stubMusic = parent.getApplication().getAssets().openFd("ussr_dnb_alarm.mp3").getFileDescriptor();
                mediaPlayer.setDataSource(stubMusic);
            }
            mediaPlayer.setLooping(true);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void interrupt() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        isActive = false;
        Thread.currentThread().interrupt();
    }

    public Set<String> getTags() {
        return tags;
    }
}
