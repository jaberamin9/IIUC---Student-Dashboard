package com.blogspot.softlabsja.iiucstudentapp.Ter.TerSubmission;

import java.util.ArrayList;

public class TerSubmissionModel {
    String name;
    boolean isActive = false;
    ArrayList<String> stringArrayList = new ArrayList<>();

    public TerSubmissionModel(String name) {
        this.name = name;

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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
