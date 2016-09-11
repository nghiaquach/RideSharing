package uts.edu.aip.dto;

import java.io.Serializable;
import javax.validation.constraints.Size;

/**
 * @author NQ
 * @version 1.0
 *
 * This class is used to store Vehicle information such as ID,
 * Model and the image of the vehicle
 * 
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
    
     /**
     * The id of the vehicle
     * @return non-null, a vehicle id number
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * The model of the vehicle
     * @return non-null, a model string
     */
    @Size(min = 2, max = 20, message="Please enter the model of your vehicle")
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    /**
     * The image/photo of the vehicle
     * @return non-null, a file name of the image which is located on the server folder
     */
    @Size(min = 2, max = 20, message="Please select a photo of your vehicle")
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
