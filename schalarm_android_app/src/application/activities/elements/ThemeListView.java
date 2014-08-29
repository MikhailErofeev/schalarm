package application.activities.elements;

import application.activities.main_tab_fragments.faces.TagsChangeListener;
import android.app.Activity;
import android.widget.ListView;
import com.github.mikhailerofeev.scholarm.api.entities.QuestionTheme;

import java.util.HashSet;
import java.util.List;

/**
 * Created by FFX20413 on 23.08.2014.
 */
public class ThemeListView extends ListView {

    private TagsChangeListener tagsChangeListener;

    public ThemeListView(Activity context, TagsChangeListener listener, HashSet<String> selectedTagsFromScheduleCreator, List<QuestionTheme> topTheme) {
        super(context);
        this.tagsChangeListener = listener;
        setAdapter(new ThemeSelectorAdapter(context,selectedTagsFromScheduleCreator, topTheme, tagsChangeListener));
    }
}
