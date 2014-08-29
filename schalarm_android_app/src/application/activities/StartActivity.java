package application.activities;

import application.activities.main_tab_fragments.FragmentSectionAdapter;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import com.example.schalarm_android_app.R;

public class StartActivity extends Activity implements ActionBar.TabListener {

    private ViewPager pageContainer;
    private FragmentSectionAdapter fragmentSectionAdapter;
    private ActionBar actionBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
        initElements();

        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        pageContainer.setAdapter(fragmentSectionAdapter);
        pageContainer.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });
        for (int i = 0; i < fragmentSectionAdapter.getCount(); i++) {
            actionBar.addTab(actionBar.newTab()
                    .setText(fragmentSectionAdapter.getPageTitle(i)).setTabListener(this));
        }
    }

    private void initElements() {
        pageContainer = (ViewPager) findViewById(R.id.start_activity_view_pager);
        fragmentSectionAdapter = new FragmentSectionAdapter(this, getFragmentManager());
        actionBar = getActionBar();
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        pageContainer.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }


}
