package com.tdtu.englishvocabquiz.Model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TopicModel {
    private String idTopic;
    private String topicName;
    private String description;
    private int numberOfVocab;
    private Date createDate;
    private String mode;
    private String idAuthor;

    public TopicModel(String idTopic, String topicName, String description,int numberOfVocab, Date createDate, String mode, String idUser) {
        this.idTopic = idTopic;
        this.topicName = topicName;
        this.numberOfVocab = numberOfVocab;
        this.description = description;
        this.createDate = createDate;
        this.mode = mode;
        this.idAuthor = idUser;
    }

    public TopicModel( String topicName, String description, int numberOfVocab, Date createDate, String mode, String idUser) {
        this.topicName = topicName;
        this.numberOfVocab = numberOfVocab;
        this.createDate = createDate;
        this.description = description;
        this.mode = mode;
        this.idAuthor = idUser;
    }
    public Map<String, Object> convertToMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("idTopic",idTopic);
        map.put("topicName",topicName);
        map.put("numberOfVocab",numberOfVocab);
        map.put("createDate",createDate);
        map.put("description",description);
        map.put("mode",mode);
        map.put("idAuthor",idAuthor);

        return map;

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIdTopic() {
        return idTopic;
    }

    public void setIdTopic(String idTopic) {
        this.idTopic = idTopic;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public int getNumberOfVocab() {
        return numberOfVocab;
    }

    public void setNumberOfVocab(int numberOfVocab) {
        this.numberOfVocab = numberOfVocab;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getIdAuthor() {
        return idAuthor;
    }

    public void setIdAuthor(String idAuthor) {
        this.idAuthor = idAuthor;
    }
}
