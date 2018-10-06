package com.scopefirst.domain;

/*
Created by Joharry Correa 17/09/2018
 */
public class DataGridObject {

    private String id;
    private String turno;
    private String hourInit;
    private String hourFin;
    private String description;
    private String dataInputId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getHourInit() {
        return hourInit;
    }

    public void setHourInit(String hourInit) {
        this.hourInit = hourInit;
    }

    public String getHourFin() {
        return hourFin;
    }

    public void setHourFin(String hourFin) {
        this.hourFin = hourFin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDataInputId() {
        return dataInputId;
    }

    public void setDataInputId(String dataInputId) {
        this.dataInputId = dataInputId;
    }
}
