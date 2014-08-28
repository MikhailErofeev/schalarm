package activities.main_tab_fragments;

import activities.main_tab_fragments.tabs.ApplicationSettingsFragment;
import activities.main_tab_fragments.tabs.CreateAlarmsFragment;
import activities.main_tab_fragments.tabs.CurrentAvailableAlarmsFragment;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;
import com.example.schalarm_android_app.R;

/**
 * Created by FFX20413 on 27.08.2014.
 */

public class FragmentSectionAdapter extends FragmentPagerAdapter {

    private static final int FRAGMENTS_COUNT = 3;
    private static final int ALARM_CREATE_FRAGMENT = 1;
    private static final int ALARMS_SHOWER_FRAGMENT = 0;
    private static final int APPLICATION_SETTINGS = 2;

    private Activity parent;

    public FragmentSectionAdapter(Activity parent, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.parent = parent;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case ALARMS_SHOWER_FRAGMENT:
                return new CurrentAvailableAlarmsFragment();
            case ALARM_CREATE_FRAGMENT:
                return new CreateAlarmsFragment();
            default:
                return new ApplicationSettingsFragment();
        }
    }

    @Override
    public int getCount() {
        return FRAGMENTS_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case ALARM_CREATE_FRAGMENT:
                return parent.getString(R.string.create_new_alarm_tab_title);
            case ALARMS_SHOWER_FRAGMENT:
                return parent.getString(R.string.current_alarms_tab_title);
            case APPLICATION_SETTINGS:
                return parent.getString(R.string.application_settings_tab_title);
            default:
                return "";
        }
    }
}