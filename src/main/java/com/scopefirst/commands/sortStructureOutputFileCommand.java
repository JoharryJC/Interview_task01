package com.scopefirst.commands;

import com.scopefirst.domain.DataGridObject;
import java.util.Comparator;

/*
Created by Joharry Correa 19/09/2018
 */
public class sortStructureOutputFileCommand implements Comparator<DataGridObject> {
    private boolean asc;
    private boolean grupo;

    public sortStructureOutputFileCommand(boolean asc, boolean grupo) {
        this.asc = asc;
        this.grupo = grupo;
    }

    @Override
    public int compare(DataGridObject o1, DataGridObject o2) {
        int ret;
        String turno1 = ((o1.getTurno().equals("Morning"))?"A":"B");
        String turno2 = ((o2.getTurno().equals("Morning"))?"A":"B");

        if (grupo == true) {
            if (asc) {
                ret = (o1.getId() + " " + turno1 + " " + o1.getHourInit() + " " + o1.getDescription()).compareTo((o2.getId() + " " + turno2 + " " + o2.getHourInit() + " " + o2.getDescription()));
            } else {
                ret = (o2.getId() + " " + turno2 + " " + o2.getHourInit() + " " + o2.getDescription()).compareTo((o1.getId() + " " + turno1 + " " + o1.getHourInit() + " " + o1.getDescription()));
            }
        }
        else {
            if (asc) {
                ret = (turno1 + " " + o1.getHourInit() + " " + o1.getDescription()).compareTo((turno2 + " " + o2.getHourInit() + " " + o2.getDescription()));
            } else {
                ret = (turno2 + " " + o2.getHourInit() + " " + o2.getDescription()).compareTo((turno1 + " " + o1.getHourInit() + " " + o1.getDescription()));
            }
        }
        return ret;
    }

}