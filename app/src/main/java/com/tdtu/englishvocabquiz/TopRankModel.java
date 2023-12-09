package com.tdtu.englishvocabquiz;

import java.util.ArrayList;

public class TopRankModel {
    private String topic;
    private String userName;
    private Integer rank;
    private Integer score;

    public TopRankModel(String topic, String user, Integer rank, Integer score) {
        this.topic = topic;
        this.userName = user;
        this.rank = (rank>=1)?rank:-1;
        this.score = (score>=0)?score:-1;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = (rank>=1)?rank:-1;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = (score>=0)?score:-1;
    }

    public static ArrayList<TopRankModel> generate(Integer number){
        ArrayList<TopRankModel> topRankList = new ArrayList<>();

        for (int i = 0; i < number; i++) {
            topRankList.add(new TopRankModel("topic 1", "User top "+(i+1), i+1, number-i));
        }
        return topRankList;
    }
}
