/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sse.server;

import java.io.IOException;
import org.glassfish.jersey.media.sse.OutboundEvent;

import java.util.ArrayList;
import java.util.Date;
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

import java.util.Date;
import javax.jms.*;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

  
@Singleton
@Path("/broadcast3")

public class AMQResource {
    
  private static final String url = ActiveMQConnection.DEFAULT_BROKER_URL;
  ConnectionFactory connectionFactory;
  Connection connection = null;
    
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
                        
               System.out.println("------------" + new Date());
               
               
               
               
               
               
                    eventBuilder.name("test");
                    eventBuilder.data(String.class, "msg");
                    eventBuilder.id("id_str");
                    final OutboundEvent event = eventBuilder.build();
                    try {
                        eventOutput.write(event);
                    } catch (IOException ex) {
                        Logger.getLogger(AMQResource.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                
                finally {
                    
                }
                try {
                    eventOutput.close();
                } catch (IOException ex) {
                    Logger.getLogger(AMQResource.class.getName()).log(Level.SEVERE, null, ex);
                }
            
           }
   
    }).start();
        return eventOutput;
    }
     
    
}