package com.tdtu.englishvocabquiz.Activity;

import android.os.Build;

import java.util.ArrayList;
import java.util.Collections;

public class QuestionModel {
    private String question = "?";
    private ArrayList<String> answer;
    private String choice;
    private String correctAnswer;
    private boolean correct = false;

    public QuestionModel(String question, ArrayList<String> answer, String correctAnswer) {
        this.question = question;
        this.answer = answer;
        this.correctAnswer = correctAnswer;
    }

    public QuestionModel() {
        question = "?";
        answer = new ArrayList<String>();
    }

    public static QuestionModel generate(VocabularyModel v, ArrayList<VocabularyModel> topic, boolean englishMode){
        QuestionModel quest = new QuestionModel();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if(englishMode){
                quest.question = v.getEnglish()+" mean?";
                quest.setCorrectAnswer(v.getVietnamese());
            }
            else {
                quest.question = v.getVietnamese()+" nghĩa là?";
                quest.setCorrectAnswer(v.getEnglish());
            }
            quest.answer = v.randomAnswer(topic, englishMode);

        }
        Collections.shuffle(quest.answer);
        return quest;
    }
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<String> getAnswer() {
        return answer;
    }

    public void setAnswer(ArrayList<String> answer) {
        this.answer = answer;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }
}
