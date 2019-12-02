package com.example.trivia.model;

public class Question {

    private String quest;
    private boolean answer;

    public Question() {
    }

    public String getQuest() {
        return quest;
    }

    public void setQuest(String quest) {
        this.quest = quest;
    }

    public boolean getAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "Question{" +
                "quest='" + quest + '\'' +
                ", answer=" + answer +
                '}';
    }
}