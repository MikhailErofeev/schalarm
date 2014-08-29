package application.activities.elements;

import application.activities.main_tab_fragments.faces.TagsChangeListener;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.*;
import com.github.mikhailerofeev.scholarm.api.entities.QuestionTheme;

import java.util.HashSet;
import java.util.List;

/**
 * Created by FFX20413 on 23.08.2014.
 */

public class ThemeSelectorAdapter extends BaseAdapter {

    public static final String SUB_TAGS_FRAGMENT_KEY = "sub_tags_fragment_key";
    private List<QuestionTheme> topThemes;
    private Activity parent;
    private TagsChangeListener tagsChangeListener;
    private HashSet<String> selectedTagsFromScheduleCreator;

    public ThemeSelectorAdapter(Activity parent, HashSet<String> selectedTagsFromScheduleCreator, List<QuestionTheme> topThemes, TagsChangeListener tagsChangeListener) {
        this.topThemes = topThemes;
        this.parent = parent;
        this.tagsChangeListener = tagsChangeListener;
        this.selectedTagsFromScheduleCreator = selectedTagsFromScheduleCreator;
    }

    @Override
    public int getCount() {
        return topThemes.size();
    }

    @Override
    public Object getItem(int i) {
        return topThemes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup group) {
        if (view == null) {
            view = new SelectedCheckBoxElement(parent, topThemes.get(i).getName());
            final SelectedCheckBoxElement selectedCheckBoxElement = (SelectedCheckBoxElement) view;
            setMainThemeListener(selectedCheckBoxElement);
            setCheckBocksForMainThemeListener(selectedCheckBoxElement);
            if (selectedTagsFromScheduleCreator != null && selectedTagsFromScheduleCreator.contains(selectedCheckBoxElement.getTagName())) {
                selectedCheckBoxElement.getCheckBox().setChecked(true);
            }
            view.setLayoutParams(new AbsListView.LayoutParams(parent.getResources().getDisplayMetrics().widthPixels,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
        }
        return view;
    }

    private void setCheckBocksForMainThemeListener(final SelectedCheckBoxElement selectedCheckBoxElement) {
        selectedCheckBoxElement.getCheckBox().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (QuestionTheme topTheme : topThemes) {
                    if (topTheme.getName().equals(selectedCheckBoxElement.getTagName())) {
                        for (QuestionTheme questionTheme : topTheme.getChildren()) {
                            if (selectedCheckBoxElement.isChecked()) {
                                tagsChangeListener.tagSelected(selectedCheckBoxElement.getTagName());
                                tagsChangeListener.tagSelected(questionTheme.getName());
                                selectedTagsFromScheduleCreator.add(questionTheme.getName());
                                selectedTagsFromScheduleCreator.add(selectedCheckBoxElement.getTagName());
                            } else {
                                tagsChangeListener.tagUnselected(questionTheme.getName());
                                tagsChangeListener.tagUnselected(questionTheme.getName());
                                selectedTagsFromScheduleCreator.remove(questionTheme.getName());
                                selectedTagsFromScheduleCreator.remove(selectedCheckBoxElement.getTagName());
                            }
                        }
                    }
                }
            }
        });
    }

    private void setMainThemeListener(final SelectedCheckBoxElement selectedCheckBoxElement) {
        selectedCheckBoxElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (QuestionTheme topTheme : topThemes) {
                    if (topTheme.getName().equals(selectedCheckBoxElement.getTagName())) {
                        parent.getFragmentManager()
                                .beginTransaction()
                                .add(new SubTagsSelectFragment(selectedTagsFromScheduleCreator, topTheme.getChildren()), SUB_TAGS_FRAGMENT_KEY)
                                .commit();
                    }
                }
            }
        });
    }
}
