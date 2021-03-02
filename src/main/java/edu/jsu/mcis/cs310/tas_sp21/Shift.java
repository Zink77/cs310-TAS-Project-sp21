package edu.jsu.mcis.cs310.tas_sp21;

import java.time.LocalTime;

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

    public Shift(int id, String description, LocalTime start, LocalTime stop, int interval, int graceperiod, int dock, LocalTime lunchstop, int lunchdeduct) 
    {
        this.id = id;
        this.description = description;
        this.start = start;
        this.stop = stop;
        this.interval = interval;
        this.graceperiod = graceperiod;
        this.dock = dock;
        this.lunchstop = lunchstop;
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
        s.append(" (").append(interval).append("); ").append("Lunch: ").append(lunchstart);
        s.append(" - ").append(lunchstop).append(" (" ).append(lunchdeduct).append(")");
        
        return ( s.toString() );
    }
    
}
