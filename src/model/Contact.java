package model;

/** Model contact class */
public class Contact {
    private int contactId;
    private String contactName;
    private String email;

    /** new contact instance method */
    public Contact(int contactId, String contactName, String email){
        this.contactId = contactId;
        this.contactName = contactName;
        this.email = email;
    }

    /** Get the id of the contact.
     * @return the id of the contact */
    public int getContactId() {
        return contactId;
    }
    /** Set the id of the contact.
     * @param contactId the id of the contact. */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
    /** Get the name of the contact.
     * @return the name of the contact */
    public String getContactName() {
        return contactName;
    }
    /** Set the name of the contact.
     * @param contactName the name of the contact. */
    public void setContactName(String contactName) {
        this.contactName = contactName;
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
