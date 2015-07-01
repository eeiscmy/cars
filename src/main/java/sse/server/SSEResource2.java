/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sse.server;

/**
 *
 * @author seamus
 */

import java.io.IOException;
import javax.inject.Singleton;
//import javax.jms.JMSException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.OutboundEvent;
import org.glassfish.jersey.media.sse.SseBroadcaster;
import org.glassfish.jersey.media.sse.SseFeature;
//import org.jms.consumer;

@Singleton
@Path("/broadcast2")

public class SSEResource2 {
    
    @GET
    @Produces(SseFeature.SERVER_SENT_EVENTS)
    public EventOutput getServerSentEvents() throws InterruptedException {
        final EventOutput eventOutput = new EventOutput();
      
                try {
                    
                        // ... code that waits 1 second
                        final OutboundEvent.Builder eventBuilder
                        = new OutboundEvent.Builder();
                        eventBuilder.name("message-to-client");
                        
                        String msg = "ddd";
                        System.out.println("Message received:" + msg );
                        
                        
                        
                        eventBuilder.data(String.class,
                            "Message Received " + msg + "!");
                        final OutboundEvent event = eventBuilder.build();
                       
                        eventOutput.write(event);
                    }
                 catch (IOException e) {
                    throw new RuntimeException(
                        "Error when writing the event.", e);
                } finally {
                    try {
                        eventOutput.close();
                    } catch (IOException ioClose) {
                        throw new RuntimeException(
                            "Error when closing the event output.", ioClose);
                    }
                }
            
        return eventOutput;
    }
}
