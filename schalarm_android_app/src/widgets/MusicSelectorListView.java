package widgets;

import android.app.Activity;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import utils.entitys.MusicSelectorAdapter;
import utils.entitys.MusicTrack;

import java.util.ArrayList;
import java.util.List;

public class MusicSelectorListView extends ListView {

    private MediaPlayer mediaPlayer;
    private List<MusicTrack> musicTracks = new ArrayList<>();

    public MusicSelectorListView(Activity context, List<MusicTrack> musicTracks) {
        super(context);
        super.setOnItemClickListener(onTrackClickListener);
        super.setAdapter(new MusicSelectorAdapter(musicTracks, context));
        this.musicTracks = musicTracks;
        mediaPlayer = new MediaPlayer();
    }

    private OnItemClickListener onTrackClickListener = new OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int index, long id) {
            try {
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.setDataSource(musicTracks.get(index).getFilePath());
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } else
                    mediaPlayer.reset();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
}
