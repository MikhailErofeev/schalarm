package com.github.mikhailerofeev.scholarm.api.entities;

import java.util.List;

/**
 * @author m-erofeev
 * @since 22.08.14
 */
public interface QuestionTheme {
  String getName();

  List<QuestionTheme> getChildren();
}
