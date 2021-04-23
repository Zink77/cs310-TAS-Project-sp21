package edu.jsu.mcis.cs310.tas_sp21;

import java.util.*;
import java.time.LocalTime;
import java.time.temporal.*;


public class Shift 
{
    private int id;
    private String description;
    private LocalTime start;
    private LocalTime stop;
    private int interval;
    private int graceperiod;
    private int dock;
    private LocalTime lunchstart;
    private LocalTime lunchstop;
    private int lunchdeduct; 
    
    private int lunchduration;

    public Shift(int id, String description, String start, String stop, int interval, int graceperiod, int dock, String lunchstart, String lunchstop, int lunchdeduct) 
    {
        this.id = id;
        this.description = description;
        this.start = LocalTime.parse(start);
        this.stop = LocalTime.parse(stop);
        this.interval = interval;
        this.graceperiod = graceperiod;
        this.dock = dock;
        this.lunchstart = LocalTime.parse(lunchstart);
        this.lunchstop = LocalTime.parse(lunchstop);
        
        GregorianCalendar stopCalendar = new GregorianCalendar();
        GregorianCalendar startCalendar = new GregorianCalendar();
        
        stopCalendar.set(0, 0, 0, this.lunchstop.getHour(), this.lunchstop.getMinute(), this.lunchstop.getSecond());
        startCalendar.set(0, 0, 0, this.lunchstart.getHour(), this.lunchstart.getMinute(), this.lunchstart.getSecond());
        this.lunchduration = (int) (stopCalendar.getTimeInMillis() - startCalendar.getTimeInMillis());
        this.lunchduration = this.lunchduration/60000;
        this.lunchdeduct = lunchdeduct;
    }
    

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getStop() {
        return stop;
    }

    public int getInterval() {
        return interval;
    }

    public int getGraceperiod() {
        return graceperiod;
    }

    public int getDock() {
        return dock;
    }

    public LocalTime getLunchstart() {
        return lunchstart;
    }

    public LocalTime getLunchstop() {
        return lunchstop;
    }

    public int getLunchdeduct() {
        return lunchdeduct;
    }

    public int getLunchduration() {
        return lunchduration;
    }
    
    @Override 
    public String toString() {
        //"Shift 1: 07:00 - 15:30 (510 minutes); Lunch: 12:00 - 12:30 (30 minutes)"
        StringBuilder s = new StringBuilder();
        
        s.append(description).append(": ").append(start).append(" - ").append(stop);
        s.append(" (").append(start.until(stop, ChronoUnit.MINUTES)).append(" minutes);"); 
        s.append(" Lunch: ").append(lunchstart).append(" - ").append(lunchstop);
        s.append(" (" ).append(lunchstart.until(lunchstop, ChronoUnit.MINUTES)).append(" minutes)");
        
        return ( s.toString() );
    }
    
}
