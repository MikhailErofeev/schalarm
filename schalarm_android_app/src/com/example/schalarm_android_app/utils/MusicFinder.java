package com.example.schalarm_android_app.utils;

import android.app.Activity;
import android.database.Cursor;
import com.example.schalarm_android_app.utils.entitys.MusicTrack;

import java.util.ArrayList;

import static android.provider.MediaStore.Audio.Media.*;

public class MusicFinder {

    private final static String[] INTERESTED_COLUMN_TAGS = {_ID, DATA, DISPLAY_NAME, DURATION, ARTIST};
    private final static String SORT_ORDER = ARTIST + " " + "ASC";

    public static ArrayList<MusicTrack> getAllAvailableMusicTracks(Activity context) {
        Cursor media = getTracksCursor(context);
        int mediaCount = media.getCount();
        ArrayList<MusicTrack> musicTracks = new ArrayList<>(mediaCount);
        try {
            for (int i = 0; i < mediaCount; i++) {
                final boolean moved = media.moveToPosition(i);
                if (moved) {
                    final long id = media.getLong(media.getColumnIndexOrThrow(_ID));
                    final long trackDuration = media.getColumnIndexOrThrow(DURATION);
                    final String fileName = media.getString(media.getColumnIndexOrThrow(DATA));
                    final String trackName = media.getString(media.getColumnIndexOrThrow(DISPLAY_NAME));
                    final String artistName = media.getString(media.getColumnIndexOrThrow(ARTIST));
                    musicTracks.add(new MusicTrack(fileName, trackName, artistName, id, trackDuration));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            media.close();
        }
        return musicTracks;
    }

    private static Cursor getTracksCursor(Activity context) {
        return context.managedQuery(EXTERNAL_CONTENT_URI, INTERESTED_COLUMN_TAGS, null, null, SORT_ORDER);
    }
}
