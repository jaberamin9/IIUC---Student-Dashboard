package com.blogspot.softlabsja.iiucstudentapp.Ter;

import android.util.Pair;

import java.util.ArrayList;

public class TerModel {
    String cc, cn, ch, ss, t, s, a;
    ArrayList<Pair<String, String>> arrayList;
    boolean isActive = false;

    public TerModel(String cc, String cn, String ch, String ss, String t, String s, String a, ArrayList<Pair<String, String>> arrayList) {
        this.cc = cc;
        this.cn = cn;
        this.ch = ch;
        this.ss = ss;
        this.t = t;
        this.s = s;
        this.a = a;
        this.arrayList = arrayList;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public String getCh() {
        return ch;
    }

    public void setCh(String ch) {
        this.ch = ch;
    }

    public String getSs() {
        return ss;
    }

    public void setSs(String ss) {
        this.ss = ss;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public ArrayList<Pair<String, String>> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<Pair<String, String>> arrayList) {
        this.arrayList = arrayList;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
