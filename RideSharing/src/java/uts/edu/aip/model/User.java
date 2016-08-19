/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.edu.aip.model;

/**
 *
 * @author NQ
 */
public class User {
    
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
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
