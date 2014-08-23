package com.example.schalarm_android_app.main_settings.widgets;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.schalarm_android_app.utils.entitys.MusicSelectorAdapter;
import com.example.schalarm_android_app.utils.entitys.MusicTrack;

import java.util.ArrayList;

public class MusicSelectorListView extends ListView {

    private MediaPlayer mediaPlayer;
    private ArrayList<MusicTrack> musicTracks = new ArrayList<>();

    public MusicSelectorListView(Activity context, ArrayList<MusicTrack> musicTracks) {
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
