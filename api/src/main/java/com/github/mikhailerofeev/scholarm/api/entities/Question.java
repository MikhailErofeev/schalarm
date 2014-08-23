package com.github.mikhailerofeev.scholarm.api.entities;

import java.util.Map;
import java.util.Set;

/**
 * @author m-erofeev
 * @since 22.08.14
 */
public interface Question {
    Type getType();

    String getQuestionText();

    Map<Character, String> getVariants();

    //can be one
    Set<Character> getRightAnswers();

    String getThemeName();

    enum Type {
        TEXT,
        IMAGE
    }
}
