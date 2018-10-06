package com.scopefirst.services;

import com.scopefirst.domain.DataInput;
import com.scopefirst.domain.StructureFileInput;
import com.scopefirst.repositories.DataInputRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
Created by Joharry Correa 16/09/2018
 */
@Service
public class DataInputServiceImpl implements DataInputService {

    private final DataInputRepository dataInputRepository;

    public DataInputServiceImpl(DataInputRepository dataInputRepository) {
        this.dataInputRepository = dataInputRepository;
    }

    @Override
    public Set<DataInput> getDataInputAll() {

        Set<DataInput> dataInputSet = new HashSet<>();
        dataInputRepository.findAll().iterator().forEachRemaining(dataInputSet::add);
        System.out.println("count of record dataInput = " + dataInputSet.size());
        return dataInputSet;

    }

    @Override
    public DataInput findById(Long l) {
        return null;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void saveDataInput(List<StructureFileInput> listDataInput) {
        System.out.println("*** Saving data of the InputFile ***");
        for (StructureFileInput structureFileInput: listDataInput) {
            final DataInput dataInput = new DataInput();
            dataInput.setDescription(structureFileInput.getDescription());
            dataInput.setTimeActivity(structureFileInput.getTimeValue());
            dataInputRepository.save(dataInput);
        }
        System.out.println("*** Saved data of the InputFile 100% ***");

    }
}
