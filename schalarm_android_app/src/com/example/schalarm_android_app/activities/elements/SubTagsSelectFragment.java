package com.example.schalarm_android_app.activities.elements;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import com.github.mikhailerofeev.scholarm.api.entities.QuestionTheme;

import java.util.Arrays;
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
    private HashSet<String> selectedTagsFromScheduleCreator;
    private static final String SELECT_SUB_TAGS = "Select sub tags";


    public SubTagsSelectFragment() {

    }

    public SubTagsSelectFragment(HashSet<String> selectedTagsFromScheduleCreator, List<QuestionTheme> children) {
        subTags = new String[children.size()];
        for (int i = 0; i < children.size(); i++) {
            QuestionTheme q = children.get(i);
            subTags[i] = q.getName();
        }
        selects = new boolean[children.size()];
        result = new HashSet<>();
        System.out.println(selectedTagsFromScheduleCreator);
        for (int i = 0; i < subTags.length; i++) {
            if (selectedTagsFromScheduleCreator.contains(subTags[i])) {
                System.out.println(subTags[i]);
                selects[i] = true;
            }
        }
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
                if (result != null && !result.isEmpty()) {
                    for (String tags : result) {
                        ((TagSelectedListener) getActivity()).tagSelected(tags);
                    }
                } else if (selectedTagsFromScheduleCreator != null) {
                    for (String subTag : subTags) {
                        if (selectedTagsFromScheduleCreator.contains(subTag)) {
                            result.add(subTag);
                        }
                    }
                    for (String tags : result) {
                        ((TagSelectedListener) getActivity()).tagSelected(tags);
                    }
                }
            }
        }).setNegativeButton(CANCEL, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO
            }
        });
        return builder.create();
    }
}
