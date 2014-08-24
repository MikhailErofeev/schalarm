package com.example.schalarm_android_app.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.schalarm_android_app.R;
import com.example.schalarm_android_app.utils.InjectorApplication;
import com.github.mikhailerofeev.scholarm.api.entities.Question;
import com.github.mikhailerofeev.scholarm.api.services.QuestionsService;

import java.util.*;

/**
 * Created by FFX20413 on 23.08.2014.
 */
public class QueryShowerActivity extends Activity {
    private static Set<String> tags = new HashSet<String>() {{
        add("programming");
    }};

    QuestionsService questionsService;


    private TextView questionText;
    private LinearLayout answersLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qa);
        questionText = (TextView) findViewById(R.id.question_text);
        answersLayout = (LinearLayout) findViewById(R.id.answers_list);
        questionsService = InjectorApplication.get(QuestionsService.class);
        Question question1 = getNextQuestion();
        boolean canQuit = false;
        setNewQuestion(question1, canQuit);
    }

    private Random random = new Random();

    private Question getNextQuestion() {
        List<Question> questions = questionsService.getQuestions(tags);
        int index = random.nextInt(questions.size());
        return questions.get(index);
    }

    private void setNewQuestion(final Question question, boolean canQuit) {
        questionText.setText(question.getQuestionText());
        answersLayout.removeAllViews();

        if (question.getRightAnswers().size() == 1) {
            setOneAnswerButtons(question);
        }
        if (canQuit) {
            Button closeBtn = new Button(this.getApplicationContext());
            answersLayout.addView(closeBtn);
        }
    }

    private void setOneAnswerButtons(Question question) {
        ActionBar.LayoutParams fillParams = new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        final Character rightAnswer = question.getRightAnswers().iterator().next();
        final Map<Character, Button> answersBtns = new HashMap<>();
        for (Map.Entry<Character, String> key2answer : question.getVariants().entrySet()) {
            final Button answerBtn = new Button(this.getApplicationContext());
            answersBtns.put(key2answer.getKey(), answerBtn);
            answerBtn.setText(key2answer.getValue());
            answerBtn.setTextSize(14);
            answerBtn.setLayoutParams(fillParams);
            answerBtn.setTag(key2answer.getKey());
            answerBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button btn = (Button) v;
                    Character answer = (Character) btn.getTag();
                    boolean correct = rightAnswer.equals(answer);
                    if (correct) {
                        btn.setBackgroundColor(Color.GREEN);
                    } else {
                        btn.setBackgroundColor(Color.RED);
                        answersBtns.get(rightAnswer).setBackgroundColor(Color.GREEN);
                    }
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setNewQuestion(getNextQuestion(), false);
                        }
                    }, 2000);

                }
            });
            answersLayout.addView(answerBtn);
        }
    }

    @Override
    public void onBackPressed() {

    }
}
