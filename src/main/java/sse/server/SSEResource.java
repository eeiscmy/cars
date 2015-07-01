/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author seamus
 */

package sse.server;

import com.cars.dbConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Singleton;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.OutboundEvent;
import org.glassfish.jersey.media.sse.SseFeature;
import org.json.JSONException;
import org.json.JSONObject;
  
@Singleton
@Path("/broadcast")

public class SSEResource {
    
    ResultSet rs = null;
    static ResultSet rs_prev = null;
    
   @GET
   @Produces(SseFeature.SERVER_SENT_EVENTS)
   public EventOutput getServerSentEvents() {
        
        final EventOutput eventOutput = new EventOutput();
        new Thread(new Runnable() {
            @Override
            public void run() {
                
                
                try {
                    
                        // ... code that waits 1 second
                        final OutboundEvent.Builder eventBuilder
                        = new OutboundEvent.Builder();
                        
             
                        
          Connection c = null;
          dbConnection conn = new dbConnection();
    	  String sql = "select * from car_list_queue WHERE update_time > (NOW() - INTERVAL 20 SECOND)";
        
          Boolean found = false;
          
          try {
              c = conn.getConnection(); 
              if ( c != null ) {
                
               Statement s = c.createStatement();
               rs = s.executeQuery(sql);
               
 
               
                while (rs.next()) {                    
                   //carupdates.add(processRow(rs));// Call processRow method 
                                               // from this class
                   System.out.println("Database:" + rs.getInt("id"));
                   int id = rs.getInt("id");
                   String id_str = rs.getString("id");
                   String make = rs.getString("make");
                   String model = rs.getString("model");
                   String update_type = rs.getString("update_type");
                   int row_count = rs.getInt("row_count");
                  
                   ArrayList arr = new ArrayList();
                  
                   //Put into json format
                   JSONObject obj = new JSONObject();
                   try {
                       obj.put("id" ,id);
                       obj.put("make",make);
                       obj.put("model",model);
                   
                   } catch (JSONException ex) {
                       Logger.getLogger(SSEResource.class.getName()).log(Level.SEVERE, null, ex);
                   }
                  
                   // Construct a sting in json format.
                   String msg = "{\"model\":\""+model+"\",\"id\":\""+id+"\",\"make\":"
                      +      "\""+make+"\",\"update_type\":\""+update_type+"\",\"row_count\":\""+row_count+"\"}";
                   
                    System.out.println("Sending event.."); 
                    System.out.println(msg);
                   
                    eventBuilder.name(update_type);
                    eventBuilder.data(String.class, msg);
                    eventBuilder.id(id_str);
                    final OutboundEvent event = eventBuilder.build();
                    eventOutput.write(event);
                
                }
             }
             else {
                    System.out.println("Connection is null!!!");
             }
            
        } 
        
        catch (SQLException e) {
            
            e.printStackTrace();
            throw new RuntimeException(e);
            
	}
        
        finally {
            // close connection in finallyblock so it always closes
            // regardless of an exception or not.
            closeConnections(c,conn);
        }             
     }       
               
        catch (IOException e) {
             throw new RuntimeException("Error when writing the event.", e);
         } 
       finally {
                    
                try {    
                    eventOutput.close();
                } 
                
                catch (IOException ioClose) {
                    throw new RuntimeException(
                             "Error when closing the event output.", ioClose);
                }
                        
                        
                try {
                     
                     Thread.sleep(5000);
                      //System.out.println(Thread.currentThread());
                } 
                    
                catch (InterruptedException ex) {
                        System.out.print("Interrupt Exception" + ex.getStackTrace() );
                       // Logger.getLogger(SSEResource.class.getName()).log(Level.SEVERE, null, ex);
                }
                 
            }
                
        }

        private Object events(int i) {
              throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }     
    }).start();
        return eventOutput;
    }
     
    private void closeConnections(Connection con, dbConnection conn){
      
        try {
              if ( con != null ) {
                con.close();
               }
              
              if ( conn != null ) {
                conn.close(con);
              }
        }
        catch (Exception e) {
             e.printStackTrace();
        }
    }  
}
