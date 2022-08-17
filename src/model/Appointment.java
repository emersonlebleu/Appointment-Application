package model;

import java.util.Date;

/** Appointment Class for appointment object. */
public class Appointment {
    private int apptId;
    private String title;
    private String description;
    private String location;
    private String type;
    private Date startDate;
    private Date endDate;
    private int customerId;
    private int userId;
    private int contactId;

    /** New appointment method. Generate new appointment. */
    public Appointment(int apptId, String title, String description, String location, String type, Date startDate, Date endDate, int customerId, int userId, int contactId){
        this.apptId = apptId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this. customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    /** Get the appointment id.
     * @return the appointment id of the object. */
    public int getApptId() {
        return apptId;
    }

    /** Set the id.
     * @param id the id to set this object to. */
    public void setApptId(int id){
        this.apptId = id;
    }

    /** Get the appointment title.
     * @return the appointment title of the object. */
    public String getTitle() {
        return title;
    }

    /** Set the title.
     * @param title the title to set this object to. */
    public void setTitle(String title){
        this.title = title;
    }

    /** Get the appointment description.
     * @return the description of the object. */
    public String getDescription() {
        return description;
    }

    /** Set the description.
     * @param description the description to set. */
    public void setDescription(String description){
        this.description = description;
    }


}
