package com.github.mikhailerofeev.scholarm.local.services;


import android.app.Application;
import com.github.mikhailerofeev.scholarm.api.services.QuestionsService;
import com.github.mikhailerofeev.scholarm.local.AndroidMockModule;
import com.github.mikhailerofeev.scholarm.local.stuff.LocalQuestionBaseModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class QuestionsServiceImplTest {

    @Test
    public void testGuice() throws IOException {
        Injector injector = Guice.createInjector(new LocalQuestionBaseModule(), new AndroidMockModule());
        QuestionsService questionsService = injector.getInstance(QuestionsService.class);
        Application application = injector.getInstance(Application.class);
        assertEquals(1, questionsService.getTopLevelThemes().size());
    }
}