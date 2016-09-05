/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.edu.aip.model;

import java.io.Serializable;

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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
