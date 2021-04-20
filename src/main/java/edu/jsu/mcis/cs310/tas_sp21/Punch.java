package edu.jsu.mcis.cs310.tas_sp21;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Date;




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

    public String getBadgeid() {
        return BadgeID;
    }

    public int getTerminalid() {
        return terminalID;
    }

    public long getOriginaltimestamp() {
        return originalTime;
    }

    public long getAdjustedTime() {
        return adjustedTime;
    }

    public String getAdjustmentType() {
        return adjustmentType;
    }

    public int getPunchtypeid() {
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
    
   public void adjust (Shift s) {
             
       
        //Convert time ranges to Longs for comparisons
        long interval = s.getInterval() * 60000;
        long dock = s.getDock() * 60000;
        long grace = s.getGraceperiod() * 60000;
        
       //creates original calendar
        GregorianCalendar oc = new GregorianCalendar();
        oc.setTimeInMillis(this.getOriginaltimestamp());
        oc.clear(GregorianCalendar.SECOND);
        Long punchTime = oc.getTimeInMillis();
        
        //Shifts
        GregorianCalendar startC = (GregorianCalendar) oc.clone();
        startC.set(GregorianCalendar.HOUR_OF_DAY, s.getStart().getHour());
        startC.set(GregorianCalendar.MINUTE, s.getStart().getMinute());
        Long shiftStart = startC.getTimeInMillis();
        
        GregorianCalendar stopC = (GregorianCalendar) oc.clone();
        stopC.set(GregorianCalendar.HOUR_OF_DAY, s.getStop().getHour());
        stopC.set(GregorianCalendar.MINUTE, s.getStop().getMinute());
        Long shiftStop = stopC.getTimeInMillis();
        
        //Lunches
        GregorianCalendar lunchStartC = (GregorianCalendar) oc.clone();
        lunchStartC.set(GregorianCalendar.HOUR_OF_DAY, s.getLunchstart().getHour());
        lunchStartC.set(GregorianCalendar.MINUTE, s.getLunchstart().getMinute());
        Long lunchStart = lunchStartC.getTimeInMillis();
       
        GregorianCalendar lunchStopC = (GregorianCalendar) oc.clone();
        lunchStopC.set(GregorianCalendar.HOUR_OF_DAY, s.getLunchstop().getHour());
        lunchStopC.set(GregorianCalendar.MINUTE, s.getLunchstop().getMinute());
        Long lunchStop = lunchStopC.getTimeInMillis();
        
          if ((oc.get(GregorianCalendar.DAY_OF_WEEK) != GregorianCalendar.SATURDAY) && (oc.get(GregorianCalendar.DAY_OF_WEEK)  != GregorianCalendar.SUNDAY)) {
          
             switch (this.getPunchtypeid()) {
                 
                 //CLOCK INS
                 case 1:
                     
                     //Early clock, within interval
                     if ((punchTime <= shiftStart) && (punchTime >= shiftStart - interval)) {
                        this.setAdjustedTime(shiftStart);
                        this.setAdjustmentType("Shift Start");
                    }
                     
                     //Late clock in, within grace 
                    else if ((punchTime <= shiftStart + grace) && (punchTime >= shiftStart)) {
                         this.setAdjustedTime(shiftStart);
                         this.setAdjustmentType("Shift Start");
                     }
                     
                     //Late clock in, not within grace
                    else if ((punchTime >= shiftStart + grace) && (punchTime <= shiftStart + interval)) {
                        this.setAdjustedTime(shiftStart + dock);
                        this.setAdjustmentType("Shift Dock");
                    }
                     
                     //Lunch
                    else if ((punchTime >= lunchStart) && (punchTime <= lunchStop)) {
                        this.setAdjustedTime(lunchStop);
                        this.setAdjustmentType("Lunch Stop");
                    }
                     
                     //Other cases                    
                    else if (punchTime % interval != 0){ 
                        punchIntervalAdjust(interval, punchTime);
                    }
                    
                    else {
                        
                        this.setAdjustedTime(punchTime);
                        this.setAdjustmentType("None");
                    }
            break; 
                
            //CLOCK OUTS
            case 0:
                
                //Early clock out, in grace
                if ((punchTime >= shiftStop - grace) && (punchTime <= shiftStop)){
                    this.setAdjustedTime(shiftStop);
                    this.setAdjustmentType("Shift Stop");
                }
                
                //Late clock out, not in grace
                else if((punchTime >= shiftStop - dock) && (punchTime <= shiftStop -grace)) {
                    this.setAdjustedTime(shiftStop - dock);
                    this.setAdjustmentType("Shift Dock");
                }
                
                //Late clock out, no dock
                else if ((punchTime <= shiftStop + interval) && (punchTime >= shiftStop)) {
                    this.setAdjustedTime(shiftStop);
                    this.setAdjustmentType("Shift Stop");
                }
                
                //lunch
                else if ((punchTime >= lunchStart) && (punchTime <= lunchStop)) {
                    this.setAdjustedTime(lunchStart);
                    this.setAdjustmentType("Lunch Start");                    
                }
                
                //Other cases
                else if (punchTime % interval != 0){
                    punchIntervalAdjust(interval, punchTime);
                }
                
                else {
                        
                        this.setAdjustedTime(punchTime);
                        this.setAdjustmentType("None");
                    }
            break; 
              
                    }
                     
             }
          else if (punchTime % interval != 0){
                    punchIntervalAdjust(interval, punchTime);
                }
                
                else {
                        
                        this.setAdjustedTime(punchTime);
                        this.setAdjustmentType("None");
                    }
          }
                
 
    
     public void punchIntervalAdjust(long interval, long punchtime) {
         if (punchtime % interval != 0){
                        
                        if ((interval / 2) > (this.getOriginaltimestamp() % interval)){
                            punchtime = Math.round((long)punchtime/(interval) ) * (interval);
                        } 
                        
                        else {
                            punchtime = Math.round((long)(punchtime + interval)/(interval) ) * (interval);
                        }
                        
                        this.setAdjustedTime(punchtime);
                        this.setAdjustmentType("Interval Round");
                    } 
                    
                    //No changes 
                    else {                   
                        this.setAdjustedTime(punchtime); 
                        this.setAdjustmentType("None");                                           
                    }
         
     }
       
   
    
    public String printOriginalTimestamp() {
        
        StringBuilder string = new StringBuilder();
        
        GregorianCalendar thisCalendar = new GregorianCalendar();
        thisCalendar.setTimeInMillis(this.originalTime);
        
        Date thisDate = thisCalendar.getTime();
        
        SimpleDateFormat formatter = new SimpleDateFormat("E MM/dd/yyyy HH:mm:ss");
        
        
        if (this.getPunchtypeid() == 1){
            string.append("#" + this.getBadgeid() + " CLOCKED IN: " + formatter.format(thisDate).toUpperCase());
        }
        
        else if (this.getPunchtypeid() == 0){
            string.append("#" + this.getBadgeid() + " CLOCKED OUT: " + formatter.format(thisDate).toUpperCase());
        }
        
        else{
            string.append("#" + this.getBadgeid() + " TIMED OUT: " + formatter.format(thisDate).toUpperCase());
        }
        
        return string.toString();
    }
    
    public String printAdjustedTimestamp(){
    
        StringBuilder s = new StringBuilder();
        
        GregorianCalendar thisCalendar = new GregorianCalendar();
        thisCalendar.setTimeInMillis(this.originalTime);
        
        Date thisDate =  new Date(this.adjustedTime);
        
        SimpleDateFormat formatter = new SimpleDateFormat("E MM/dd/yyyy HH:mm:ss");
        
        
        if (this.getPunchtypeid() == 1){
            s.append("#" + this.getBadgeid() + " CLOCKED IN: " + formatter.format(thisDate).toUpperCase() + " (" + (this.getAdjustmentType()) + ")");
        }
        
        else if (this.getPunchtypeid() == 0){
            s.append("#" + this.getBadgeid() + " CLOCKED OUT: " + formatter.format(thisDate).toUpperCase() + " (" + (this.getAdjustmentType()) + ")");
        }
        
        else{
            s.append("#" + this.getBadgeid() + " TIMED OUT: " + formatter.format(thisDate).toUpperCase() + " (" + (this.getAdjustmentType()) + ")");
        }
        
        return s.toString();
        
    }
    
   
     
}
