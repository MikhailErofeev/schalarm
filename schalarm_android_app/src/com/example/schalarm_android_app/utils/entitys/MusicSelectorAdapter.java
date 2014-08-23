package com.example.schalarm_android_app.utils.entitys;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.schalarm_android_app.R;
import com.example.schalarm_android_app.activities.elements.TrackSelectorElement;
import com.example.schalarm_android_app.utils.entitys.MusicTrack;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class MusicSelectorAdapter extends BaseAdapter {

    private ArrayList<MusicTrack> musicTracks;
    private Activity parent;

    public MusicSelectorAdapter(ArrayList<MusicTrack> musicTracks, Activity parent) {
        this.musicTracks = musicTracks;
        this.parent = parent;
    }

    @Override
    public int getCount() {
        return musicTracks.size();
    }

    @Override
    public Object getItem(int position) {
        return musicTracks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return musicTracks.get(position).getTrackID();
    }

    @Override
    public View getView(int position, View element, ViewGroup group) {
        if (element == null) {
            element = new TrackSelectorElement(parent, musicTracks.get(position));
        }
        return element;
    }
}
