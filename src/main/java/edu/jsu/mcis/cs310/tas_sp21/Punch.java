package edu.jsu.mcis.cs310.tas_sp21;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;


/*
*
*  @author Payton
*
*/

public class Punch {
    private int id;
    private String BadgeID;
    private int terminalID;
    private long originalTime;
    private long adjustedTime;
    private String adjustmentType;
    private int punchTypeID;
    
    public Punch(Badge badge, int terminalid, int punchtypeid){
        this.BadgeID = badge.getId();
        this.terminalID = terminalid;
        this.punchTypeID = punchtypeid;
        
        adjustmentType = null;
        this.id = 0;
        this.originalTime = 0;
        this.adjustedTime = 0;
    }

    public int getId() {
        return id;
    }

    public String getBadgeID() {
        return BadgeID;
    }

    public int getTerminalID() {
        return terminalID;
    }

    public long getOriginalTime() {
        return originalTime;
    }

    public long getAdjustedTime() {
        return adjustedTime;
    }

    public String getAdjustmentType() {
        return adjustmentType;
    }

    public int getPunchTypeID() {
        return punchTypeID;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBadgeID(String BadgeID) {
        this.BadgeID = BadgeID;
    }

    public void setTerminalID(int terminalID) {
        this.terminalID = terminalID;
    }

    public void setOriginalTime(long originalTime) {
        this.originalTime = (originalTime);
    }

    public void setAdjustedTime(long adjustedTime) {
        this.adjustedTime = adjustedTime;
    }

    public void setAdjustmentType(String adjustmentType) {
        this.adjustmentType = adjustmentType;
    }

    public void setPunchTypeID(int punchTypeID) {
        this.punchTypeID = punchTypeID;
    }
    
    public String printOriginalTimestamp() {
        
        StringBuilder string = new StringBuilder();
        
        GregorianCalendar thisCalendar = new GregorianCalendar();
        thisCalendar.setTimeInMillis(this.originalTime);
        
        Date thisDate = thisCalendar.getTime();
        
        SimpleDateFormat formatter = new SimpleDateFormat("E MM/dd/yyyy HH:mm:ss");
        
        
        if (this.getPunchTypeID() == 1){
            string.append("#" + this.getBadgeID() + " CLOCKED IN: " + formatter.format(thisDate).toUpperCase());
        }
        
        else if (this.getPunchTypeID() == 0){
            string.append("#" + this.getBadgeID() + " CLOCKED OUT: " + formatter.format(thisDate).toUpperCase());
        }
        
        else{
            string.append("#" + this.getBadgeID() + " TIMED OUT: " + formatter.format(thisDate).toUpperCase());
        }
        
        return string.toString();
    }
    
}
