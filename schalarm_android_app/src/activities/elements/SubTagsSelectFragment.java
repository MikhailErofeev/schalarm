package activities.elements;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import com.example.schalarm_android_app.R;
import com.github.mikhailerofeev.scholarm.api.entities.QuestionTheme;

import java.util.*;

/**
 * Created by FFX20413 on 23.08.2014.
 */

public class SubTagsSelectFragment extends DialogFragment {

    private String[] subTagsArray;
    private boolean[] checkedSubTagsStateArray;
    private HashSet<String> selectedTagsFromScheduleCreator;

    private final Set<String> result;
    private final DialogInterface.OnClickListener positiveListener;
    private final DialogInterface.OnMultiChoiceClickListener multiChoiceListener;

    public SubTagsSelectFragment(HashSet<String> selectedTagsFromScheduleCreator, List<QuestionTheme> children) {
        this.selectedTagsFromScheduleCreator = selectedTagsFromScheduleCreator;
        this.result = new HashSet<>(selectedTagsFromScheduleCreator);
        createSubTags(children);
        createCheckedStateArray(children);
        positiveListener = createPositiveListener();
        multiChoiceListener = createMultiChoiceListener();
    }

    private void createCheckedStateArray(List<QuestionTheme> children) {
        checkedSubTagsStateArray = new boolean[children.size()];
        for (int i = 0; i < checkedSubTagsStateArray.length; i++) {
            if (selectedTagsFromScheduleCreator.contains(children.get(i).getName())) {
                checkedSubTagsStateArray[i] = true;
            }
        }
    }

    private void createSubTags(List<QuestionTheme> children) {
        subTagsArray = new String[children.size()];
        for (int i = 0; i < children.size(); i++) {
            subTagsArray[i] = children.get(i).getName();
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.sub_tags_select_title)
                .setMultiChoiceItems(subTagsArray, checkedSubTagsStateArray, multiChoiceListener)
                .setPositiveButton(R.string.ok_button, positiveListener);
        return builder.create();
    }

    private DialogInterface.OnMultiChoiceClickListener createMultiChoiceListener() {
        return new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) result.add(subTagsArray[which]);
                else result.remove(subTagsArray[which]);
                checkedSubTagsStateArray[which] = isChecked;
            }
        };
    }

    private DialogInterface.OnClickListener createPositiveListener() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((TagSelectedListener) getActivity()).tagsSelected(result);
            }
        };
    }
}
