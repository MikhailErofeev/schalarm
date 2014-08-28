package activities.elements;

import android.app.Activity;
import android.widget.ListView;
import com.github.mikhailerofeev.scholarm.api.entities.QuestionTheme;

import java.util.HashSet;
import java.util.List;

/**
 * Created by FFX20413 on 23.08.2014.
 */
public class ThemeListView extends ListView {

    private TagSelectedListener tagSelectedListener;

    public ThemeListView(Activity context, TagSelectedListener listener, HashSet<String> selectedTagsFromScheduleCreator, List<QuestionTheme> topTheme) {
        super(context);
        this.tagSelectedListener = listener;
        setAdapter(new ThemeSelectorAdapter(context,selectedTagsFromScheduleCreator, topTheme, tagSelectedListener));
    }
}
