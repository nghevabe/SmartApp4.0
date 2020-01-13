package com.example.smartapp;

public class DateTimeProcess {


    private int sYear;
    private int sMonth;
    private int sDay;

    private int sHour;
    private int sMinute;

    public int getsYear() {
        return sYear;
    }

    public void setsYear(int sYear) {
        this.sYear = sYear;
    }

    public int getsMonth() {
        return sMonth;
    }

    public void setsMonth(int sMonth) {
        this.sMonth = sMonth;
    }

    public int getsDay() {
        return sDay;
    }

    public void setsDay(int sDay) {
        this.sDay = sDay;
    }

    public int getsHour() {
        return sHour;
    }

    public void setsHour(int sHour) {
        this.sHour = sHour;
    }

    public int getsMinute() {
        return sMinute;
    }

    public void setsMinute(int sMinute) {
        this.sMinute = sMinute;
    }

    public void GetTimeForEvent(String input){

        // 1/6/2020 12:16:00 PM
        String[] DateTime_cut = input.split(" ");
        String[] Date_cut = DateTime_cut[0].split("/");

        String year = Date_cut[2];
        String month = Date_cut[0];
        String day = Date_cut[1];

        String[] Time_cut = DateTime_cut[1].split(":");

        String hour = Time_cut[0];
        String minute = Time_cut[1];

        sYear = Integer.parseInt(year);
        sMonth = Integer.parseInt(month);
        sDay = Integer.parseInt(day);

        sHour = Integer.parseInt(hour);

        String part = DateTime_cut[2];
        if(part.equals("PM")){
            sHour = sHour - 12;
        }

        sMinute = Integer.parseInt(minute);







    }


}
