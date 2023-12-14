package com.tdtu.englishvocabquiz.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tdtu.englishvocabquiz.Listener.Word.OnWordListReady;
import com.tdtu.englishvocabquiz.Model.QuestionModel;
import com.tdtu.englishvocabquiz.Model.VocabularyModel;
import com.tdtu.englishvocabquiz.R;
import com.tdtu.englishvocabquiz.Service.TopicDatabaseService;

import java.util.ArrayList;
import java.util.Collections;

public class TypeWordActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private TextView textProgress;
    private static TextView textQuestion;
    private static EditText editAnswer;
    private Button btnQuitTest;
    private Button btnNext;
    private ImageButton btnClose;
    private TextView textCorrectNumber;
    ConstraintLayout layoutTest;
    ConstraintLayout layoutResult;
    private Integer progressNumber = 0;// bien dem so cau
    private int numCorrect = 0;//so cau dung
    private QuestionModel questionModel = new QuestionModel();
    private TopicDatabaseService topicDatabaseService;
    ArrayList<VocabularyModel> topic = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_word);

        progressBar = findViewById(R.id.progressBar);
        textProgress = findViewById(R.id.progress);
        textQuestion = findViewById(R.id.question);
        editAnswer = findViewById(R.id.answer);
        btnQuitTest = findViewById(R.id.btnQuitTest);
        btnClose = findViewById(R.id.btnClose);
        btnNext = findViewById(R.id.btnNext);
        textCorrectNumber = findViewById(R.id.correctNumber);

        layoutResult = findViewById(R.id.layoutResult);
        layoutTest = findViewById(R.id.layoutTest);

        String IdTopic = getIntent().getStringExtra("IdTopic");
        Boolean englishMode = getIntent().getBooleanExtra("englishMode", false);
        Boolean shuffle = getIntent().getBooleanExtra("shuffle", false);
        topicDatabaseService = new TopicDatabaseService(getApplication());


        topic = topicDatabaseService.getWordFromTopic(IdTopic, new OnWordListReady() {
            @Override
            public void onListReady(ArrayList<VocabularyModel> vocabList) {
                if (topic.size() != 0) {
                    if(shuffle) Collections.shuffle(topic);
                    progressBar.setMax(topic.size());
                    progressBar.setProgress(progressNumber);
                    textProgress.setText("0/"+topic.size());
                    questionModel = loadQuestion(topic.get(progressNumber),englishMode);
                }
            }
        });

//        ArrayList<VocabularyModel> finaltopic = topic;

        //su kien chon dap an
        View.OnClickListener choose = v -> {
            //no empty answer
//            if (editAnswer.getText().toString().equals("")){
//                Toast.makeText(this, "Please enter answer",Toast.LENGTH_LONG).show();
//            }
//            else{

                //ghi cau tra loi
                questionModel.setChoice(editAnswer.getText().toString());
                progressNumber++;//

                // neu tra loi dung thi cong vao
                if(questionModel.getChoice().equalsIgnoreCase(questionModel.getCorrectAnswer())) numCorrect++;

                if (progressNumber <topic.size()){//neu chua toi cau cuoi thi di tiep
                    questionModel = new QuestionModel();
                    VocabularyModel voca =  topic.get(progressNumber);
                        questionModel = loadQuestion(voca,englishMode);
                    textProgress.setText(progressNumber+"/"+ topic.size());//set progress bar
                    progressBar.setProgress(progressNumber);
                }
                else{// toi cau cuoi thi hien ket qua
                    textCorrectNumber.setText("Kết quả: "+numCorrect+"/"+topic.size());
                    layoutTest.setVisibility(View.GONE);
                    layoutResult.setVisibility(View.VISIBLE);
                }
//            }
        };
        btnNext.setOnClickListener(choose);
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
    public static QuestionModel loadQuestion(VocabularyModel v, Boolean englishMode){
        QuestionModel quest = new QuestionModel();
        if(englishMode){
            quest.setQuestion(v.getEnglish() + " là ?");
            quest.setCorrectAnswer(v.getVietnamese());
        }
        else {
            quest.setQuestion(v.getVietnamese() + " là ?");
            quest.setCorrectAnswer(v.getEnglish());
        }
        textQuestion.setText(quest.getQuestion());
        editAnswer.setText("");
        return quest;
    }
}