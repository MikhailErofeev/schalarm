package com.example.schalarm_android_app.utils;

import android.content.Context;
import com.example.schalarm_android_app.utils.entitys.MusicTrack;
import org.joda.time.DateTime;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by FFX20413 on 22.08.2014.
 */

public class SettingsSaver {

    public static final String FILE_NAME = "file_buffer";

    public static void saveSettings(Context context, Setting setting) {
        FileOutputStream fileIO = null;
        ObjectOutputStream objSaver = null;
        try {
            new File(context.getFilesDir(), FILE_NAME);
            fileIO = context.openFileOutput(FILE_NAME, Context.MODE_WORLD_READABLE);
            objSaver = new ObjectOutputStream(fileIO);
            objSaver.writeObject(setting);
            objSaver.close();
            fileIO.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void removeSettings(Context context) {
        context.deleteFile(FILE_NAME);
    }
    // FIXME da, govno, pozwe

    public static Setting loadSettings(Context context) {
        FileInputStream fileIO = null;
        ObjectInputStream objSaver = null;
        Setting setting = null;
        try {
            fileIO = context.openFileInput(FILE_NAME);
            objSaver = new ObjectInputStream(fileIO);
            setting = (Setting) objSaver.readObject();
            objSaver.close();
            fileIO.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return setting;
    }

    public static class Setting implements Serializable {

        HashSet<String> tags;
        MusicTrack musicTrack;
        String timerText;
        boolean onOffState;
        int seekProgressState;

        public Setting(int seekProgressState, boolean onOffState, HashSet<String> tags, MusicTrack musicTrack, String timeText) {
            this.timerText = timeText;
            this.musicTrack = musicTrack;
            this.tags = tags;
            this.onOffState = onOffState;
            this.seekProgressState = seekProgressState;
        }

        public HashSet<String> getTags() {
            return tags;
        }

        public MusicTrack getMusicTrack() {
            return musicTrack;
        }

        public String getTimerText() {
            return timerText;
        }

        public boolean getOnOffState() {
            return onOffState;
        }

        public int getSeekProgressState() {
            return seekProgressState;
        }
    }
}
