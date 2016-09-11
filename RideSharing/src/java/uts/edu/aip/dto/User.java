package uts.edu.aip.dto;

import java.io.Serializable;
import javax.validation.constraints.Size;

/**
 *
 * @author NQ
 * @version 1.0
 * This user class is used for both Driver and Passenger
 * this also has a constraint feature such as min/max of the characters and validation messages
 * 
 */
public class User implements Serializable{
    
    private int id;
    private String username;
    private String password;
    private String registrationDate;
    private String userType;
    private String firstName;
    private String lastName;
    private String phoneNo;

    public User() {
    }

    public User(int id, String username, String password, String registrationDate, String userType, String firstName, String lastName, String phoneNo) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.registrationDate = registrationDate;
        this.userType = userType;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNo = phoneNo;
    }
    
    /**
     * The id of the user which could be used for driver 
     * or passenger
     * @return an ID number
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * The username of the user which could be used both driver or passenger
     * @return non-null, a username string
     */
    @Size(min = 3, max = 20, message="The username must have at least 3 characters")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    /**
     * The password of the user 
     * @return non-null, a password string
     */
    @Size(min = 4, max = 20, message="The password must have at least 4 characters")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

     /**
     * 
     * The registration date which is automatically generated 
     * when the user registered on the system
     * @return non-null, a date string
     * 
     */
    @Size(min = 4, max = 20)
    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    /**
     * 
     * The user type (Driver or Passenger)
     * @return non-null, an user type string
     * 
     */
    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

     /**
     * 
     * The first name of the user (Driver or Passenger)
     * @return non-null, an user type string
     * 
     */
    @Size(min = 2, max = 20, message="The first name must have at least 2 characters")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    
    /**
     * 
     * The last name of the user (Driver or Passenger)
     * @return non-null, an user type string
     * 
     */
    @Size(min = 2, max = 20, message="The last name must have at least 2 characters")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * 
     * The phone number of the user (Driver or Passenger)
     * @return non-null, an user type string
     * 
     */
    @Size(min = 5, max = 20, message="The phone number must have at least 5 characters")
    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
