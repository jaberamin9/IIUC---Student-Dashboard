package com.blogspot.softlabsja.iiucstudentapp.CourseRegistration;


import org.jsoup.nodes.Element;

public class CourseRegistrationModel {
    String courseState;
    Element CheckboxValue, Credit_Hours;
    String Course_Code, Course_Name, Section;
    private boolean isSelected = false;

    public CourseRegistrationModel(String courseState, Element checkboxValue, Element credit_Hours, String course_Code, String course_Name, String section) {
        this.courseState = courseState;
        CheckboxValue = checkboxValue;
        Credit_Hours = credit_Hours;
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
