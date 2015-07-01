
package com.cars;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;

/**
 *
 * @author seamus
 */

@Path("/cars")
public class CarResource {
    
   carsDAO dao = new carsDAO();
//    @Inject
//    carsDAO dao;
   
        @GET
        @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
        public List<Car> getCars() {
	      System.out.println("Get all cars from database.");
	      return dao.findAllCars();
        }
        
        
        
        @GET 
        @Path("search/{query}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Car> findByName(@PathParam("query") String query) {
		System.out.println("findByName: " + query);
		return dao.findByName(query);
	}

        
       
        
	@GET 
        @Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Car findById(@PathParam("id") String id) {
		System.out.println("findById " + id);
                int ID = Integer.parseInt(id);
		return dao.findById(ID);
                
	}
        
        
        @POST
        //Specify typeof MIME we can consume from the client. Here JSON and XML.
	@Consumes({ MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML})
        //used to specify the MIME media types or representations a resource 
        //can produce and send back to the client.
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Car create(String json_str) {
         //Pass Json string into a json object
           JsonObject jsonobj = Json.createReader(new StringReader(json_str)).readObject();
           
           Car car = new Car();
           // Set car make and model in car oblect
           car.setMake(jsonobj.getString("make"));
           car.setModel(jsonobj.getString("model"));
          //Pass car to data access object (access database) and return.
           return dao.create(car);
	}

	@PUT 
        @Path("{id}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Car update(Car car) {
		System.out.println("Updating car: " + car.getMake());
		dao.update(car);
		return car;
	}
	
	@DELETE 
        @Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void remove(@PathParam("id") int id) {
		dao.remove(id);
	}
         
}
