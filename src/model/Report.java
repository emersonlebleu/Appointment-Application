package model;


public class Report {
    private Integer month;
    private String type;
    private Integer numberAppt;

    /** Report constructor instance if there is a month type and number */
    public Report(Integer month, String type, Integer numberAppt){
        this.month = month;
        this.type = type;
        this.numberAppt = numberAppt;
    }
    /** Report constructor instance if there is a month and number */
    public Report(Integer month, Integer numberAppt){
        this.month = month;
        this.numberAppt = numberAppt;
    }
    /** Report constructor instance if there is a type and number */
    public Report(String type, Integer numberAppt){
        this.type = type;
        this.numberAppt = numberAppt;
    }
    /** Months returned from DB are numerical. When the controller accesses a month for the row in report they get the
     * corresponding alphabetical representation. */
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
    /** Used by reports to get the number of appointments.
     * @return the number of appointments for that report. */
    public Integer getNumberAppt() {
        return numberAppt;
    }
    /** Used by reports controller to get the type.
     * @return the type of appointment for that report row. */
    public String getType() {
        return type;
    }
}
