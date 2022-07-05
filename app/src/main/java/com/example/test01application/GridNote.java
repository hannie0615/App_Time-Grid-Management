package com.example.test01application;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class GridNote {

    private String category;
    private int start_hour;
    private int start_min;
    private int end_hour;
    private int end_min;
    private int total_time;
    private String memo;

    public GridNote(){

    }

    public GridNote(String category, int start_hour, int start_min, int end_hour, int end_min, int total_time, String memo) {

        this.category = category;
        this.start_hour = start_hour;
        this.start_min = start_min;
        this.end_hour = end_hour;
        this.end_min = end_min;
        this.total_time = total_time;
        this.memo = memo;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getStart_hour() {
        return start_hour;
    }

    public void setStart_hour(int start_hour) {
        this.start_hour = start_hour;
    }

    public int getStart_min() { return start_min; }

    public void setStart_min(int start_min) {
        this.start_min = start_min;
    }

    public int getEnd_hour() {
        return end_hour;
    }

    public void setEnd_hour(int end_hour) {
        this.end_hour = end_hour;
    }

    public int getEnd_min() {
        return end_min;
    }

    public void setEnd_min(int end_min) {
        this.end_min = end_min;
    }


    public int getTotal_time() {
        return total_time;
    }

    public void setTotal_time(int total_time) {
        this.total_time = total_time;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
