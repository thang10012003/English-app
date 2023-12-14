package com.tdtu.englishvocabquiz.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.tdtu.englishvocabquiz.Listener.Word.OnWordListReady;
import com.tdtu.englishvocabquiz.Model.QuestionModel;
import com.tdtu.englishvocabquiz.Model.VocabularyModel;
import com.tdtu.englishvocabquiz.R;
import com.tdtu.englishvocabquiz.Service.TopicDatabaseService;

import java.util.ArrayList;
import java.util.Collections;

public class MultipleChoiceActivity extends AppCompatActivity {

    ArrayList<VocabularyModel> topic = new ArrayList<>();
    private TopicDatabaseService topicDatabaseService;
    private QuestionModel questionModel = new QuestionModel();
    private int numCorrect = 0;//so cau dung
    private Integer progressNumber = 0;
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
        Button btnQuitTest = findViewById(R.id.btnQuitTest);

        String IdTopic = getIntent().getStringExtra("IdTopic");
        Boolean englishMode = getIntent().getBooleanExtra("englishMode", false);
        Boolean shuffle = getIntent().getBooleanExtra("shuffle", false);

        topicDatabaseService = new TopicDatabaseService(getApplication());

        topic = topicDatabaseService.getWordFromTopic(IdTopic, new OnWordListReady() {
            @Override
            public void onListReady(ArrayList<VocabularyModel> vocabList) {
                if(shuffle) Collections.shuffle(topic);
                if (topic.size() != 0) {
                    progressBar.setMax(topic.size());
                    progressBar.setProgress(progressNumber);
                    numProgress.setText("0/" + topic.size());
                    questionModel = QuestionModel.generate(topic.get(0), topic, englishMode);
                    loadQuestion(questionModel, question, btnAnswer1, btnAnswer2, btnAnswer3, btnAnswer4);
                }
            }
        });
        //su kien chon dap an
        View.OnClickListener choose = v -> {
            //ghi cau tra loi
            if(v.getId() == R.id.btnAnswer1){
                questionModel.setChoice(questionModel.getAnswer().get(0));
            }
            else if(v.getId() == R.id.btnAnswer2){
                questionModel.setChoice(questionModel.getAnswer().get(1));
            }
            else if(v.getId() == R.id.btnAnswer3){
                questionModel.setChoice(questionModel.getAnswer().get(2));
            }
            else if(v.getId() == R.id.btnAnswer4){
                questionModel.setChoice(questionModel.getAnswer().get(3));
            }
            progressNumber++;//
            String TAG = "tag";
            if(questionModel.getChoice().equalsIgnoreCase(questionModel.getCorrectAnswer())) numCorrect++;// neu tra loi dung thi cong vao
            if (progressNumber <topic.size()){//neu chua toi cau cuoi thi di tiep
                questionModel = QuestionModel.generate(topic.get(progressNumber), topic,englishMode);
                loadQuestion(questionModel,question,btnAnswer1,btnAnswer2,btnAnswer3,btnAnswer4);
                numProgress.setText(progressNumber+"/"+ topic.size());
                progressBar.setProgress(progressNumber);
            }
            else{// toi cau cuoi thi hien ket qua
                correctNumber.setText("Kết quả: "+numCorrect+"/"+topic.size());
                layoutTest.setVisibility(View.GONE);
                layoutResult.setVisibility(View.VISIBLE);
            }

        };
        btnAnswer1.setOnClickListener(choose);
        btnAnswer2.setOnClickListener(choose);
        btnAnswer3.setOnClickListener(choose);
        btnAnswer4.setOnClickListener(choose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnQuitTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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