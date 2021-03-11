package edu.jsu.mcis.cs310.tas_sp21;

import java.sql.*;


public class TASDatabase {

    Connection conn = null;
    PreparedStatement pstSelect = null, pstUpdate = null;
    ResultSet resultset = null;
    ResultSetMetaData metadata = null;

    public TASDatabase(){

        try {

            String server = ("jdbc:mysql://localhost/p2_test");
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
    public Punch getPunch(int punchid){
        return null;
    }
    public Badge getBadge(String badgeid){
        return null;
    }
    public Shift getShiftbyID(int shiftid){
        return null;
    }
    public Shift getShiftbyBadge(Badge badge){
        return null;
    }
}
