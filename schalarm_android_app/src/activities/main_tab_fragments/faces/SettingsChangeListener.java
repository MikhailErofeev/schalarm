package activities.main_tab_fragments.faces;

import org.joda.time.DateTime;
import utils.entitys.MusicTrack;

/**
 * Created by FFX20413 on 28.08.2014.
 */
public interface SettingsChangeListener {

    void timeHasChanged(DateTime time, int hours, int minutes);

    void trackHasSelected(MusicTrack musicTrack);

    DateTime getCurrentSettedTime();
}
