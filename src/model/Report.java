package model;

import java.time.LocalDateTime;

public class Report {
    private Integer month;
    private String type;
    private Integer numberAppt;

    public Report(Integer month, String type, Integer numberAppt){
        this.month = month;
        this.type = type;
        this.numberAppt = numberAppt;
    }
    public Report(Integer month, Integer numberAppt){
        this.month = month;
        this.numberAppt = numberAppt;
    }
    public Report(String type, Integer numberAppt){
        this.type = type;
        this.numberAppt = numberAppt;
    }

    public String getMonth(){
        String monthText = null;
        switch (this.month){
            case 1:
                monthText = "JAN";
                break;
            case 2:
                monthText = "FEB";
                break;
            case 3:
                monthText = "MAR";
                break;
            case 4:
                monthText = "APR";
                break;
            case 5:
                monthText = "MAY";
                break;
            case 6:
                monthText = "JUN";
                break;
            case 7:
                monthText = "JUL";
                break;
            case 8:
                monthText = "AUG";
                break;
            case 9:
                monthText = "SEP";
                break;
            case 10:
                monthText = "OCT";
                break;
            case 11:
                monthText = "NOV";
                break;
            case 12:
                monthText = "DEC";
                break;
        }
        return monthText;
    }

    public Integer getNumberAppt() {
        return numberAppt;
    }

    public String getType() {
        return type;
    }
}
