package com.blogspot.softlabsja.iiucstudentapp.CourseStatus;

import org.jsoup.select.Elements;

public class CourseStatusModel {
    String data;
    Elements headData;

    public CourseStatusModel(String data, Elements headData) {
        this.data = data;
        this.headData = headData;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Elements getHeadData() {
        return headData;
    }

    public void setHeadData(Elements headData) {
        this.headData = headData;
    }
}
