package org.fraunhofer.test;

//STEP 1. Import required packages
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.*;
import java.sql.*;


public class ConnectionPostgres {
Connection c = null;


public static Connection dbConnector() {
     
     try {
        Class.forName("org.postgresql.Driver");
        Connection c = DriverManager
           .getConnection("jdbc:postgresql://127.0.0.1:5432/sensorthings",
           "sensorthings", "1");
        return c;
        
     } catch (Exception e) {
       // e.printStackTrace();
        //System.err.println(e.getClass().getName()+": "+e.getMessage());
        //System.exit(0);
      JOptionPane.showMessageDialog(null, e);
      return null;
     }
     //System.out.println("Opened database successfully");
  }
}