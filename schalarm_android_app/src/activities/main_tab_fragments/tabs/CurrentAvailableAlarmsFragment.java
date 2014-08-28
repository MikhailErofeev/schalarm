package activities.main_tab_fragments.tabs;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

/**
 * Created by FFX20413 on 27.08.2014.
 */
public class CurrentAvailableAlarmsFragment extends Fragment {

    private Activity parent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.parent = getActivity();

        LinearLayout linearLayout = new LinearLayout(parent);
        linearLayout.addView(new CheckBox(parent));

        return linearLayout;
    }
}
