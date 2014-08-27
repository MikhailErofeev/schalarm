package com.example.schalarm_android_app.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import com.example.schalarm_android_app.main_settings.widgets.MusicSelectorListView;
import com.example.schalarm_android_app.utils.MusicFinder;
import com.example.schalarm_android_app.utils.entitys.MusicTrack;

import java.util.ArrayList;

/**
 * Created by FFX20413 on 23.08.2014.
 */
public class SelectTrackActivity extends Activity {

    private ArrayList<MusicTrack> allAvailableTracks;
    private LinearLayout mainLay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainLay = new LinearLayout(this);
        allAvailableTracks = new ArrayList<>(MusicFinder.getAllAvailableMusicTracks(this));
        mainLay.addView(new MusicSelectorListView(this, allAvailableTracks));
        setContentView(mainLay);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(MusicTrack.class.getCanonicalName(), allAvailableTracks);
        super.onSaveInstanceState(outState);
    }

}
