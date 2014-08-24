package com.github.mikhailerofeev.scholarm.local.services;

import android.app.Application;
import android.content.Context;
import com.github.mikhailerofeev.scholarm.api.entities.Question;
import com.github.mikhailerofeev.scholarm.local.entities.QuestionImpl;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.io.Files;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@RunWith(JUnit4.class)
public class QuestionsManagerTest {

    @Test
    public void testSerialization() {
        File tmpDir = Files.createTempDir();
        tmpDir.deleteOnExit();

        Application mockApplication = mock(Application.class);
        Context context = mock(Context.class);
        doReturn(context).when(mockApplication).getApplicationContext();
        doReturn(tmpDir).when(context).getFilesDir();

        QuestionsManager questionsManager = new QuestionsManager(mockApplication);
        questionsManager.init();
        List<Question> emptyList = questionsManager.getQuestions(Sets.newHashSet("programming"));
        assertTrue(emptyList.isEmpty());

        List<QuestionImpl> questions = Lists.newArrayList();
        ImmutableMap<Character, String> qa = ImmutableMap.<Character, String>builder().
                put('A', "da").
                put('B', "ne").
                put('C', "muu").
                put('D', "yarh").build();
        questions.add(new QuestionImpl(-1L, qa, Question.Type.TEXT, "skaji da", Sets.newHashSet('A'), "programming"));
        questions.add(new QuestionImpl(-1L, qa, Question.Type.TEXT, "skaji muu", Sets.newHashSet('C'), "programming"));
        questions.add(new QuestionImpl(-1L, qa, Question.Type.TEXT, "skaji yarh", Sets.newHashSet('D'), "physics"));

        questionsManager.addQuestions(questions);
        List<Question> programmingQuestions = questionsManager.getQuestions(Sets.newHashSet("programming"));
        assertEquals(2, programmingQuestions.size());
        List<Question> physics = questionsManager.getQuestions(Sets.newHashSet("physics"));
        assertEquals(1, physics.size());

        List<QuestionImpl> questions2 = Lists.newArrayList();
        questions2.add(new QuestionImpl(-1L, qa, Question.Type.TEXT, "dadadada", Sets.newHashSet('A'), "programming"));
        questionsManager.addQuestions(questions2);
        programmingQuestions = questionsManager.getQuestions(Sets.newHashSet("programming"));
        assertEquals(3, programmingQuestions.size());
    }

    @Test
    public void buildJavaQuiz() throws IOException {
        File tmpDir = Files.createTempDir();
        tmpDir.deleteOnExit();

        Application mockApplication = mock(Application.class);
        Context context = mock(Context.class);
        doReturn(context).when(mockApplication).getApplicationContext();
        doReturn(tmpDir).when(context).getFilesDir();

        QuestionsManager questionsManager = new QuestionsManager(mockApplication);
        questionsManager.init();
        List<QuestionImpl> questions = GenerateJavaQuestions.getQuestions();
        questionsManager.addQuestions(questions);
        questionsManager.getQuestions(Sets.newHashSet("java"));
    }
}