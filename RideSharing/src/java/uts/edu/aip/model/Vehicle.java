/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.edu.aip.model;

import java.io.Serializable;
import javax.validation.constraints.Size;

/**
 *
 * @author NQ
 */
public class Vehicle implements Serializable{
    private int id;
    private String model;
    private String image;

    public Vehicle() {
    }

    public Vehicle(int id, String model, String image) {
        this.id = id;
        this.model = model;
        this.image = image;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @Size(min = 2, max = 20, message="Please enter the model of your vehicle")
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Size(min = 2, max = 20, message="Please select a photo of your vehicle")
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
