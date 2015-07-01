/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cars;


import java.sql.*;

/**
 *
 * @author seamus
 */
public class dbConnection {
 
     Connection con = null;
     
     public Connection getConnection() {
     
      try {
          
         Class.forName("com.mysql.jdbc.Driver") ;
     
         if ( con == null ) {
            con = DriverManager
		.getConnection("jdbc:mysql://localhost:3306/cars","root", "");
         }
         //else {
         //    System.out.println("Connection already established.No need to initiate a new one.");
         //}

         } 
      
      catch (ClassNotFoundException | SQLException e) {
         System.err.println("Exception: "+e.getMessage());
      }
    return con;
  } 
  
     
  public void close(Connection con) {
      
     try {
         
	if (con != null) {
            con.close();
            //System.out.println("Closing connection.");
	}
                
      } 
      catch (SQLException e) {
	 e.printStackTrace();
      }
   }    
}
