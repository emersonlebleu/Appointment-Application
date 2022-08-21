package model;

import java.security.PublicKey;
/** Class to hold information regarding division. */
public class Division {
    private int id;
    private String name;
    private int country;
    /** Constructor */
    public Division(int id, String name, int country){
        this.id = id;
        this.name = name;
        this.country = country;
    }
    /** Get the id of the division.
     * @return the id of the division */
    public int getId() {
        return id;
    }
    /** Set the id of the division.
     * @param id the id of the division. */
    public void setId(int id) {
        this.id = id;
    }
    /** Get the name of the division.
     * @return the name of the division */
    public String getName() {
        return name;
    }
    /** Set the name of the division.
     * @param name the name of the division. */
    public void setName(String name) {
        this.name = name;
    }
    /** Get the country of the division.
     * @return the country of the division */
    public int getCountry() {
        return country;
    }
    /** Set the country of the division.
     * @param country the country of the division. */
    public void setCountry(int country) {
        this.country = country;
    }

    /** Override toString in order to get appropriate display on combo boxes */
    @Override
    public String toString(){
        return (name);
    }
}
