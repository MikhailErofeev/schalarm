package application.activities.elements;

import application.activities.main_tab_fragments.faces.TagsChangeListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import application.activities.callbacks.TagSelectActivityCallBack;
import application.utils.InjectorApplication;
import com.github.mikhailerofeev.scholarm.api.services.QuestionsService;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by FFX20413 on 23.08.2014.
 */

public class TagsSelectorActivity extends Activity implements TagsChangeListener {

    public final static int GET_SELECTED_TAG_REQUEST_CODE = 0x25;
    public static final String SELECTED_TAGS_INTENT_KEY = "selected_tags_intent_key";

    private HashSet<String> currentSelectedTags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        startActionMode(new TagSelectActivityCallBack(this));
        createCurrentSelectedTags();
        QuestionsService questionsService = InjectorApplication.get(QuestionsService.class);
        LinearLayout mainLay = new LinearLayout(this);
        mainLay.addView(new ThemeListView(this, this, currentSelectedTags, questionsService.getTopLevelThemes()));
        setContentView(mainLay);
        super.onCreate(savedInstanceState);
    }

    private void createCurrentSelectedTags() {
        Intent intent = getIntent();
        if (intent != null)
            currentSelectedTags = new HashSet<>((HashSet<String>) intent.getSerializableExtra(SELECTED_TAGS_INTENT_KEY));
        else
            currentSelectedTags = new HashSet<>();
    }

    @Override
    public void tagUnselected(String tag) {
        currentSelectedTags.remove(tag);
    }

    @Override
    public void tagsSelected(Set<String> tags) {
        currentSelectedTags.addAll(tags);
    }

    @Override
    public void tagSelected(String tag) {
        currentSelectedTags.add(tag);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(TagsSelectorActivity.class.getCanonicalName(), currentSelectedTags);
        setResult(GET_SELECTED_TAG_REQUEST_CODE, intent);
        super.onBackPressed();
    }
}
