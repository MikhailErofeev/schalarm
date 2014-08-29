package application.alarm;

import org.joda.time.DateTime;
import application.utils.entitys.MusicTrack;

import java.io.Serializable;
import java.util.HashSet;

/**
 * Created by FFX20413 on 28.08.2014.
 */
public class AlarmRule implements Serializable {

    private MusicTrack musicTrack;
    private DateTime alarmTime;
    private int queryCount = 1;
    private HashSet<String> selectedTags;
    private boolean onOffState;
    private boolean useRandomMusicTrack;

    public AlarmRule() {
        selectedTags = new HashSet<>();
    }

    public MusicTrack getMusicTrack() {
        return musicTrack;
    }

    public void setMusicTrack(MusicTrack selectedTrack) {
        this.musicTrack = selectedTrack;
    }

    public DateTime getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(DateTime alarmTime) {
        this.alarmTime = alarmTime;
    }

    public int getQueryCount() {
        return queryCount;
    }

    public void setQueryCount(int queryCount) {
        this.queryCount = queryCount;
    }

    public HashSet<String> getSelectedTags() {
        return selectedTags;
    }

    public void setSelectedTags(HashSet<String> selectedTags) {
        this.selectedTags = selectedTags;
    }

    public boolean isOnState() {
        return onOffState;
    }

    public void setOnState(boolean onOffState) {
        this.onOffState = onOffState;
    }

    public boolean isUseRandomMusicTrack() {
        return useRandomMusicTrack;
    }

    public void setUseRandomMusicTrack(boolean useRandomMusicTrack) {
        this.useRandomMusicTrack = useRandomMusicTrack;
    }
}
