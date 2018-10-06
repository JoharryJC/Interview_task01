package com.scopefirst.services;

import com.scopefirst.commands.DataDetailCommand;
import com.scopefirst.domain.DataDetail;
import com.scopefirst.domain.DataGridObject;

import java.util.List;
import java.util.Set;

/*
Created by Joharry Correa 16/09/2018
 */
public interface DataDetailService {

    Set<DataDetail> getDataDetail();

    DataDetail findById(Long l);

    void deleteAll(Long track);

    void deleteById(Long l);

    DataDetailCommand saveDataDetail(DataDetailCommand command);

    List<DataGridObject> getMyTable(Long l, String sessionValue);

    List<DataGridObject> listDataGridObjects();
}
