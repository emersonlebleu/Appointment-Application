package model;

public class Customer {
    private int id;
    private String name;
    private String address;
    private String phone;
    private int division;

    public Customer(int id, String name, String address, String phone, int division){
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.division = division;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getDivision() {
        return division;
    }

    public void setDivision(int division) {
        this.division = division;
    }
}
