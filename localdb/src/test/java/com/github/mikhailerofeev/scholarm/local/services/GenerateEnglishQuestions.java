package com.github.mikhailerofeev.scholarm.local.services;

import com.github.mikhailerofeev.scholarm.api.entities.Question;
import com.github.mikhailerofeev.scholarm.local.entities.QuestionImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author m-erofeev
 * @since 24.08.14
 */
public class GenerateEnglishQuestions {

    public static void main(String[] args) throws IOException {
        getQuestions();
    }

    public static List<QuestionImpl> getQuestions() throws IOException {
        List<String> russian = FileUtils.readLines(new File("/Users/m-erofeev/projects/schalarm/localdb/src/main/resources/russian.txt"));
        List<String> english = FileUtils.readLines(new File("/Users/m-erofeev/projects/schalarm/localdb/src/main/resources/english.txt"));
        List<QuestionImpl> ret = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < english.size(); i++) {
            String word = english.get(i);
            Map<Character, String> key2Answer = new HashMap<>();
            List<Character> keys = Lists.newArrayList('a', 'b', 'c', 'd');
            String rightAnswer = russian.get(i);
            Character rightKey = keys.remove(random.nextInt(4));
            key2Answer.put(rightKey, rightAnswer);
            Set<Character> rightKeys = Sets.newHashSet(rightKey);
            for (int j = 0; j < 3; j++) {
                int rand = random.nextInt(russian.size());
                if (rand == i) {
                    j--;
                } else {
                    char key = keys.remove(random.nextInt(3 - j));
                    String wrongAnswer = russian.get(rand);
                    key2Answer.put(key, wrongAnswer);
                }
            }
            String questionText = "Correct translate for \"" + word + "\"";
            QuestionImpl question = new QuestionImpl(-1, key2Answer, Question.Type.TEXT, questionText, rightKeys, "English");
            ret.add(question);
        }
        return ret;
    }
}
