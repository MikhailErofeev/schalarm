package com.github.mikhailerofeev.scholarm.local.services;

import com.github.mikhailerofeev.scholarm.api.entities.Question;
import com.github.mikhailerofeev.scholarm.local.entities.QuestionImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * @author m-erofeev
 * @since 24.08.14
 */
public class GenerateJavaQuestions {
    private final static String innerBlock = "==========================";
    private final static String questionBlock = "###########################";

    public static void main(String[] args) throws IOException {
        List<QuestionImpl> questionList = getQuestions();
        questionList.size();

    }

    public static List<QuestionImpl> getQuestions() throws IOException {
        File srcPath = new File("/Users/m-erofeev/projects/schalarm/localdb/src/main/resources/javaQuizze.txt");
        BufferedReader reader = new BufferedReader(new FileReader(srcPath));
        String question = "";
        Map<Character, String> key2answ = new HashMap<>();
        Set<Character> rightAnswsers = new HashSet<>();
        String s;
        State state = State.question;
        List<QuestionImpl> questionList = new ArrayList<>();
        while ((s = reader.readLine()) != null) {
            if (s.isEmpty()) {
                continue;
            }
            if (s.equals(questionBlock)) {
                state = State.question;
                questionList.add(buildQuestion(question, key2answ, rightAnswsers));
                System.out.println(question);
                key2answ.clear();
                rightAnswsers.clear();
                question = "";
                continue;
            } else if (s.equals(innerBlock)) {
                if (state == State.question) {
                    state = State.varians;
                } else if (state == State.varians) {
                    state = State.answers;
                }
                continue;
            }
            if (state == State.question) {
                question += "\n" + s;
            } else if (state == State.varians) {
                char key = s.charAt(0);
                String answer = s.substring(3);
                key2answ.put(key, answer);
            } else if (state == State.answers) {
                char key = s.charAt(0);
                rightAnswsers.add(key);
            }
        }
        return questionList;
    }

    private static QuestionImpl buildQuestion(String question, Map<Character, String> key2answ, Set<Character> rightAnswsers) {
        if (question.startsWith("\n")) {
            question = question.substring(1);
        }
        QuestionImpl ret = new QuestionImpl(-1, new HashMap<>(key2answ), Question.Type.TEXT, question, new HashSet<>(rightAnswsers), "java");
        return ret;
    }

    static enum State {
        question,
        varians,
        answers
    }
}
