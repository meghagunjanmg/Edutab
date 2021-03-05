package com.example.edutab;

import java.util.List;

public class quizmodel {
    List<String> question;
    List<String> correctans ;
    List<String> invalid1;
    List<String> invalid2;

    public List<String> getQuestion() {
        return question;
    }

    public void setQuestion(List<String> question) {
        this.question = question;
    }

    public List<String> getCorrectans() {
        return correctans;
    }

    public void setCorrectans(List<String> correctans) {
        this.correctans = correctans;
    }

    public List<String> getInvalid1() {
        return invalid1;
    }

    public void setInvalid1(List<String> invalid1) {
        this.invalid1 = invalid1;
    }

    public List<String> getInvalid2() {
        return invalid2;
    }

    public void setInvalid2(List<String> invalid2) {
        this.invalid2 = invalid2;
    }
}
