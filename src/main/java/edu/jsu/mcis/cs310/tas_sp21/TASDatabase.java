package edu.jsu.mcis.cs310.tas_sp21;

import org.json.simple.*;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.time.LocalTime;


public class TASDatabase {
    JSONObject badgesData = new JSONObject();
    JSONObject punchData = new JSONObject();
    Connection conn = null;
    PreparedStatement pstSelect = null, pstUpdate = null;
    ResultSet resultset = null;
    ResultSetMetaData metadata = null;

    public TASDatabase(){

        String query;
        boolean hasresults;
        int resultCount;

        try {

            String server = ("jdbc:mysql://localhost/tas");
            String username = "tasuser";
            String password = "PrestigeWorldwideD";
            System.out.println("Connecting to " + server + "...");

            /* Load the MySQL JDBC Driver */

            Class.forName("com.mysql.jdbc.Driver").newInstance();

            /* Open Connection */

            conn = DriverManager.getConnection(server, username, password);

            query = "SELECT * FROM badge";
            pstSelect = conn.prepareStatement(query);
            hasresults = pstSelect.execute();

            while ( hasresults || pstSelect.getUpdateCount() != -1 )
            {
                if (hasresults)
                {

                    resultset = pstSelect.getResultSet();

                    while (resultset.next())
                    {
                        Badge currentEmployee;
                        currentEmployee = new Badge(resultset.getString(1), resultset.getString(2));
                        this.badgesData.put(resultset.getString(1), currentEmployee);
                    }
                    
                    hasresults = !hasresults;
                }
                else
                    {
                    resultCount = pstSelect.getUpdateCount();
                    if (resultCount == -1) {
                        break;
                    }
                }
            }
            
            query = "SELECT * FROM punch";
            pstSelect = conn.prepareStatement(query);
            hasresults = pstSelect.execute();
            
            
            while ( hasresults || pstSelect.getUpdateCount() != -1 )
            {
                if (hasresults)
                {

                    resultset = pstSelect.getResultSet();

                    while (resultset.next())
                    {
                        Punch currentPunch;
                        Badge passBadge = new Badge(resultset.getString(3), null);
                        
                        currentPunch = new Punch(passBadge, resultset.getInt(2), resultset.getInt(5));
                        currentPunch.setOriginalTime(resultset.getLong(4));
                        this.punchData.put(resultset.getInt(1), currentPunch);
                    }
                    
                    hasresults = !hasresults;
                    
                }
                else
                    {
                    resultCount = pstSelect.getUpdateCount();
                    if (resultCount == -1) {
                        break;
                    }
                }
            }
            
            
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
            
        
       
 
    
    public Punch getPunch(int punchid){
        
        String query = "SELECT * FROM punch WHERE id=";
        query+= punchid;
        //System.out.println(query);
        
        
        try {
            pstSelect = conn.prepareStatement(query);
            ResultSet result = pstSelect.executeQuery();
            
            result.next();


            Badge someBadge = new Badge(result.getString("badgeid"), "");
            
            Punch userPunch = new Punch(someBadge,result.getInt("terminalid"), result.getInt("punchtypeid"));

            Timestamp converterTime = Timestamp.valueOf(result.getString("originaltimestamp"));
            userPunch.setOriginalTime(converterTime.getTime());
            System.out.println(userPunch.printOriginalTimestamp());
            return userPunch;
            
        } catch (Exception e) {
            System.err.println(e.toString());
            return null;
        }
        
    }

    
    public Shift getShift(int shiftid){ 
    try {
        pstSelect = conn.prepareStatement("SELECT * FROM shift WHERE id = ?");
        pstSelect.setInt(1, shiftid);
        ResultSet result = pstSelect.executeQuery();
        
        result.next();
        Shift shift  = new Shift (result.getInt(1), result.getString(2), result.getString(3), result.getString(4), result.getInt(5), result.getInt(6), result.getInt(7), result.getString(8), result.getString(9), result.getInt(10));
        
        return shift;
        
    } catch (Exception e) {
            System.err.println(e.toString());
            return null;
        }
        
        
    }
    
    
 public Badge getBadge(String badgeNumber)
    {
       Badge returningBadge = (Badge)this.badgesData.get(badgeNumber);
       return returningBadge;
    }
           
    
    
 public Shift getShift(Badge badge){
     try{          
            
            PreparedStatement pstSelect = conn.prepareStatement("SELECT * FROM shift INNER JOIN employee"
                    + " ON employee.shiftid = shift.id INNER JOIN badge ON badge.id"
                    + " = employee.badgeid WHERE badge.id = ?");       
            
            pstSelect.setString( 1, badge.getId());
            ResultSet result = pstSelect.executeQuery();
            result.next();
              
            Shift shift  = new Shift (result.getInt(1), result.getString(2), result.getString(3), result.getString(4), result.getInt(5), result.getInt(6), result.getInt(7), result.getString(8), result.getString(9), result.getInt(10));
        
            return shift;
        
        } catch (Exception e) {
            System.err.println(e.toString());
            return null;
        }
         
}

/*
public int insertPunch(Punch p){


            int id = 
            String badgeID = p.getBadgeid();
            int terminalID = p.getTerminalid();
            long originalTime = p.getOriginaltimestamp();
            int punchTypeID = p.getPunchtypeid();
            
            
            
}
*/
 
  
}
