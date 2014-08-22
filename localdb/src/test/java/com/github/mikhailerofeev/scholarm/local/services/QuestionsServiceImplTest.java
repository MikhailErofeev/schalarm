package com.github.mikhailerofeev.scholarm.local.services;


import com.github.mikhailerofeev.scholarm.api.services.QuestionsService;
import com.github.mikhailerofeev.scholarm.local.AndroidMockModule;
import com.github.mikhailerofeev.scholarm.local.stuff.GuiceModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class QuestionsServiceImplTest {

  @Test
  public void testGuice() {
    Injector injector = Guice.createInjector(new GuiceModule(), new AndroidMockModule());
    QuestionsService questionsService = injector.getInstance(QuestionsService.class);
    assertEquals(1, questionsService.getTopLevelThemes().size());
  }
}