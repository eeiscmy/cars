/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cars.util;
/**
 *
 * @author seamus
 */

/*
* This class prints the given message on console.
*/

public class MessageUtil {
   private String message;

   // Constructor
   // @param message to be printed
   public MessageUtil(String message) {
      this.message = message;
   }

   // prints the message
   public String printMessage() {
      System.out.println(message);
      return message;
   }

   // add "Hi!" to the message
   public String salutationMessage() {
      message = "Hi!" + message;
      System.out.println(message);
      return message;
   }
}