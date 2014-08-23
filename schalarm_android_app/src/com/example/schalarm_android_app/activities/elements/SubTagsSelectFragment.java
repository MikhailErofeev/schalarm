package com.example.schalarm_android_app.activities.elements;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import com.github.mikhailerofeev.scholarm.api.entities.QuestionTheme;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by FFX20413 on 23.08.2014.
 */
public class SubTagsSelectFragment extends DialogFragment {


    public static final String OK = "Ok";
    public static final String CANCEL = "Cancel";
    private String[] subTags;
    private boolean[] selects;
    private Set<String> result;

    private static final String SELECT_SUB_TAGS = "Select sub tags";


    public SubTagsSelectFragment() {

    }

    public SubTagsSelectFragment(List<QuestionTheme> children) {
        subTags = children.toArray(new String[children.size()]);
        selects = new boolean[children.size()];
        result = new HashSet<>();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(SELECT_SUB_TAGS).setMultiChoiceItems(subTags, selects, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    result.add(subTags[which]);
                } else {
                    result.remove(subTags[which]);
                }
            }
        }).setPositiveButton(OK, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (String tags : result) {
                    ((TagSelectedListener) getActivity()).tagSelected(tags);
                }
            }
        }).setNegativeButton(CANCEL, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO
            }
        });
        return super.onCreateDialog(savedInstanceState);
    }
}
