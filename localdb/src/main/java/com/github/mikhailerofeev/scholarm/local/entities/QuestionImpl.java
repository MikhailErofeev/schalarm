package com.github.mikhailerofeev.scholarm.local.entities;

import com.github.mikhailerofeev.scholarm.api.entities.Question;

import java.util.Map;

/**
 * @author m-erofeev
 * @since 22.08.14
 */
public class QuestionImpl implements Question {
  private long id;
  private final Map<Key, String> variants;
  private final Type type;
  private final String questionText;
  private final Question.Key rightAnswer;
  private final String themeName;

  public QuestionImpl(long id, Map<Key, String> variants, Type type, String questionText, Key rightAnswer, String themeName) {
    this.id = id;
    this.variants = variants;
    this.type = type;
    this.questionText = questionText;
    this.rightAnswer = rightAnswer;
    this.themeName = themeName;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  @Override
  public Type getType() {
    return type;
  }

  @Override
  public String getQuestionText() {
    return questionText;
  }

  @Override
  public Map<Key, String> getVariants() {
    return variants;
  }

  @Override
  public Key getRightAnswer() {
    return rightAnswer;
  }

  @Override
  public String getThemeName() {
    return themeName;
  }
}
