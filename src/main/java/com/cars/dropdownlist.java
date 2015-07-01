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



import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/makes")
public class dropdownlist {
    
     carsDAO dao = new carsDAO();
    
        @GET
        @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
        //@Produces("text/html")
       // @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
        public List<Car> getMakes() {
	      //System.out.println("Get all cars for dropdown list.");
	      return dao.dropdownlist();
        }
    
}
