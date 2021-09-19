package com.awareeTeam.awaree;

public class Homework {
    private String classRef;
    private String difficulty;
    private String duration;
    private String priority;
    private String dueDate;
    private String tempKey;

    public Homework() {
    }

    public Homework(String classRef, String difficulty, String duration, String priority, String dueDate, String tempKey) {
        this.classRef = classRef;
        this.difficulty = difficulty;
        this.duration = duration;
        this.priority = priority;
        this.dueDate = dueDate;
        this.tempKey = tempKey;
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

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getTempKey() {
        return tempKey;
    }

    public void setTempKey(String tempKey) {
        this.tempKey = tempKey;
    }
}
