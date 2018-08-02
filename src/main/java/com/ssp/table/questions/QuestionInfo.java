package com.ssp.table.questions;

public class QuestionInfo {

    private String question;
    private Long id;
    private String average;
    private Long meaning;
    public QuestionInfo() {
    }
    public QuestionInfo(Long id, String question,String average,Long meaning) {
        super();
        this.id = id;
        this.question = question;
        this.average = average;
        this.meaning = meaning;

    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAverage() {
        return average;
    }

    public void setAverage(String average) {
        this.average = average;
    }

    public Long getMeaning() {
        return meaning;
    }

    public void setMeaning(Long meaning) {
        this.meaning = meaning;
    }
}

