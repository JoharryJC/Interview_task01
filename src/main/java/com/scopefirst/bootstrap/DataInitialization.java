package com.scopefirst.bootstrap;

import com.scopefirst.commands.sortStructureFileCommand;
import com.scopefirst.domain.StructureFileInput;
import com.scopefirst.services.DataInputService;
import com.scopefirst.services.ReadTextFileService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
/*
Created by Joharry Correa 17/09/2018
 */
@Component
public class DataInitialization implements ApplicationListener<ContextRefreshedEvent>  {

    private final ReadTextFileService readTextFileService;
    private final DataInputService dataInputService;

    public DataInitialization(ReadTextFileService readTextFileService, DataInputService dataInputService) {
        this.readTextFileService = readTextFileService;
        this.dataInputService = dataInputService;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        List<StructureFileInput> listaDataInput = readTextFileService.readTextFile();
        if (listaDataInput == null) {
            System.out.println("There is nothing file to read");
            return;
        }

        Collections.sort(listaDataInput, new sortStructureFileCommand(true));
        dataInputService.saveDataInput(listaDataInput);
    }

}
