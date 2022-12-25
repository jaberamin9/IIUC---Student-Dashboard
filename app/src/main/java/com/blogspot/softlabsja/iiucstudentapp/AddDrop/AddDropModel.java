package com.blogspot.softlabsja.iiucstudentapp.AddDrop;

import org.jsoup.nodes.Element;

public class AddDropModel {
    String courseState;
    Element CheckboxValue, Credit_Hours, selected;
    String Course_Code, Course_Name, Section;
    private boolean isSelected = false;

    public AddDropModel(String courseState, Element checkboxValue, Element credit_Hours, Element selected, String course_Code, String course_Name, String section) {
        this.courseState = courseState;
        CheckboxValue = checkboxValue;
        Credit_Hours = credit_Hours;
        this.selected = selected;
        Course_Code = course_Code;
        Course_Name = course_Name;
        Section = section;
    }

    public String getCourseState() {
        return courseState;
    }

    public void setCourseState(String courseState) {
        this.courseState = courseState;
    }

    public Element getCheckboxValue() {
        return CheckboxValue;
    }

    public void setCheckboxValue(Element checkboxValue) {
        CheckboxValue = checkboxValue;
    }

    public Element getCredit_Hours() {
        return Credit_Hours;
    }

    public void setCredit_Hours(Element credit_Hours) {
        Credit_Hours = credit_Hours;
    }

    public Element getSelected() {
        return selected;
    }

    public void setSelected(Element selected) {
        this.selected = selected;
    }

    public String getCourse_Code() {
        return Course_Code;
    }

    public void setCourse_Code(String course_Code) {
        Course_Code = course_Code;
    }

    public String getCourse_Name() {
        return Course_Name;
    }

    public void setCourse_Name(String course_Name) {
        Course_Name = course_Name;
    }

    public String getSection() {
        return Section;
    }

    public void setSection(String section) {
        Section = section;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
