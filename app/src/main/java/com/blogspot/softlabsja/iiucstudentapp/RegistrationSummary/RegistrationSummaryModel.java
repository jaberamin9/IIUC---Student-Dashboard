package com.blogspot.softlabsja.iiucstudentapp.RegistrationSummary;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;

public class RegistrationSummaryModel {
    int RS_semesterNo;
    String m_RS_semester, m_RS_enrolled, m_RS_total_CH, m_RS_Urc, m_RS_Dc, m_RS_Cost, m_RS_Payment, m_RS_Gpa, m_RS_Cgpa;
    Element link;

    public RegistrationSummaryModel(int RS_semesterNo, String m_RS_semester, String m_RS_enrolled, String m_RS_total_CH, String m_RS_Urc, String m_RS_Dc, String m_RS_Cost, String m_RS_Payment, String m_RS_Gpa, String m_RS_Cgpa, Element link) {
        this.RS_semesterNo = RS_semesterNo;
        this.m_RS_semester = m_RS_semester;
        this.m_RS_enrolled = m_RS_enrolled;
        this.m_RS_total_CH = m_RS_total_CH;
        this.m_RS_Urc = m_RS_Urc;
        this.m_RS_Dc = m_RS_Dc;
        this.m_RS_Cost = m_RS_Cost;
        this.m_RS_Payment = m_RS_Payment;
        this.m_RS_Gpa = m_RS_Gpa;
        this.m_RS_Cgpa = m_RS_Cgpa;
        this.link = link;
    }

    public int getRS_semesterNo() {
        return RS_semesterNo;
    }

    public void setRS_semesterNo(int RS_semesterNo) {
        this.RS_semesterNo = RS_semesterNo;
    }

    public String getM_RS_semester() {
        return m_RS_semester;
    }

    public void setM_RS_semester(String m_RS_semester) {
        this.m_RS_semester = m_RS_semester;
    }

    public String getM_RS_enrolled() {
        return m_RS_enrolled;
    }

    public void setM_RS_enrolled(String m_RS_enrolled) {
        this.m_RS_enrolled = m_RS_enrolled;
    }

    public String getM_RS_total_CH() {
        return m_RS_total_CH;
    }

    public void setM_RS_total_CH(String m_RS_total_CH) {
        this.m_RS_total_CH = m_RS_total_CH;
    }

    public String getM_RS_Urc() {
        return m_RS_Urc;
    }

    public void setM_RS_Urc(String m_RS_Urc) {
        this.m_RS_Urc = m_RS_Urc;
    }

    public String getM_RS_Dc() {
        return m_RS_Dc;
    }

    public void setM_RS_Dc(String m_RS_Dc) {
        this.m_RS_Dc = m_RS_Dc;
    }

    public String getM_RS_Cost() {
        return m_RS_Cost;
    }

    public void setM_RS_Cost(String m_RS_Cost) {
        this.m_RS_Cost = m_RS_Cost;
    }

    public String getM_RS_Payment() {
        return m_RS_Payment;
    }

    public void setM_RS_Payment(String m_RS_Payment) {
        this.m_RS_Payment = m_RS_Payment;
    }

    public String getM_RS_Gpa() {
        return m_RS_Gpa;
    }

    public void setM_RS_Gpa(String m_RS_Gpa) {
        this.m_RS_Gpa = m_RS_Gpa;
    }

    public String getM_RS_Cgpa() {
        return m_RS_Cgpa;
    }

    public void setM_RS_Cgpa(String m_RS_Cgpa) {
        this.m_RS_Cgpa = m_RS_Cgpa;
    }

    public Element getLink() {
        return link;
    }

    public void setLink(Element link) {
        this.link = link;
    }
}
