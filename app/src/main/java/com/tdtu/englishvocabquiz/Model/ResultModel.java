package com.tdtu.englishvocabquiz.Model;

public class ResultModel {
    private String idAuthor;
    private int numRight;

    public ResultModel(String idAuthor, int numRight) {
        this.idAuthor = idAuthor;
        this.numRight = numRight;
    }
    public ResultModel() {
    }
    public String getIdAuthor() {
        return idAuthor;
    }

    public void setIdAuthor(String idAuthor) {
        this.idAuthor = idAuthor;
    }

    public int getNumRight() {
        return numRight;
    }

    public void setNumRight(int numRight) {
        this.numRight = numRight;
    }

    @Override
    public String toString() {
        return "ResultModel{" +
                "idAuthor='" + idAuthor + '\'' +
                ", numRight=" + numRight +
                '}';
    }
}
