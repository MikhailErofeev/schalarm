package com.github.mikhailerofeev.scholarm.api.services;

import com.github.mikhailerofeev.scholarm.api.entities.Question;
import com.github.mikhailerofeev.scholarm.api.entities.QuestionTheme;

import java.util.List;
import java.util.Set;

/**
 * @author m-erofeev
 * @since 22.08.14
 */
public interface QuestionsService {

  List<Question> getQuestions(Set<String> themesNames);
  
  List<QuestionTheme> getTopLevelThemes();
}
