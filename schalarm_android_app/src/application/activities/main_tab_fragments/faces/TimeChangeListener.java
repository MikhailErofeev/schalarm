package application.activities.main_tab_fragments.faces;

import org.joda.time.DateTime;

/**
 * Created by FFX20413 on 29.08.2014.
 */
public interface TimeChangeListener {

    void timeHasChanged(DateTime time, int hours, int minutes);

    DateTime getCurrentSettedTime();
}
