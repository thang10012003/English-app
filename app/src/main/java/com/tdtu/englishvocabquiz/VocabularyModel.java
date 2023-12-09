package com.tdtu.englishvocabquiz;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

@RequiresApi(api = Build.VERSION_CODES.O)
public class VocabularyModel {
    private String id;
    private String english;
    private String vietnamese;
    private String createDate;
    private String progress;
    private boolean mark;
    private String img;
    private String desc;
    private String id_topic;

    LocalDate myObj = LocalDate.now();
    String dateNow = String.valueOf(myObj);
    public VocabularyModel(String id, String english, String vietnamese, String img, String desc, String id_topic) {
        this.id = id;
        this.english = english;
        this.vietnamese = vietnamese;
        this.img = img;
        this.desc = desc;
        this.id_topic = id_topic;
        this.mark = false;
        this.createDate = dateNow;
        this.progress = "";
    }
    public static ArrayList<VocabularyModel> generate(){
        ArrayList<VocabularyModel> data = new ArrayList<>();

        data.add(new VocabularyModel("001","hello", "xin chào", "", "hello là xin chào", "topic1"));
        data.add(new VocabularyModel("002","goodbye", "tạm biệt", "", "goodbye là tạm biệt", "topic1"));
        data.add(new VocabularyModel("003","one", "một", "", "one là một", "topic2"));
        data.add(new VocabularyModel("004","two", "hai", "", "two là hai", "topic2"));
        data.add(new VocabularyModel("005","three", "ba", "", "three là ba", "topic2"));
        data.add(new VocabularyModel("006","four", "bốn", "", "four là bốn", "topic2"));
        data.add(new VocabularyModel("007","five", "năm", "", "five là năm", "topic2"));
        data.add(new VocabularyModel("008","six", "sáu", "", "six là sáu", "topic2"));
        data.add(new VocabularyModel("009","seven", "bảy", "", "seven là bảy", "topic2"));
        data.add(new VocabularyModel("010","eight", "tám", "", "eight là tám", "topic2"));
        data.add(new VocabularyModel("011","nine", "chín", "", "nine là chín", "topic2"));
        data.add(new VocabularyModel("012","ten", "mười", "", "ten là mười", "topic2"));
        data.add(new VocabularyModel("013","table", "cái bàn", "", "table là cái bàn", "topic3"));
        data.add(new VocabularyModel("014","chair", "cái ghế", "", "chair là cái ghế", "topic3"));
        data.add(new VocabularyModel("015","bed", "cái giường", "", "bed là cái giường", "topic3"));
        data.add(new VocabularyModel("016","bag", "cái cặp", "", "bag là cái cặp", "topic3"));
        data.add(new VocabularyModel("017","pen", "cái bút", "", "pen là cái bút", "topic3"));
        data.add(new VocabularyModel("018","shoe", "chiếc giày", "", "shoe là chiếc giày", "topic3"));
        data.add(new VocabularyModel("019","hat", "cái nón", "", "hat là cái nón", "topic3"));
        data.add(new VocabularyModel("020","computer", "máy tính", "", "computer là máy tính", "topic3"));

        return data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getVietnamese() {
        return vietnamese;
    }

    public void setVietnamese(String vietnamese) {
        this.vietnamese = vietnamese;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public boolean isMark() {
        return mark;
    }

    public void setMark(boolean mark) {
        this.mark = mark;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getId_topic() {
        return id_topic;
    }

    public void setId_topic(String id_topic) {
        this.id_topic = id_topic;
    }

    public ArrayList<String> randomAnswer(ArrayList<VocabularyModel> VocaList, boolean englishMode){
        ArrayList<String> answer = new ArrayList<String>();
        Random rand = new Random();
        if(!VocaList.isEmpty()){
           answer.add(englishMode?this.vietnamese:this.english); // add correct answer
           for (int i = 0; i<3; i++){
               int randInt = rand.nextInt(VocaList.size());
               while(this.equals(VocaList.get(randInt))) randInt = rand.nextInt(VocaList.size());
               //add random answer
               answer.add(englishMode? VocaList.get(randInt).vietnamese : VocaList.get(randInt).english);
           }
        }
        return answer;
    }
}
