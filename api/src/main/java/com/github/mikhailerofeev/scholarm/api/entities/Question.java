package com.github.mikhailerofeev.scholarm.api.entities;

import java.util.Map;

/**
 * @author m-erofeev
 * @since 22.08.14
 */
public interface Question {
  Type getType();

  String getQuestionText();

  Map<Character, String> getVariants();

  char getRightAnswer();

  String getThemeName();

  enum Type {
    TEXT,
    IMAGE
  }
}
