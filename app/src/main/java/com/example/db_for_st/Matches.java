package com.example.db_for_st;

import java.io.Serializable;

public class Matches implements Serializable {
    private long id;
    private String name;
    private int year;
    private int yearinschool;
    private String history;

    public Matches(long id, String name, int year, int yearinschool, String history) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.yearinschool = yearinschool;
        this.history=history;
    }

    public long getId() {
        return id;
    }

    public String getTeamhouse() {
        return name;
    }

    public int getTeamguest() {
        return year;
    }

    public int getGoalshouse() {
        return yearinschool;
    }

    public String getGoalsguest() {
        return history;
    }
}