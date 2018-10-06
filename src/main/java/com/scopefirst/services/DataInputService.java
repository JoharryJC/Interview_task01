package com.scopefirst.services;

import com.scopefirst.domain.DataInput;
import com.scopefirst.domain.StructureFileInput;

import java.util.List;
import java.util.Set;

/*
Created by Joharry Correa 16/09/2018
 */
public interface DataInputService {

    Set<DataInput> getDataInputAll();

    DataInput findById(Long l);

    void deleteAll();

    void saveDataInput(List<StructureFileInput> listDataInput);

}
