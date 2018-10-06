package com.scopefirst.services;

import com.scopefirst.domain.DataGridObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

@Service
public class WriteTextFileServiceImpl implements WriteTextFileService {

    private final String fileDirectory;

    public WriteTextFileServiceImpl(@Value("${fileDirectory.write}") String fileDirectory) {
        this.fileDirectory = fileDirectory;
    }

    @Override
    public String writeTextFile(List<DataGridObject> listDataGridObjects) {
        FileWriter fichero = null;
        PrintWriter pw = null;
        String valMensaje = null;

        try
        {
            if ((listDataGridObjects == null) || (listDataGridObjects.size() == 0)){
                return valMensaje;
            }

            Date dayHour = new Date();
            String dateN =  Integer.valueOf(dayHour.getYear()).toString() + "_" + Integer.valueOf(dayHour.getMonth()).toString() + "_" +
                            Integer.valueOf(dayHour.getDay()).toString() + "_" + Integer.valueOf(dayHour.getHours()).toString() + "_" +
                            Integer.valueOf(dayHour.getMinutes()).toString();

            fichero = new FileWriter(fileDirectory + "_" + dateN + ".txt");
            pw = new PrintWriter(fichero);

            String title = "Track " + listDataGridObjects.get(0).getId() + ":";
            pw.println(title);
            String turne = listDataGridObjects.get(0).getTurno();

            for (DataGridObject dataGridObject: listDataGridObjects ) {
                String titleTemp = "Track " + dataGridObject.getId() + ":";
                String turneTemp = dataGridObject.getTurno();

                /*
                //afternoon
                if ((!title.equals(titleTemp))&&(turne.equals("Afternoon"))) {
                    pw.println("05:00PM Meet Your Colleagues Event");
                }

                //lunch
                if ((title.equals(titleTemp))&&(!turne.equals(turneTemp))) {
                    turne = titleTemp;
                    pw.println("12:00PM Lunch");
                }
                */

                if (!title.equals(titleTemp)) {
                    title = titleTemp;
                    turne = titleTemp;
                    pw.println("");
                    pw.println(title);
                }

                String prefi = ((dataGridObject.getTurno().equals("Morning"))?"AM": "PM");
                if (dataGridObject.getHourInit().equals("12:00")) {
                    prefi = "PM";
                }
                String line = dataGridObject.getHourInit() + prefi + " " + dataGridObject.getDescription();
                pw.println(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fichero)
                    fichero.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        return valMensaje;
    }

}


