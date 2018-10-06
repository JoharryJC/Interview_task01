package com.scopefirst.domain;

import java.util.LinkedHashMap;
import java.util.Map;

/*
Created by Joharry Correa 15/09/2018
 */
public class TimeSelectMinute {

    //private List<Integer> listMinute = new ArrayList();

    private static Map<Integer, String> listMinute;

    public static Map<Integer, String> getListMinute() {
        listMinute = new LinkedHashMap<Integer, String>();
        listMinute.put(0, "00");
        listMinute.put(5, "05");
        for (Integer i=10; i<=55; i=(i+5)) {
            listMinute.put(i, i.toString());
        }

        return listMinute;
    }

    /*
    public List<Integer> getListMinute() {
        return listMinute;
    }

    public void initializeList () {
        listMinute = new ArrayList();
        for (int i=5; i<=55; i=(i+5)) {
            listMinute.add(5);
        }
    }

    public void initializeList () {
        listMinute = new LinkedHashMap<Integer, Integer>();
        for (int i=5; i<=55; i=(i+5)) {
            listMinute.put(i, i);
        }
    }

    */



}
