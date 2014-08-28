package activities.callbacks;

import android.app.Activity;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by FFX20413 on 24.08.2014.
 */
public class TagSelectActivityCallBack implements ActionMode.Callback {
    private Activity parent;

    public TagSelectActivityCallBack(Activity parent) {
        this.parent = parent;
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        parent.onBackPressed();
    }
}
