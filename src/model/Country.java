package model;

/** Class to hold information regarding country. */
public class Country {
    private int countryId;
    private String countryName;

    /** Constructor */
    public Country(int countryId, String countryName) {
        this.countryId = countryId;
        this.countryName = countryName;
    }
    /** Get the id of the country.
     * @return the id of the country */
    public int getCountryId() {
        return countryId;
    }
    /** Set the id of the country.
     * @param countryId the id of the country. */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }
    /** Get the name of the country.
     * @return the name of the country */
    public String getCountryName() {
        return countryName;
    }
    /** Set the name of the country.
     * @param countryName the name of the country. */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
