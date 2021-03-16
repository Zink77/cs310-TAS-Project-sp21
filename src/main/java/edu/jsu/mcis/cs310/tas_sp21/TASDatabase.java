package edu.jsu.mcis.cs310.tas_sp21;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TASDatabase {

    Connection conn = null;
    PreparedStatement pstSelect = null, pstUpdate = null;
    ResultSet resultset = null;
    ResultSetMetaData metadata = null;

    public TASDatabase(){

        try {

            String server = ("jdbc:mysql://localhost/tas");
            String username = "tasuser";
            String password = "PrestigeWorldwideD";
            System.out.println("Connecting to " + server + "...");

            /* Load the MySQL JDBC Driver */

            Class.forName("com.mysql.jdbc.Driver").newInstance();

            /* Open Connection */

            conn = DriverManager.getConnection(server, username, password);
        }

        catch (Exception e) {
            System.err.println(e.toString());
        }

            /* Close Other Database Objects */

        finally {

                if (resultset != null) { try { resultset.close(); resultset = null; } catch (Exception e) {} }

                if (pstSelect != null) { try { pstSelect.close(); pstSelect = null; } catch (Exception e) {} }

                if (pstUpdate != null) { try { pstUpdate.close(); pstUpdate = null; } catch (Exception e) {} }

            }


    }
    public Badge getBadge(String badgeid){
        return null;
    }
    
    public Punch getPunch(int punchid){
        
        String query = "SELECT * FROM punch WHERE id=" + punchid;
        try {
            PreparedStatement pstSelect = conn.prepareStatement(query);
        } catch (SQLException ex) {
            Logger.getLogger(TASDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            ResultSet resultset = pstSelect.getResultSet();
        } catch (SQLException ex) {
            Logger.getLogger(TASDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        Badge someBadge = null;
        try {
            someBadge = getBadge(resultset.getString("badgeid"));
        } catch (SQLException ex) {
            Logger.getLogger(TASDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
        Punch userPunch = null;
        try {
            userPunch = new Punch(someBadge,resultset.getInt("terminalid"), resultset.getInt("punchtypeid"));
        } catch (SQLException ex) {
            Logger.getLogger(TASDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }


        
        return userPunch;
    }

    public Shift getShiftbyID(int shiftid){
        return null;
    }
    public Shift getShiftbyBadge(Badge badge){
        return null;
    }
}
