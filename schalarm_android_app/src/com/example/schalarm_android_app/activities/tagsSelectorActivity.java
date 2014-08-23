package com.example.schalarm_android_app.activities;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import com.example.schalarm_android_app.activities.elements.TagSelectedListener;
import com.example.schalarm_android_app.activities.elements.ThemeListView;
import com.github.mikhailerofeev.scholarm.api.services.QuestionsService;
import com.github.mikhailerofeev.scholarm.local.stuff.LocalQuestionBaseModule;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import java.util.HashSet;

/**
 * Created by FFX20413 on 23.08.2014.
 */
public class tagsSelectorActivity extends Activity implements TagSelectedListener {

    public final static int GET_SELECTED_TAG_REQUEST_CODE = 0x25;

    private LinearLayout mainLay;
    private HashSet<String> selectedTags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mainLay = new LinearLayout(this);
        selectedTags = new HashSet<>();

        Injector injector = Guice.createInjector(new ApplicationModule(), new LocalQuestionBaseModule());
        QuestionsService questionsService = injector.getBinding(QuestionsService.class).getProvider().get();
        mainLay.addView(new ThemeListView(this, this, questionsService.getTopLevelThemes()));
        setContentView(mainLay);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void tagUnselected(String tag) {
        selectedTags.remove(tag);
    }

    @Override
    public void tagSelected(String tag) {
        selectedTags.add(tag);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(tagsSelectorActivity.class.getCanonicalName(), selectedTags);
        setResult(GET_SELECTED_TAG_REQUEST_CODE, intent);
        finish();
        super.onBackPressed();
    }

    private class ApplicationModule extends AbstractModule {
        @Override
        protected void configure() {
            bind(Application.class).toInstance(tagsSelectorActivity.this.getApplication());
        }
    }
}
