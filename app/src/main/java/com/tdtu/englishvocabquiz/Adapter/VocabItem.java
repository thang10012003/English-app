package com.tdtu.englishvocabquiz.Adapter;

public class VocabItem {
    private String word;
    private String mean;

    public VocabItem() {
        this.word = null;
        this.mean = null;
    }

    public VocabItem(String word, String mean) {
        this.word = word;
        this.mean = mean;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }
}
