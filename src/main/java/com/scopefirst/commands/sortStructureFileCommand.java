package com.scopefirst.commands;

import com.scopefirst.domain.StructureFileInput;

import java.util.Comparator;

/*
Created by Joharry Correa 19/09/2018
 */
public class sortStructureFileCommand implements Comparator<StructureFileInput> {
    private boolean asc;
    public sortStructureFileCommand(boolean asc) {
        this.asc = asc;
    }

    @Override
    public int compare(StructureFileInput o1, StructureFileInput o2) {
        int ret;
        if (asc) {
            ret = o1.getDescription().compareTo(o2.getDescription());
        } else {
            ret = o2.getDescription().compareTo(o1.getDescription());
        }
        return ret;
    }
}
