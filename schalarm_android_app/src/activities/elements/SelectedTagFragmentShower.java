package activities.elements;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.schalarm_android_app.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

/**
 * Created by FFX20413 on 24.08.2014.
 */
public class SelectedTagFragmentShower extends DialogFragment {

    public static final String CURRENT_TAGS = "Current Tags";
    public static final String NO_TAGS_SELECTED = "NO Tags Selected";
    private Set<String> selectedTag;

    public SelectedTagFragmentShower(Set<String> selectedTag) {
        this.selectedTag = selectedTag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (selectedTag != null && !selectedTag.isEmpty()) {
            builder.setTitle(CURRENT_TAGS).setAdapter(new ShortTagsAdapter(getActivity(), selectedTag), null).setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
        } else {
            builder.setTitle(NO_TAGS_SELECTED).setPositiveButton(R.string.ok_button, null);
        }
        return builder.create();
    }

    private static class ShortTagsAdapter extends BaseAdapter {
        private ArrayList<String> elements;
        private Activity parent;

        private ShortTagsAdapter(Activity parent, Collection<String> elements) {
            this.elements = new ArrayList<>(elements);
            this.parent = parent;
        }

        @Override
        public int getCount() {
            return elements.size();
        }

        @Override
        public Object getItem(int position) {
            return elements.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup group) {
            if (convertView == null) {
                TextView textView = new TextView(parent);
                textView.setText("# " + elements.get(position));
                convertView = textView;
            }
            return convertView;
        }
    }
}
