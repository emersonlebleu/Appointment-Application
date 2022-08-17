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
    private String customerName;
    private int userId;
    private String userName;
    private int contactId;
    private String contactName;

    /** New appointment method. Generate new appointment. */
    public Appointment(int apptId, String title, String description, String location, String type, Date startDate, Date endDate, int customerId, int userId, int contactId){
        this.apptId = apptId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.customerId = customerId;
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

    /** Get the appointment location.
     * @return the location of the object. */
    public String getLocation() {
        return location;
    }

    /** Set the location.
     * @param location the location to set. */
    public void setLocation(String location){
        this.location = location;
    }

    /** Get the appointment type.
     * @return the type of the object. */
    public String getType() {
        return type;
    }

    /** Set the type.
     * @param type the type to set. */
    public void setType(String type){
        this.type = type;
    }

    /** Get the appointment startDate.
     * @return the startDate of the object. */
    public Date getStartDate() {
        return startDate;
    }

    /** Set the startDate.
     * @param startDate the startDate to set. */
    public void setStartDate(Date startDate){
        this.startDate = startDate;
    }

    /** Get the appointment endDate.
     * @return the endDate of the object. */
    public Date getEndDate() {
        return endDate;
    }

    /** Set the endDate.
     * @param endDate the endDate to set. */
    public void setEndDate(Date endDate){
        this.endDate = endDate;
    }

    /** Get the customer id.
     * @return the customer id. */
    public int getCustomerId() {
        return customerId;
    }

    /** Set the customer id.
     * @param id the customer id to set. */
    public void setCustomerId(int id){
        this.customerId = id;
    }

    /** Get the user id.
     * @return the user id. */
    public int getUserId() {
        return userId;
    }

    /** Set the user id.
     * @param id the user id to set. */
    public void setUserId(int id){
        this.userId = id;
    }

    /** Get the contact id.
     * @return the contact id. */
    public int getContactId() {
        return contactId;
    }

    /** Set the contact id.
     * @param id the contact id to set. */
    public void setContactId(int id){
        this.contactId = id;
    }
}
