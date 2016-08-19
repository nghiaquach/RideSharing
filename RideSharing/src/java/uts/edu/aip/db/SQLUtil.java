/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.edu.aip.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author NQ
 */
public class SQLUtil {
   
   private static SQLUtil instance = null;
   private Connection conn = null;
   
   private SQLUtil() {
      // Exists only to defeat instantiation.
   }
   public static SQLUtil getInstance() {
      if(instance == null) {
         instance = new SQLUtil();
      }
      return instance;
   }
    
    public Connection getConnection() throws SQLException{
        
        if(conn == null){
            String dbURL = "jdbc:derby://localhost:1527/RideSharing";
            Properties properties = new Properties();
            properties.put("user", "uts");
            properties.put("password", "utsadmin");
            
            conn = DriverManager.getConnection(dbURL, properties);
        }
        return conn;
    }
}
