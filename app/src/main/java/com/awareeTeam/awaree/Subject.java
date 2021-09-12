package com.awareeTeam.awaree;

public class Subject {
    private String subjectName;
    private String subjectCategory;
    private String credits;
    private String coursesNumber;
    private String seminariesNumber;
    private String labsNumber;
    private String isExam;

    public Subject() {
    }

    public Subject(String subjectName,
                   String subjectCategory,
                   String credits,
                   String coursesNumber,
                   String seminariesNumber,
                   String labsNumber,
                   String isExam) {
        this.subjectName = subjectName;
        this.subjectCategory = subjectCategory;
        this.credits = credits;
        this.coursesNumber = coursesNumber;
        this.seminariesNumber = seminariesNumber;
        this.labsNumber = labsNumber;
        this.isExam = isExam;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectCategory() {
        return subjectCategory;
    }

    public void setSubjectCategory(String subjectCategory) {
        this.subjectCategory = subjectCategory;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getCoursesNumber() {
        return coursesNumber;
    }

    public void setCoursesNumber(String coursesNumber) {
        this.coursesNumber = coursesNumber;
    }

    public String getSeminariesNumber() {
        return seminariesNumber;
    }

    public void setSeminariesNumber(String seminariesNumber) {
        this.seminariesNumber = seminariesNumber;
    }

    public String getLabsNumber() {
        return labsNumber;
    }

    public void setLabsNumber(String labsNumber) {
        this.labsNumber = labsNumber;
    }

    public String getIsExam() {
        return isExam;
    }

    public void setIsExam(String exam) {
        isExam = exam;
    }
}
