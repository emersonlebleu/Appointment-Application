package model;

/** Class to hold information regarding country. */
public class Country {
    private int id;
    private String name;

    /** Constructor */
    public Country(int countryId, String countryName) {
        this.id = countryId;
        this.name = countryName;
    }
    /** Get the id of the country.
     * @return the id of the country */
    public int getId() {
        return id;
    }
    /** Set the id of the country.
     * @param id the id of the country. */
    public void setId(int id) {
        this.id = id;
    }
    /** Get the name of the country.
     * @return the name of the country */
    public String getName() {
        return name;
    }
    /** Set the name of the country.
     * @param name the name of the country. */
    public void setName(String name) {
        this.name = name;
    }
}
