package com.tdtu.englishvocabquiz.Adapter;

public class TopicItem {
    String FolderName;
    int CountWord;
    String CreatorName;


    public TopicItem(String folderName, int countWord, String creatorName) {
        FolderName = folderName;
        CountWord = countWord;
        CreatorName = creatorName;
    }

    public String getFolderName() {
        return FolderName;
    }

    public void setFolderName(String folderName) {
        FolderName = folderName;
    }

    public int getCountWord() {
        return CountWord;
    }

    public void setCountWord(int countWord) {
        CountWord = countWord;
    }

    public String getCreatorName() {
        return CreatorName;
    }

    public void setCreatorName(String creatorName) {
        CreatorName = creatorName;
    }
}
