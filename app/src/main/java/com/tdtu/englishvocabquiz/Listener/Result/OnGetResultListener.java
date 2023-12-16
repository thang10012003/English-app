package com.tdtu.englishvocabquiz.Listener.Result;

import com.tdtu.englishvocabquiz.Model.ResultModel;
import com.tdtu.englishvocabquiz.Model.VocabularyModel;

import java.util.ArrayList;

public interface OnGetResultListener {
    void onGetReady(ArrayList<ResultModel> resultModel);

}
