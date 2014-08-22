package com.github.mikhailerofeev.scholarm.api.entities;

import java.util.Map;

/**
 * @author m-erofeev
 * @since 22.08.14
 */
public interface Question {
  Type getType();

  String getQuestionText();

  Map<Key, String> getVariants();

  Key getRightAnswer();

  String getThemeName();

  enum Type {
    TEXT,
    IMAGE
  }

  enum Key {
    A, B, C, D
  }
}
