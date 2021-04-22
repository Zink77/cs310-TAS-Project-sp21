/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.jsu.mcis.cs310.tas_sp21;

import java.util.ArrayList;

/**
 *
 * @author steph
 */
public class TASLogic {

    public static int calculateTotalMinutes(ArrayList<Punch> dailypunchlist, Shift shift){

        int totalMinutes = 0;
        int punchCounter = 0;
        long clockInTime = 0;
        long clockOutTime = 0;

        for (int i = 0; i < dailypunchlist.size(); i++){

            /* CLOCK IN */
            if (dailypunchlist.get(i).getPunchtypeid() == 1){

                clockInTime = dailypunchlist.get(i).getAdjustedTime();
                punchCounter++;
            }

            /* CLOCK OUT */
            if (dailypunchlist.get(i).getPunchtypeid() == 0){

                clockOutTime = dailypunchlist.get(i).getAdjustedTime();
                punchCounter++;
            }

            if (clockInTime != 0 && clockOutTime != 0){

                totalMinutes = (int) ((clockOutTime - clockInTime)/60);
            }

            if (punchCounter == 2)
                totalMinutes -= shift.getLunchduration();
        }

        return totalMinutes;
    }

}
