package model;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;
import utilities.CurrentSession;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/** Appointment Class for appointment object. */
public class Appointment {
    private Integer id;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime start;
    private ZonedDateTime startFormat;
    private LocalDateTime end;
    private ZonedDateTime endFormat;
    private Integer customer;
    private Integer user;
    private Integer contact;

    /** New appointment method. Generate new appointment. */
    public Appointment(Integer apptId, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, Integer customerId, Integer userId, Integer contactId){
        this.id = apptId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.startFormat = start.atZone(CurrentSession.getZone());
        this.end = end;
        this.endFormat = end.atZone(CurrentSession.getZone());
        this.customer = customerId;
        this.user = userId;
        this.contact = contactId;

    }
    public Appointment(String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, Integer customerId, Integer userId, Integer contactId){
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.startFormat = start.atZone(CurrentSession.getZone());
        this.end = end;
        this.endFormat = end.atZone(CurrentSession.getZone());
        this.customer = customerId;
        this.user = userId;
        this.contact = contactId;
    }

    /** Get the appointment id.
     * @return the appointment id of the object. */
    public Integer getId() {
        return id;
    }

    /** Set the id.
     * @param id the id to set this object to. */
    public void setId(Integer id){
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
    public LocalDateTime getStart() {
        return start;
    }

    /** Set the startDate.
     * @param start the startDate to set. */
    public void setStart(LocalDateTime start){
        this.start = start;
    }

    /** Get the appointment endDate.
     * @return the endDate of the object. */
    public LocalDateTime getEnd() {
        return end;
    }

    /** Set the endDate.
     * @param end the endDate to set. */
    public void setEnd(LocalDateTime end){
        this.end = end;
    }

    /** Get the customer id.
     * @return the customer id. */
    public Integer getCustomer() {
        return customer;
    }

    /** Set the customer id.
     * @param id the customer id to set. */
    public void setCustomer(Integer id){
        this.customer = id;
    }

    /** Get the user id.
     * @return the user id. */
    public Integer getUser() {
        return user;
    }

    /** Set the user id.
     * @param id the user id to set. */
    public void setUser(Integer id){
        this.user = id;
    }

    /** Get the contact id.
     * @return the contact id. */
    public Integer getContact() {
        return contact;
    }

    /** Set the contact id.
     * @param id the contact id to set. */
    public void setContact(Integer id){
        this.contact = id;
    }

    public String getStartFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm zzz");
        return startFormat.format(formatter);
    }

    public String getEndFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm zzz");
        return endFormat.format(formatter);
    }

}
