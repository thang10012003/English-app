package com.tdtu.englishvocabquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TypeWordActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private TextView textProgress;
    private static TextView textQuestion;
    private static EditText editAnswer;
    private Button btnQuitTest;
    private Button btnNext;
    private TextView textCorrectNumber;
    ConstraintLayout layoutTest;
    ConstraintLayout layoutResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_word);

        progressBar = findViewById(R.id.progressBar);
        textProgress = findViewById(R.id.progress);
        textQuestion = findViewById(R.id.question);
        editAnswer = findViewById(R.id.answer);
        btnQuitTest = findViewById(R.id.btnQuitTest);
        btnNext = findViewById(R.id.btnNext);
        textCorrectNumber = findViewById(R.id.correctNumber);

        layoutResult = findViewById(R.id.layoutResult);
        layoutTest = findViewById(R.id.layoutTest);

        final Integer[] progressNumber = {0};// bien dem so cau

        ArrayList<VocabularyModel> topic;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            topic = VocabularyModel.generate();
        } else {
            topic = new ArrayList<>();
        }

        progressBar.setMax(topic.size());
        progressBar.setProgress(progressNumber[0]);
        textProgress.setText("0/"+topic.size());

        final QuestionModel[] questionModel = {loadQuestion(topic.get(progressNumber[0]),true)};


        ArrayList<VocabularyModel> finalTopic = topic;
        final int[] numCorrect = {0};//so cau dung

        //su kien chon dap an
        View.OnClickListener choose = v -> {
            //no empty answer
//            if (editAnswer.getText().toString().equals("")){
//                Toast.makeText(this, "Please enter answer",Toast.LENGTH_LONG).show();
//            }
//            else{

                //ghi cau tra loi
                questionModel[0].setChoice(editAnswer.getText().toString());
                progressNumber[0]++;//

                if(questionModel[0].getChoice().equals(questionModel[0].getCorrectAnswer())) numCorrect[0]++;// neu tra loi dung thi cong vao

                if (progressNumber[0] <finalTopic.size()){//neu chua toi cau cuoi thi di tiep
                    questionModel[0] = new QuestionModel();
                    VocabularyModel voca =  topic.get(progressNumber[0]);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        questionModel[0] = loadQuestion(voca,true);
                    }
                    textProgress.setText(progressNumber[0]+"/"+ finalTopic.size());//set progress bar
                    progressBar.setProgress(progressNumber[0]);
                }
                else{// toi cau cuoi thi hien ket qua
                    textCorrectNumber.setText("Kết quả: "+numCorrect[0]+"/"+finalTopic.size());
                    layoutTest.setVisibility(View.GONE);
                    layoutResult.setVisibility(View.VISIBLE);
                }
//            }
        };
        btnNext.setOnClickListener(choose);
    }
    public static QuestionModel loadQuestion(VocabularyModel v, Boolean englishMode){
        QuestionModel quest = new QuestionModel();
        if(englishMode){
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                quest.setQuestion(v.getEnglish() + " là ?");
                quest.setCorrectAnswer(v.getVietnamese());
            }
        }
        else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                quest.setQuestion(v.getVietnamese() + " là ?");
                quest.setCorrectAnswer(v.getEnglish());
            }
        }
        textQuestion.setText(quest.getQuestion());
        editAnswer.setText("");
        return quest;
    }
}