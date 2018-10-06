package com.scopefirst.domain;

import java.util.LinkedHashMap;
import java.util.Map;

/*
Created by Joharry Correa 15/09/2018
 */
public class TimeSelectHour {
    //private List<Integer> listHour = new ArrayList();

    private static Map<Integer, String> listHour = new LinkedHashMap<Integer, String>();

    /*
    public List<Integer> getListHour() {
        return listHour;
    }

    public void initializeList () {
        listHour = new ArrayList();
        listHour.add(1);
        listHour.add(2);
        listHour.add(3);
        listHour.add(4);
        listHour.add(9);
        listHour.add(10);
        listHour.add(11);
    }
    */

    public static Map<Integer, String> getListHour() {
        listHour = new LinkedHashMap<Integer, String>();
        listHour.put(1, "01");
        listHour.put(2, "02");
        listHour.put(3, "03");
        listHour.put(4, "04");
        listHour.put(9, "09");
        listHour.put(10, "10");
        listHour.put(11, "11");

        return listHour;
    }

    /*
    public void initializeList () {
        listHour = new LinkedHashMap<Integer, Integer>();
        listHour.put(1, 1);
        listHour.put(2, 2);
        listHour.put(3, 3);
        listHour.put(4, 4);
        listHour.put(9, 9);
        listHour.put(10, 10);
        listHour.put(11, 11);
    }

    */


}
