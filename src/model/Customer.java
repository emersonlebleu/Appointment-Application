package model;

/** Class to hold information regarding customer. */
public class Customer {
    private int id;
    private String name;
    private String address;
    private String postalCode;
    private String phone;
    private int division;

    /** Constructor */
    public Customer(int id, String name, String address, String postalCode, String phone, int division){
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.division = division;
    }
    /** Get the id of the customer.
     * @return the id of the customer */
    public int getId() {
        return id;
    }
    /** Set the id of the customer.
     * @param id the id of the customer. */
    public void setId(int id) {
        this.id = id;
    }
    /** Get the name of the customer.
     * @return the name of the customer */
    public String getName() {
        return name;
    }
    /** Set the name of the customer.
     * @param name the name of the customer. */
    public void setName(String name) {
        this.name = name;
    }
    /** Get the address of the customer.
     * @return the address of the customer */
    public String getAddress() {
        return address;
    }
    /** Set the address of the customer.
     * @param address the address of the customer. */
    public void setAddress(String address) {
        this.address = address;
    }
    /** Get the postalCode of the customer.
     * @return the postalCode of the customer */
    public String getPostalCode() {
        return postalCode;
    }
    /** Set the postalCode of the customer.
     * @param postalCode the address of the customer. */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    /** Get the phone of the customer.
     * @return the phone of the customer */
    public String getPhone() {
        return phone;
    }
    /** Set the phone of the customer.
     * @param phone the phone of the customer. */
    public void setPhone(String phone) {
        this.phone = phone;
    }
    /** Get the division of the customer.
     * @return the division of the customer */
    public int getDivision() {
        return division;
    }
    /** Set the division of the customer.
     * @param division the division of the customer. */
    public void setDivision(int division) {
        this.division = division;
    }
}
