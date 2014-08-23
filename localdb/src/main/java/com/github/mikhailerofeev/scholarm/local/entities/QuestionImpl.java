package com.github.mikhailerofeev.scholarm.local.entities;

import com.github.mikhailerofeev.scholarm.api.entities.Question;

import java.util.Map;
import java.util.Set;

/**
 * @author m-erofeev
 * @since 22.08.14
 */
public class QuestionImpl implements Question {
    private long id;
    private final Map<Character, String> variants;
    private final Type type;
    private final String questionText;
    private final Set<Character> rightAnswers;
    private final String themeName;

    public QuestionImpl(long id, Map<Character, String> variants, Type type, String questionText, Set<Character> rightAnswers, String themeName) {
        this.id = id;
        this.variants = variants;
        this.type = type;
        this.questionText = questionText;
        this.rightAnswers = rightAnswers;
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
    public Map<Character, String> getVariants() {
        return variants;
    }

    @Override
    public Set<Character> getRightAnswers() {
        return rightAnswers;
    }

    @Override
    public String getThemeName() {
        return themeName;
    }
}
