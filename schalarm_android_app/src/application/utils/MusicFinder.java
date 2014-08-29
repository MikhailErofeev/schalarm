package application.utils;

import android.app.Activity;
import android.database.Cursor;
import application.utils.entitys.MusicTrack;

import java.util.ArrayList;
import java.util.List;

import static android.provider.MediaStore.Audio.Media.*;

public class MusicFinder {

    private final static String[] INTERESTED_COLUMN_TAGS = {_ID, DATA, DISPLAY_NAME, DURATION, ARTIST};
    private final static String SORT_ORDER = ARTIST + " " + "ASC";

    public static List<MusicTrack> getAllAvailableMusicTracks(Activity context) {
        List<MusicTrack> musicTracks = new ArrayList<>();
        Cursor media = getTracksCursor(context);
        try {
            int mediaCount = media.getCount();
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
        }
        return musicTracks;
    }

    private static Cursor getTracksCursor(Activity context) {
        return context.managedQuery(EXTERNAL_CONTENT_URI, INTERESTED_COLUMN_TAGS, null, null, SORT_ORDER);
    }
}
