package model;

import java.util.Date;

/** Appointment Class for appointment object. */
public class Appointment {
    private int id;
    private String title;
    private String description;
    private String location;
    private String type;
    private Date start;
    private Date end;
    private int customer;
    private String customerName;
    private int user;
    private String userName;
    private int contact;
    private String contactName;

    /** New appointment method. Generate new appointment. */
    public Appointment(int apptId, String title, String description, String location, String type, Date startDate, Date endDate, int customerId, int userId, int contactId){
        this.id = apptId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = startDate;
        this.end = endDate;
        this.customer = customerId;
        this.user = userId;
        this.contact = contactId;
    }

    /** Get the appointment id.
     * @return the appointment id of the object. */
    public int getId() {
        return id;
    }

    /** Set the id.
     * @param id the id to set this object to. */
    public void setId(int id){
        this.id = id;
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
    public Date getStart() {
        return start;
    }

    /** Set the startDate.
     * @param start the startDate to set. */
    public void setStart(Date start){
        this.start = start;
    }

    /** Get the appointment endDate.
     * @return the endDate of the object. */
    public Date getEnd() {
        return end;
    }

    /** Set the endDate.
     * @param end the endDate to set. */
    public void setEnd(Date end){
        this.end = end;
    }

    /** Get the customer id.
     * @return the customer id. */
    public int getCustomer() {
        return customer;
    }

    /** Set the customer id.
     * @param id the customer id to set. */
    public void setCustomer(int id){
        this.customer = id;
    }

    /** Get the user id.
     * @return the user id. */
    public int getUser() {
        return user;
    }

    /** Set the user id.
     * @param id the user id to set. */
    public void setUser(int id){
        this.user = id;
    }

    /** Get the contact id.
     * @return the contact id. */
    public int getContact() {
        return contact;
    }

    /** Set the contact id.
     * @param id the contact id to set. */
    public void setContact(int id){
        this.contact = id;
    }
}
