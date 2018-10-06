package com.scopefirst.services;

import com.scopefirst.domain.StructureFileInput;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReadTextFileServiceImpl implements ReadTextFileService {

    private final String fileDirectory;

    public ReadTextFileServiceImpl(@Value("${fileDirectory.read}") String fileDirectory) {
        this.fileDirectory = fileDirectory;
    }

    @Override
    public List<StructureFileInput> readTextFile() {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        List<StructureFileInput> lista = new ArrayList <StructureFileInput>();

        try {

            //String directoryFile = ("${fileDirectory.read}");
            archivo = new File (fileDirectory);
            fr = new FileReader (archivo);
            br = new BufferedReader(fr);

            String linea;
            while((linea = br.readLine())!= null) {
                if (linea.trim().equals("")) {
                    continue;
                }
                System.out.println(linea);
                StructureFileInput structureFileInput = new StructureFileInput();
                String [] cadena = linea.split(" ");
                String description = "";
                String timeActivity = cadena[cadena.length - 1].toString().trim().replace("min", "");
                for (int i = 0; i< cadena.length - 1; i++){
                    description += cadena[i] + " ";
                }

                System.out.print("description = " + description.trim());
                System.out.println(" - timeActivity = " + timeActivity.trim() + " min.");

                try {
                    //structureFileInput.setDescription(description.trim());
                    structureFileInput.setDescription(linea.trim());
                    structureFileInput.setTimeValue(Integer.valueOf(timeActivity.trim()));
                    lista.add(structureFileInput);
                }
                catch (java.lang.NumberFormatException ex) {
                    System.out.println("There is one activity without time. This is an error! I will process the others.");
                }
            }

            if (lista.size() == 0) {
                lista = null;
            }
        }
        catch (FileNotFoundException ex) {
            System.out.println("*** Error - Not exist input file or the input directory ***");
            fr = null;
            lista = null;
        }
        catch(Exception e){
            lista = null;
            e.printStackTrace();
        }
        finally{
            try{
                if( null != fr ){
                    fr.close();
                }
            }catch (Exception e2){
                lista = null;
                e2.printStackTrace();
            }
        }

        return lista;
    }

}
