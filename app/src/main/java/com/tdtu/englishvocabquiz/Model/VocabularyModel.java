package com.tdtu.englishvocabquiz.Model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.tdtu.englishvocabquiz.Enum.WordType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class VocabularyModel {
    private String id;
    private String english;
    private String vietnamese;
    private Date createDate;
    private String progress;
    private boolean mark;
    private String img;
    private String desc;
    private String id_topic;
    private int countAchieve;

//    LocalDate myObj = LocalDate.now();
//    String dateNow = String.valueOf(myObj);
    public VocabularyModel(){
        this.mark = false;
        createDate = Calendar.getInstance().getTime();
        this.progress = WordType.NOT_ACHIEVED.toString();
        this.countAchieve = 0;

    }
    // Copy constructor
    public VocabularyModel(VocabularyModel original) {
        this.id = original.id;
        this.english = original.english;
        this.vietnamese = original.vietnamese;
        this.createDate = original.createDate;
        this.progress = original.progress;
        this.mark = original.mark;
        this.img = original.img;
        this.desc = original.desc;
        this.id_topic = original.id_topic;
        this.countAchieve = original.countAchieve;
    }
    public VocabularyModel(String id, String english, String vietnamese, Date createDate, String progress, boolean mark, String img, String desc, String id_topic, int countAchieve) {
        this.id = id;
        this.english = english;
        this.vietnamese = vietnamese;
        this.createDate = createDate;
        this.progress = progress;
        this.mark = mark;
        this.img = img;
        this.desc = desc;
        this.id_topic = id_topic;
        this.countAchieve = countAchieve;
    }

    public VocabularyModel(String id, String english, String vietnamese, String id_topic) {
        this.id = id;
        this.english = english;
        this.vietnamese = vietnamese;
        this.img = "";
        this.desc = "";
        this.id_topic = id_topic;
        this.mark = false;
        this.createDate = Calendar.getInstance().getTime();;
        this.progress = WordType.NOT_ACHIEVED.toString();
        this.countAchieve = 0;
    }
    public Map<String, Object> convertToMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("idTopic",id_topic);
        map.put("idWord",id);
        map.put("word",english);
        map.put("mean",vietnamese);
        map.put("imgUrl",img);
        map.put("mark",mark);
        map.put("createDate",createDate);
        map.put("progress",progress);
        map.put("countAchieve",countAchieve);
        map.put("desc",desc);

        return map;

    }

    public static ArrayList<VocabularyModel> generate(){
        ArrayList<VocabularyModel> data = new ArrayList<>();

/*        data.add(new VocabularyModel("001","hello", "xin chào", "", "hello là xin chào", "topic1"));
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
        data.add(new VocabularyModel("020","computer", "máy tính", "", "computer là máy tính", "topic3"));*/

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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
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

    public int getCountAchieve() {
        return countAchieve;
    }

    public void setCountAchieve(int countAchieve) {
        this.countAchieve = countAchieve;
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
