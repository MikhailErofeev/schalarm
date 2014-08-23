package com.example.schalarm_android_app.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.schalarm_android_app.R;
import com.example.schalarm_android_app.utils.InjectorApplication;
import com.github.mikhailerofeev.scholarm.api.entities.Question;
import com.github.mikhailerofeev.scholarm.api.services.QuestionsService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by FFX20413 on 23.08.2014.
 */
public class QueryShowerActivity extends Activity {
    // TODO Сюда через апи по тегу вытащить коллекцию вопросов!! и колекцию ответов с 1 правильным, типа isTrue O_o
    private static Set<String> tags = new HashSet<String>() {{
        add("programming");
    }};

    QuestionsService questionsService;


    private TextView questionText;
    private LinearLayout answers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qa);
        Question question = null;
        questionText = (TextView) findViewById(R.id.question_text);
        answers = (LinearLayout) findViewById(R.id.answers_list);
        questionsService = InjectorApplication.get(QuestionsService.class);
        List<Question> questions;
        questions = questionsService.getQuestions(tags);
        boolean canQuit = false;
        setNewQuestion(questions.get(0), canQuit);
    }

    private void setNewQuestion(Question question, boolean canQuit) {
        questionText.setText(question.getQuestionText());

        ArrayList<View> buttons = new ArrayList<>();
        Button exampleBtn = new Button(this.getApplicationContext());
        buttons.add(exampleBtn);
        if (canQuit) {
            Button closeBtn = new Button(this.getApplicationContext());            
            buttons.add(closeBtn);
        }
        answers.addTouchables(buttons);
    }

    @Override
    public void onBackPressed() {

    }
}
