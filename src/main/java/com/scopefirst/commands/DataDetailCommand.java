package com.scopefirst.commands;

import com.scopefirst.domain.DataGridObject;
import com.scopefirst.domain.DataInput;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Set;

/*
Created by Joharry Correa 16/09/2018
 */
@Getter
@Setter
@NoArgsConstructor
public class DataDetailCommand {

    private Long id;

    private Long trackId;

    private Long dataInputId;

    private String sessionPeriod;

    private String hourInit;

    private String hourFin;

    private Date dateCreated;

    private String timeSelectHour;

    private String timeSelectMinute;

    private Set<DataInput> listActivities;

    private List<DataGridObject> myTableMorning;

    private List<DataGridObject> myTableAfternoon;

    private String messageError;

}
