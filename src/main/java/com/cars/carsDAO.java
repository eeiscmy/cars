/*
 Database Access object
 */
package com.cars;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author seamus
 */


public class carsDAO {
    
    Car car;
    public List<Car> findAllCars() {
        
        List<Car> carlist = new ArrayList<Car>();
        
        Connection c = null;
        dbConnection conn = new dbConnection();
    	String sql = "SELECT * FROM car_list ORDER BY id";
        
        try {
            c = conn.getConnection();
            
            if ( c != null ) {
                
               Statement s = c.createStatement();
               ResultSet rs = s.executeQuery(sql);
               
               while (rs.next()) {                    
                   carlist.add(processRow(rs));// Call processRow method 
                                               // from this class
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
            
            closeConnections(c,conn);
        }
        
    return carlist;
    }
    
    
    
    public List<Car> findByName(String name) {
        
        List<Car> list = new ArrayList<Car>();
        Connection c = null;
        dbConnection conn = new dbConnection();
        
    	String sql = "SELECT * FROM car_list as e " +
			"WHERE UPPER(make) LIKE ? " +
                        "OR UPPER(model) LIKE ? "  +
			"ORDER BY make";
        try {
            c = conn.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, "%" + name.toUpperCase() + "%");
            ps.setString(2, "%" + name.toUpperCase() + "%");
            
            System.out.println(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                list.add(processRow(rs));
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
	} 
        finally {
            
	     try {
                if ( c != null ){
		   c.close();
                }
                if ( conn != null ) {
                   conn.close(c);
                }
             }
             catch (Exception e){
                e.printStackTrace();
             }
	}
        return list;
    }
    
    
    public List<Car> dropdownlist() {
        
        List<Car> list = new ArrayList<Car>();
        Connection c = null;
        dbConnection conn = new dbConnection();
        
    	String sql = "SELECT DISTINCT(make) FROM car_list " +
			"ORDER BY make";
        try {
            c = conn.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
        
            System.out.println(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
              //  list.add(rs.getString("make"));
                list.add(processRow2(rs));
                 System.out.println(rs.getString("make"));
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
	} 
        finally {
            
	     try {
                if ( c != null ){
		   c.close();
                }
                if ( conn != null ) {
                   conn.close(c);
                }
             }
             catch (Exception e){
                e.printStackTrace();
             }
	}
        return list;
    }
    
    
    public Car findById(int id) {
        
    	String sql = "SELECT * FROM car_list WHERE id = ?";
        Car car = null;
        Connection c = null;
        dbConnection conn = new dbConnection();
        
        try {
            c = conn.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                car = processRow(rs);
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
	} 
        finally {
            
            try {
                
               if ( c != null ){
		 c.close();
               }
              
               if ( conn != null ) {
                  conn.close(c);
               }
            }
            catch (Exception e){
               e.printStackTrace();
            }
	}
        return car;
    }

    
    public Car save(Car car) {
	return car.getId() > 0 ? update(car) : create(car);
    }    
   
    
    public Car create(Car car) {
        
        Connection c = null;
        
     //   PreparedStatement ps = null;
        try {
            
            dbConnection conn = new dbConnection();
            c = conn.getConnection();
            
            if ( car != null ) {
          
               String make = car.getMake();
               String model = car.getModel();
            
                if (make.isEmpty() && model.isEmpty()) {
                   System.out.println("Parameters are empty.Not putting empty values in database");
                }
                else {
                   PreparedStatement ps = c.prepareStatement("INSERT INTO car_list (make, model) VALUES (?, ?)",
                   new String[] { "id" });
        
                   ps.setString(1, make);
                   ps.setString(2, model);
        
                   ps.executeUpdate();
                   ResultSet rs = ps.getGeneratedKeys();
                   rs.next();
                   // Update the id in the returned object. This is important as 
                   //this value must be returned to 
                   //the client.
                   int id = rs.getInt(1);
                   car.setId(id);
           
                    // Update queue table.Provides notifications via SSE(Server
                   // Sent Events) to client browser.
                   
                   
                   //Clear old data first
                   //delete from car_list_queue WHERE update_time < (NOW() - INTERVAL 4 MINUTE)
                   PreparedStatement del = c.prepareStatement("delete from car_list_queue"
                           + " WHERE update_time < (NOW() - INTERVAL 4 MINUTE)");             
                    del.executeUpdate();
                   
                   
                   DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                   Calendar cal = Calendar.getInstance();
                   System.out.println(dateFormat.format(cal.getTime()));
                   // currentDate = dateFormat.format(cal.getTime());
                   PreparedStatement u = c.prepareStatement("INSERT INTO car_list_queue (id,make, model,update_type,row_count) VALUES (?,?,?,?,?)");
        
                    u.setInt(1, id);
                    //u.setString(2, dateFormat.format(cal.getTime()));
                    u.setString(2, make);
                    u.setString(3, model);
                    u.setString(4, "Add");
                    u.setInt(5, 20);
                    u.executeUpdate();
                }
            }
            else {
                System.out.println("Car object is null.");
            }          
        } 
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
	} 
        finally  {
			//dbConnection.close(c);
             try {
               if ( c != null ){
		    c.close();
                }     
              }
              catch (Exception e){
                 e.printStackTrace();
               }
	}
        return car;
    }

    public Car update(Car car) {
        
        Connection c = null;
        dbConnection conn = new dbConnection();
        
        try {
            
            c = conn.getConnection();
             
            PreparedStatement ps = c.prepareStatement("UPDATE car_list SET make=?, "
                    + "model=? WHERE id=?");
            ps.setString(1,car.getMake());
            ps.setString(2, car.getModel());
           
            ps.setInt(3, car.getId());
            ps.executeUpdate();
            
            
            
             // Update queue table
                   
                   //
                   //Clear old data first
                   //delete from car_list_queue WHERE update_time < (NOW() - INTERVAL 4 MINUTE)
                   PreparedStatement del = c.prepareStatement("delete from car_list_queue"
                           + " WHERE update_time < (NOW() - INTERVAL 4 MINUTE)");             
                    del.executeUpdate();
                   
                   
                   DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                   Calendar cal = Calendar.getInstance();
                   System.out.println(dateFormat.format(cal.getTime()));
                   // currentDate = dateFormat.format(cal.getTime());
                   PreparedStatement u = c.prepareStatement("INSERT INTO car_list_queue (id,make, model,update_type,row_count) VALUES (?,?,?,?,?)");
        
                    u.setInt(1,car.getId());
                    //u.setString(2, dateFormat.format(cal.getTime()));
                    u.setString(2, car.getMake());
                    u.setString(3, car.getModel());
                    u.setString(4, "Update");
                    u.setInt(5, 20);
                    u.executeUpdate();
     
         } 
        
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
	} 
        
        finally {
            
            try {
                
               if ( c != null ){
		     c.close();
               }
               
               if ( conn != null ) {
                        conn.close(c);
               }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        return car;
    }

    
    
    public boolean remove(int id) {
        Connection c = null;
        dbConnection conn = new dbConnection();
        
        try {
            c = conn.getConnection();
            PreparedStatement ps = c.prepareStatement("DELETE FROM car_list WHERE id=?");
            ps.setInt(1, id);
            int count = ps.executeUpdate();
            System.out.println("SQL delete count value:"+count);
            
            if (count == 1) {
             // Update queue table
               PreparedStatement del = c.prepareStatement("delete from car_list_queue WHERE update_time < (NOW() - INTERVAL 4 MINUTE)");             
               del.executeUpdate();
                
                
               DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
               Calendar cal = Calendar.getInstance();
               System.out.println(dateFormat.format(cal.getTime()));
               
               PreparedStatement u = c.prepareStatement("INSERT INTO car_list_queue (id,make, model,update_type,row_count) VALUES (?,?,?,?,?)");
                u.setInt(1, id);
                //u.setString(2, dateFormat.format(cal.getTime()));
                u.setString(2, "-");
                u.setString(3, "-");
                u.setString(4, "Delete");
                u.setInt(5, 20);
                u.executeUpdate();
            }           
            return count == 1;
            
        } 
        catch (Exception e) {
            
            e.printStackTrace();
            throw new RuntimeException(e);
	} 
        finally {
            
            try {
                
              if ( c != null ) {
		   c.close();
              }
              if ( conn != null ) {
                   conn.close(c);
              }
            }
            catch (Exception e){
                 e.printStackTrace();
            }
	}
    }
    
    protected Car processRow2(ResultSet rs) throws SQLException {
          
        //Create car object c
        Car c = new Car();
        // Get values from resultSet row and set them in the object.
        c.setId(0);
        c.setMake(rs.getString("make"));
        c.setModel(null);
        
    return c;
    }
 
    protected Car processRow(ResultSet rs) throws SQLException {
          
        //Create car object c
        Car c = new Car();
        // Get values from resultSet row and set them in the object.
        c.setId(rs.getInt("id"));
        c.setMake(rs.getString("make"));
        c.setModel(rs.getString("model"));
        
    return c;
    }
    
    
    private void closeConnections(Connection con, dbConnection conn) {
        
        try {
            if ( con != null ){
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
