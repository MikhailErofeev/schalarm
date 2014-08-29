package application.utils.entitys;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import application.activities.elements.TrackSelectorElement;

import java.util.List;

public class MusicSelectorAdapter extends BaseAdapter {

    private List<MusicTrack> musicTracks;
    private Activity parent;

    public MusicSelectorAdapter(List<MusicTrack> musicTracks, Activity parent) {
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
