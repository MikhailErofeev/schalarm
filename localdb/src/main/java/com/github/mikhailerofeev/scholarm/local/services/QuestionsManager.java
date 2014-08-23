package com.github.mikhailerofeev.scholarm.local.services;

import android.app.Application;
import com.github.mikhailerofeev.scholarm.api.entities.Question;
import com.github.mikhailerofeev.scholarm.local.entities.QuestionImpl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author m-erofeev
 * @since 22.08.14
 */
public class QuestionsManager {

    private final Application application;

    private List<QuestionImpl> questions;
    private Long maxId;

    @Inject
    public QuestionsManager(Application application) {
        this.application = application;
    }

    public List<Question> getQuestions(Set<String> questionThemesAndSubthemesNames) {
        List<Question> ret = new ArrayList<>();
        for (QuestionImpl question : questions) {
            if (questionThemesAndSubthemesNames.contains(question.getThemeName())) {
                ret.add(question);
            }
        }
        return ret;
    }

    @Inject
    public synchronized void init() {
        System.out.println("init QuestionsManager");
        System.out.println("our dir: " + application.getApplicationContext().getFilesDir());
        maxId = 0L;
        File dataFile = getDataFile();
        if (!dataFile.exists()) {
            System.out.println(dataFile.getAbsolutePath() + " not exists");
            if (!tryToLoadAssets()) {
                questions = new ArrayList<>();
            }
            return;
        }
        FileReader fileReader;
        try {
            fileReader = new FileReader(dataFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        readQuestions(fileReader);
    }

    private void readQuestions(Reader fileReader) {
        Type type = new TypeToken<List<QuestionImpl>>() {
        }.getType();
        questions = new Gson().fromJson(fileReader, type);
        for (QuestionImpl question : questions) {
            if (maxId < question.getId()) {
                maxId = question.getId();
            }
        }
    }

    private boolean tryToLoadAssets() {
        if (application.getAssets() == null) {
            return false;
        }
        try {
            Reader inputStreamReader = new InputStreamReader(application.getAssets().open("database.json"));
            readQuestions(inputStreamReader);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public synchronized void addQuestions(List<QuestionImpl> questionsToAdd) {
        for (QuestionImpl question : questionsToAdd) {
            question.setId(maxId++);
            this.questions.add(question);
        }
        File dataFile = getDataFile();
        dataFile.delete();
        try {
            if (!dataFile.createNewFile()) {
                throw new RuntimeException("can't create file");
            }
            String questionsStr = new Gson().toJson(this.questions);
            FileWriter writer = new FileWriter(dataFile);
            writer.append(questionsStr);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private File getDataFile() {
        File filesDir = application.getApplicationContext().getFilesDir();
        return new File(filesDir.getAbsolutePath() + "database.json");
    }
}
