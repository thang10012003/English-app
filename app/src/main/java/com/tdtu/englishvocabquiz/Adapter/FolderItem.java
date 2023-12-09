package com.tdtu.englishvocabquiz.Adapter;

public class FolderItem {
    String FolderName;
    String CreatorName;

    public FolderItem(String folderName, String creatorName) {
        FolderName = folderName;
        CreatorName = creatorName;
    }

    public String getFolderName() {
        return FolderName;
    }

    public String getCreatorName() {
        return CreatorName;
    }

    public void setFolderName(String folderName) {
        FolderName = folderName;
    }

    public void setCreatorName(String creatorName) {
        CreatorName = creatorName;
    }
}
