
package com.cars;

/**
 *
 * @author seamus
 */
//change
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Car {
    
    private int id;
    private String make;
    private String model;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    
}
