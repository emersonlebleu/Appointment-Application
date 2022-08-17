package model;

/** Model contact class */
public class Contact {
    private int id;
    private String name;
    private String email;

    /** new contact instance method */
    public Contact(int contactId, String contactName, String email){
        this.id = contactId;
        this.name = contactName;
        this.email = email;
    }

    /** Get the id of the contact.
     * @return the id of the contact */
    public int getId() {
        return id;
    }
    /** Set the id of the contact.
     * @param id the id of the contact. */
    public void setId(int id) {
        this.id = id;
    }
    /** Get the name of the contact.
     * @return the name of the contact */
    public String getName() {
        return name;
    }
    /** Set the name of the contact.
     * @param name the name of the contact. */
    public void setName(String name) {
        this.name = name;
    }
    /** Get the email of the contact.
     * @return the email of the contact */
    public String getEmail() {
        return email;
    }
    /** Set the email of the contact.
     * @param email the email of the contact. */
    public void setEmail(String email) {
        this.email = email;
    }
}
