package com.tdtu.englishvocabquiz.Model;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FolderModel {
    private String id;
    private String name;
    private String idAuthor;
    private Date createDate;

    public FolderModel() {
        id = "";
        name = "";
        idAuthor = "";
        createDate = Calendar.getInstance().getTime();
    }
    public FolderModel( String name, String idAuthor) {
        this.name = name;
        this.idAuthor = idAuthor;
        this.createDate = Calendar.getInstance().getTime();
    }
    public FolderModel(String id, String name, String idAuthor) {
        this.id = id;
        this.name = name;
        this.idAuthor = idAuthor;
        this.createDate = Calendar.getInstance().getTime();
    }

    public Map<String, Object> convertToMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("id",id);
        map.put("nameFolder",name);
        map.put("idAuthor",idAuthor);
        map.put("createDate",createDate);
        return map;

    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
                ", name='" + name + '\'' +
                ", idAuthor='" + idAuthor + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
