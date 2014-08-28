package activities.elements;

import android.app.Activity;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import utils.entitys.MusicTrack;

/**
 * Created by FFX20413 on 23.08.2014.
 */
public class TrackSelectorElement extends LinearLayout {

    private TextView trackName;
    private TextView artistName;
    private RadioButton radioButton;
    private MusicTrack musicTrack;
    private Activity parentContext;
    private LinearLayout radioButtonContainer;

    public TrackSelectorElement(Activity context, MusicTrack musicTrack) {
        super(context);
        this.parentContext = context;
        this.musicTrack = musicTrack;

        setOrientation(VERTICAL);
        initElements();
        setDataToElements();
    }

    private void setDataToElements() {
        artistName.setText(musicTrack.getArtistName());
        trackName.setText(musicTrack.getTrackName());

        addView(trackName);
        addView(artistName);
        addView(radioButtonContainer);
    }

    private void initElements() {
        radioButtonContainer = new LinearLayout(parentContext);
        radioButtonContainer.setGravity(Gravity.RIGHT);
        radioButtonContainer.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        trackName = new TextView(parentContext);
        artistName = new TextView(parentContext);
        radioButton = new RadioButton(parentContext);

        radioButtonContainer.addView(radioButton);
    }

    public void setRadioButton(boolean state) {
        radioButton.setSelected(state);
    }
}
