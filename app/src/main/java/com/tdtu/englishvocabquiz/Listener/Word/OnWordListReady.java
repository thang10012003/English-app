package com.tdtu.englishvocabquiz.Listener.Word;

import com.tdtu.englishvocabquiz.Model.TopicModel;
import com.tdtu.englishvocabquiz.Model.VocabularyModel;

import java.util.ArrayList;

public interface OnWordListReady {
    void onListReady(ArrayList<VocabularyModel> vocabList);
}
