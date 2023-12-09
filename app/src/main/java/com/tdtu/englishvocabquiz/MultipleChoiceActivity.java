package com.tdtu.englishvocabquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MultipleChoiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_choice);

        ConstraintLayout layoutTest = findViewById(R.id.layoutTest);
        ConstraintLayout layoutResult = findViewById(R.id.layoutResult);

        TextView numProgress = findViewById(R.id.progress);
        TextView correctNumber = findViewById(R.id.correctNumber);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        ImageView btnClose = findViewById(R.id.btnClose);

        TextView question = findViewById(R.id.question);
        Button btnAnswer1 = findViewById(R.id.btnAnswer1);
        Button btnAnswer2 = findViewById(R.id.btnAnswer2);
        Button btnAnswer3 = findViewById(R.id.btnAnswer3);
        Button btnAnswer4 = findViewById(R.id.btnAnswer4);
        Button quitTest = findViewById(R.id.btnQuitTest);

        final Integer[] progressNumber = {0};

        ArrayList<VocabularyModel> topic = new ArrayList<>();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            topic = VocabularyModel.generate();
        }

        progressBar.setMax(topic.size());
        progressBar.setProgress(progressNumber[0]);
        numProgress.setText("0/"+topic.size());

        final QuestionModel[] questionModel = {QuestionModel.generate(topic.get(0), topic, true)};
        loadQuestion(questionModel[0],question,btnAnswer1,btnAnswer2,btnAnswer3,btnAnswer4);

        ArrayList<VocabularyModel> finalTopic = topic;
        ArrayList<VocabularyModel> finalTopic1 = topic;
        final int[] numCorrect = {0};//so cau dung

        //su kien chon dap an
        View.OnClickListener choose = v -> {
            //ghi cau tra loi
            if(v.getId() == R.id.btnAnswer1){
                questionModel[0].setChoice(btnAnswer1.getText().toString());
            }
            else if(v.getId() == R.id.btnAnswer2){
                questionModel[0].setChoice(btnAnswer2.getText().toString());
            }
            else if(v.getId() == R.id.btnAnswer3){
                questionModel[0].setChoice(btnAnswer3.getText().toString());
            }
            else if(v.getId() == R.id.btnAnswer4){
                questionModel[0].setChoice(btnAnswer4.getText().toString());
            }
            progressNumber[0]++;//

            if(questionModel[0].getChoice().equals(questionModel[0].getCorrectAnswer())) numCorrect[0]++;// neu tra loi dung thi cong vao

            if (progressNumber[0] <finalTopic1.size()){//neu chua toi cau cuoi thi di tiep
                questionModel[0] = QuestionModel.generate(finalTopic.get(progressNumber[0]), finalTopic,true);
                loadQuestion(questionModel[0],question,btnAnswer1,btnAnswer2,btnAnswer3,btnAnswer4);
                numProgress.setText(progressNumber[0]+"/"+ finalTopic1.size());
                progressBar.setProgress(progressNumber[0]);
            }
            else{// toi cau cuoi thi hien ket qua
                correctNumber.setText("Kết quả: "+numCorrect[0]+"/"+finalTopic1.size());
                layoutTest.setVisibility(View.GONE);
                layoutResult.setVisibility(View.VISIBLE);
            }

        };
        btnAnswer1.setOnClickListener(choose);
        btnAnswer2.setOnClickListener(choose);
        btnAnswer3.setOnClickListener(choose);
        btnAnswer4.setOnClickListener(choose);
    }
    //ham load cau hoi moi
    public void loadQuestion(QuestionModel questionModel, TextView question, Button ans1, Button ans2, Button ans3, Button ans4){
        question.setText(questionModel.getQuestion());
        ans1.setText("A. "+questionModel.getAnswer().get(0));
        ans2.setText("B. "+questionModel.getAnswer().get(1));
        ans3.setText("C. "+questionModel.getAnswer().get(2));
        ans4.setText("D. "+questionModel.getAnswer().get(3));
    }
}