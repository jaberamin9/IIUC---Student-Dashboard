package com.blogspot.softlabsja.iiucstudentapp.Ter.CourseSurvey;

import java.util.ArrayList;

public class CourseSurveyModel {
    String name, value;
    boolean isActive = false;
    ArrayList<String> stringArrayList = new ArrayList<>();

    public CourseSurveyModel(String name, String value) {
        this.name = name;
        this.value = value;
        for (int i = 0; i < 5; i++) {
            stringArrayList.add(i, "-1");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
