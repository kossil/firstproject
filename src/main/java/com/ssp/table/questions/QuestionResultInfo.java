package com.ssp.table.questions;

public class QuestionResultInfo {
//    private Long idQuestion;
//    private Long answer;
    private Long sumResult;

    public QuestionResultInfo(/*Long idQuestion, Long answer,*/ Long sumResult) {
        super();
//        this.idQuestion = idQuestion;
//        this.answer = answer;
        this.sumResult = sumResult;
    }

//    public Long getIdQuestion() {
//        return idQuestion;
//    }
//
//    public void setIdQuestion(Long idQuestion) {
//        this.idQuestion = idQuestion;
//    }
//
//    public Long getAnswer() {
//        return answer;
//    }
//
//    public void setAnswer(Long answer) {
//        this.answer = answer;
//    }

    public Long getSumResult() {
        return sumResult;
    }

    public void setSumResult(Long sumResult) {
        this.sumResult = sumResult;
    }
}

