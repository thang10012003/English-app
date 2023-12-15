package com.tdtu.englishvocabquiz.Listener.Folder;

import com.tdtu.englishvocabquiz.Model.FolderModel;
import com.tdtu.englishvocabquiz.Model.TopicModel;

import java.util.ArrayList;

public interface OnFolderListReady {
    void onListReady(ArrayList<FolderModel> folderlist);
}
