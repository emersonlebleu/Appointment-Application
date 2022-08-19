package model;

/** Class to hold information regarding user. */
public class User {
    private int id;
    private String name;
    private String password;
    /** Constructor */
    public User(int id, String name, String password){
        this.id = id;
        this.name = name;
        this.password = password;
    }
    /** Get the id of the user.
     * @return the id of the user */
    public int getId() {
        return id;
    }
    /** Set the id of the user.
     * @param id the id of the user. */
    public void setId(int id) {
        this.id = id;
    }
    /** Get the name of the user.
     * @return the name of the user */
    public String getName() {
        return name;
    }
    /** Set the name of the user.
     * @param name the name of the user. */
    public void setName(String name) {
        this.name = name;
    }
    /** Get the password of the user.
     * @return the password of the user */
    public String getPassword() {
        return password;
    }
    /** Set the password of the user.
     * @param password the password of the user. */
    public void setPassword(String password) {
        this.password = password;
    }

    /** Override toString in order to get appropriate display on combo boxes */
    @Override
    public String toString(){
        return (id + "-" + name);
    }
}
