/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.edu.aip.utilities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author NQ
 */
public class AppUtil {

    private static AppUtil instance = null;
    private Connection conn = null;

    private AppUtil() {
        // Exists only to defeat instantiation.
    }

    public static AppUtil getInstance() {
        if (instance == null) {
            instance = new AppUtil();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
            try {
                //            String dbURL = "jdbc:derby://localhost:1527/RideSharing";
//            Properties properties = new Properties();
//            properties.put("user", "uts");
//            properties.put("password", "utsadmin");
                DataSource ds = (DataSource) InitialContext.doLookup("jdbc/ridesharing");
                conn = ds.getConnection();
            } catch (NamingException ex) {
                Logger.getLogger(AppUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        return conn;
    }
    
    public void showError(String message) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage("myForm:appMessage", new FacesMessage(message));
    }
    
//    public void log(Object obj,String msg){
//        Logger log = Logger.getLogger(obj.getClass().getName());
//        log.log(Level.INFO, "{0} - {1}", new Object[]{msg, new Date().toString()});
//    }

    public String getStringDate() {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        // Get the date today using Calendar object.
        Date today = Calendar.getInstance().getTime();
        // Using DateFormat format method we can create a string 
        // representation of a date with the defined format.
        return df.format(today);
    }
    
    public boolean isValidTime(String time){
        String[] parts = time.split(":");
        Calendar cal1 = Calendar.getInstance();
        cal1.set(Calendar.HOUR_OF_DAY, Integer.parseInt(parts[0]));
        cal1.set(Calendar.MINUTE, Integer.parseInt(parts[1]));
        
        Date today = Calendar.getInstance().getTime();
        
        System.out.println("uts.edu.aip.controllers.RideController.isValidTime()" + today.toString());
        System.out.println("uts.edu.aip.controllers.RideController.isValidTime()" + cal1.getTime().toString());
        
        return today.getTime() <= cal1.getTime().getTime();
    }
    
    public String getStringDateByFormat(String format) {
        DateFormat df = new SimpleDateFormat(format);
        // Get the date today using Calendar object.
        Date today = Calendar.getInstance().getTime();
        // Using DateFormat format method we can create a string 
        // representation of a date with the defined format.
        return df.format(today);
    }

    public static String hash256(String data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(data.getBytes());
        return bytesToHex(md.digest());
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuffer result = new StringBuffer();
        for (byte byt : bytes) {
            result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
        }
        return result.toString();
    }

//    public static void main(String[] args) {
//        System.out.println(AppUtil.getInstance().getStringDate());
//    }
}
