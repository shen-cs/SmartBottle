package com.example.shen.smartbottle;

/**
 * Created by Shen Chang-Shao on 2016/9/2.
 *
 */
public class Record {
    private long id;
    private int year;
    private int month;
    private int day;
    private String time;
    private float consumption;
   public Record(long _id, int _year, int _month, int _day, String _time,  float _consumption) {
       id = _id;
       year = _year;
       month = _month;
       day = _day;
       time = _time;
       consumption = _consumption;
   }

    public long getId() {
        return id;
    }
    public int getDay() {
        return day;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public String getTime() {
        return time;
    }

    public float getConsumption() {
        return consumption;
    }

    public String getDate() {
        return year + "/" + month + "/" + day + " " + time;
    }
}
