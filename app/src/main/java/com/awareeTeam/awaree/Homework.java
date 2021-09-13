package com.awareeTeam.awaree;

public class Homework {
    private String classRef;
    private String difficulty;
    private String duration;
    private String priority;

    public Homework() {
    }

    public Homework(String classRef, String difficulty, String duration, String priority) {
        this.classRef = classRef;
        this.difficulty = difficulty;
        this.duration = duration;
        this.priority = priority;
    }

    public String getClassRef() {
        return classRef;
    }

    public void setClassRef(String classRef) {
        this.classRef = classRef;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
