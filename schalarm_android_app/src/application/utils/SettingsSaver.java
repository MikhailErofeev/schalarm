package application.utils;

import application.alarm.AlarmRule;
import android.content.Context;

import java.io.*;

/**
 * Created by FFX20413 on 22.08.2014.
 */

public class SettingsSaver {

    public static final String FILE_NAME = "file_buffer";

    public static void saveSettings(Context context, AlarmRule alarmRule) {
        FileOutputStream fileIO = null;
        ObjectOutputStream objSaver = null;
        try {
            new File(context.getFilesDir(), FILE_NAME);
            fileIO = context.openFileOutput(FILE_NAME, Context.MODE_WORLD_READABLE);
            objSaver = new ObjectOutputStream(fileIO);
            objSaver.writeObject(alarmRule);
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

    public static AlarmRule loadSettings(Context context) {
        FileInputStream fileIO = null;
        ObjectInputStream objSaver = null;
        AlarmRule setting = null;
        try {
            fileIO = context.openFileInput(FILE_NAME);
            objSaver = new ObjectInputStream(fileIO);
            setting = (AlarmRule) objSaver.readObject();
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
}
