/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cars;

/**
 *
 * @author seamus
 */

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class carModel {
    
   
    private String model;

  
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    
}
