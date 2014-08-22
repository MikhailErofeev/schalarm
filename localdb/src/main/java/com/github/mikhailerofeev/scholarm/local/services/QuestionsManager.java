package com.github.mikhailerofeev.scholarm.local.services;

import com.github.mikhailerofeev.scholarm.api.entities.Question;
import com.github.mikhailerofeev.scholarm.local.entities.QuestionImpl;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Set;

/**
 * @author m-erofeev
 * @since 22.08.14
 */
public class QuestionsManager {

  private List<QuestionImpl> theme2Question;

  public List<Question> getQuestions(Set<String> questionThemesAndSubthemesNames) {
    List<Question> ret = Lists.newArrayList();
    for (QuestionImpl question : theme2Question) {
      if (questionThemesAndSubthemesNames.contains(question.getThemeName())) {
        ret.add(question);
      }
    }
    return ret;
  }
}
