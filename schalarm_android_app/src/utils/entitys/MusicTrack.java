package utils.entitys;

import java.io.Serializable;

public class MusicTrack implements Serializable {

    private String filePath;
    private String trackName;
    private String artistName;
    private long trackID;
    private long trackDuration;

    public MusicTrack(String filePath, String trackName, String artistName, long trackID, long trackDuration) {
        this.filePath = filePath;
        this.trackName = trackName;
        this.trackID = trackID;
        this.trackDuration = trackDuration;
        if (artistName == null || artistName.isEmpty()) {
            this.artistName = "Unknown artist";
        }
        this.artistName = artistName;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getTrackName() {
        return trackName;
    }

    public long getTrackID() {
        return trackID;
    }

    public long getTrackDuration() {
        return trackDuration;
    }
    public String getStringTrackDuration() {
        // TODO
        return String.valueOf(trackDuration);
    }
}
