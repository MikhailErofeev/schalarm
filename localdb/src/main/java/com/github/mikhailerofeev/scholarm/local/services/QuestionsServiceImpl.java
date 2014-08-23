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
        QuestionTheme CPLUSPLUS = new QuestionThemeImpl("C++", null);
        QuestionTheme LISP = new QuestionThemeImpl("Lisp", null);
        QuestionTheme ASM = new QuestionThemeImpl("ASM", null);
        QuestionTheme ADA = new QuestionThemeImpl("ADA", null);
        QuestionTheme CSHURP = new QuestionThemeImpl("C#", null);
        QuestionTheme C = new QuestionThemeImpl("C", null);
        QuestionTheme Groovy = new QuestionThemeImpl("Groovy", null);
        QuestionTheme PHP = new QuestionThemeImpl("PHP", null);
        QuestionTheme PYTHON = new QuestionThemeImpl("PYTHON", null);
        QuestionTheme GO = new QuestionThemeImpl("GO", null);

        top.add(new QuestionThemeImpl("Programming", Arrays.asList(
                java, sql, C, CPLUSPLUS, CSHURP, ADA, ASM, GO, Groovy, PHP, PYTHON, LISP)));


        QuestionTheme optics = new QuestionThemeImpl("optics", null);
        QuestionTheme thermodynamics = new QuestionThemeImpl("thermodynamics", null);

        top.add(new QuestionThemeImpl("Physics", Arrays.asList(optics, thermodynamics)));

        QuestionTheme German = new QuestionThemeImpl("German", null);
        QuestionTheme Russian = new QuestionThemeImpl("Russian", null);
        QuestionTheme English = new QuestionThemeImpl("English", null);
        QuestionTheme Japan = new QuestionThemeImpl("Japan", null);
        QuestionTheme Korean = new QuestionThemeImpl("Korean", null);
        QuestionTheme French = new QuestionThemeImpl("French", null);

        top.add(new QuestionThemeImpl("Speaks", Arrays.asList(
                German, Russian, English, Japan, Korean, French)));

        QuestionTheme Maya = new QuestionThemeImpl("Maya", null);
        QuestionTheme Joseph = new QuestionThemeImpl("Joseph Stalin", null);
        QuestionTheme Hurricane = new QuestionThemeImpl("Hurricane Katrina", null);
        QuestionTheme Italy = new QuestionThemeImpl("Italy", null);
        QuestionTheme London = new QuestionThemeImpl("London", null);
        QuestionTheme Cowboys = new QuestionThemeImpl("Cowboys", null);

        top.add(new QuestionThemeImpl("History", Arrays.asList(
                Maya, Joseph, Hurricane, Italy, London, Cowboys)));
        return top;
    }
}
