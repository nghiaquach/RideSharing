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
public class Vehicle {
    private int id;
    private String model;
    private byte[] image;

    public Vehicle() {
    }

    public Vehicle(int id, String model, byte[] image) {
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
