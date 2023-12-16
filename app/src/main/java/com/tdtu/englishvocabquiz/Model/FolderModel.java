package com.tdtu.englishvocabquiz.Model;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FolderModel {
    private String id;
    private String nameFolder;
    private String idAuthor;
    private Date createDate;
    private int numTopic;

    public FolderModel() {
        id = "";
        nameFolder = "";
        idAuthor = "";
        createDate = Calendar.getInstance().getTime();
        numTopic = 0;
    }
    public FolderModel( String name, String idAuthor) {
        this.nameFolder = name;
        this.idAuthor = idAuthor;
        this.createDate = Calendar.getInstance().getTime();
        this.numTopic = 0;
    }
    public FolderModel(String id, String name, String idAuthor) {
        this.id = id;
        this.nameFolder = name;
        this.idAuthor = idAuthor;
        this.createDate = Calendar.getInstance().getTime();
        this.numTopic = 0;
    }

    public Map<String, Object> convertToMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("id",id);
        map.put("nameFolder",nameFolder);
        map.put("idAuthor",idAuthor);
        map.put("createDate",createDate);
        map.put("numTopic",numTopic);
        return map;

    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameFolder() {
        return nameFolder;
    }

    public void setNameFolder(String nameFolder) {
        this.nameFolder = nameFolder;
    }

    public String getIdAuthor() {
        return idAuthor;
    }

    public void setIdAuthor(String idAuthor) {
        this.idAuthor = idAuthor;
    }

    @Override
    public String toString() {
        return "FolderModel{" +
                "id='" + id + '\'' +
                ", name='" + nameFolder + '\'' +
                ", idAuthor='" + idAuthor + '\'' +
                ", createDate=" + createDate +
                ", numTopic=" + numTopic +
                '}';
    }

    public int getNumTopic() {
        return numTopic;
    }

    public void setNumTopic(int numTopic) {
        this.numTopic = numTopic;
    }

    public void addNumTopic(){
        this.numTopic = this.numTopic + 1;
    }
    public void minusNumTopic(){
        this.numTopic = this.numTopic - 1;
    }
}
