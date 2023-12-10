package com.tdtu.englishvocabquiz.Listener.Topic;

import com.tdtu.englishvocabquiz.Model.TopicModel;

import java.util.ArrayList;

public interface OnTopicListReady {
    void onListReady(ArrayList<TopicModel> topicList);
}
