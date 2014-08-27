package com.github.mikhailerofeev.scholarm.local.entities;

import com.github.mikhailerofeev.scholarm.api.entities.QuestionTheme;

import java.util.List;

/**
 * @author m-erofeev
 * @since 22.08.14
 */
public class QuestionThemeImpl implements QuestionTheme {


    private final String name;
    private final List<QuestionTheme> children;

    public QuestionThemeImpl(String name, List<QuestionTheme> children) {
        this.name = name;
        this.children = children;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<QuestionTheme> getChildren() {
        return children;
    }
}
