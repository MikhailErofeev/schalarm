package com.github.mikhailerofeev.scholarm.local.services;

import com.github.mikhailerofeev.scholarm.api.entities.Question;
import com.github.mikhailerofeev.scholarm.api.entities.QuestionTheme;
import com.github.mikhailerofeev.scholarm.api.services.QuestionsService;
import com.github.mikhailerofeev.scholarm.local.entities.QuestionThemeImpl;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author m-erofeev
 * @since 22.08.14
 */
public class QuestionsServiceImpl implements QuestionsService {

    private final QuestionsManager questionsManager;

    @Inject
    public QuestionsServiceImpl(QuestionsManager questionsManager) {
        this.questionsManager = questionsManager;
    }

    @Override
    public List<Question> getQuestions(Set<String> themesNames) {
        Set<String> themesAndSubthemes = new HashSet<>();
        List<QuestionTheme> themes = getTopLevelThemes();
        addSubThemesReqursive(themesAndSubthemes, themesNames, themes);
        return questionsManager.getQuestions(themesAndSubthemes);
    }

    private void addSubThemesReqursive(Set<String> themesAndSubthemes, Set<String> themesNames, List<QuestionTheme> themes) {
        for (QuestionTheme questionTheme : themes) {
            if (themesNames.contains(questionTheme.getName())) {
                themesAndSubthemes.add(questionTheme.getName());
                addSubThemesReqursive(themesAndSubthemes, themesNames, questionTheme.getChildren());
            }
        }
    }

    @Override
    public List<QuestionTheme> getTopLevelThemes() {
        List<QuestionTheme> top = new ArrayList<>();
        QuestionTheme java = new QuestionThemeImpl("java", null);
        QuestionTheme sql = new QuestionThemeImpl("sql", null);
        top.add(new QuestionThemeImpl("programming", Arrays.asList(java, sql)));
        QuestionTheme optics = new QuestionThemeImpl("optics", null);
        QuestionTheme thermodynamics = new QuestionThemeImpl("thermodynamics", null);
        top.add(new QuestionThemeImpl("physics", Arrays.asList(optics, thermodynamics)));
        return top;
    }
}
