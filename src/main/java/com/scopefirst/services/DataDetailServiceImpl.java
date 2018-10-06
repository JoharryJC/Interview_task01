package com.scopefirst.services;

import com.scopefirst.commands.DataDetailCommand;
import com.scopefirst.commands.sortStructureOutputFileCommand;
import com.scopefirst.converts.DataDetailCommandToDataDetail;
import com.scopefirst.domain.DataDetail;
import com.scopefirst.domain.DataGridObject;
import com.scopefirst.repositories.DataDetailRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/*
Created by Joharry Correa 16/09/2018
 */
@Slf4j
@Service
public class DataDetailServiceImpl implements DataDetailService  {

    private final DataDetailRepository dataDetailRepository;
    private final DataDetailCommandToDataDetail dataDetailCommandToDataDetail;

    public DataDetailServiceImpl(DataDetailRepository dataDetailRepository, DataDetailCommandToDataDetail dataDetailCommandToDataDetail) {
        this.dataDetailRepository = dataDetailRepository;
        this.dataDetailCommandToDataDetail = dataDetailCommandToDataDetail;
    }

    @Override
    public Set<DataDetail> getDataDetail() {
        Set<DataDetail> dataDetailSet = new HashSet<>();

       Iterator<DataDetail> iteraDataDetail = dataDetailRepository.findAll().iterator();
       while (iteraDataDetail.hasNext()) {
           DataDetail tempDatadetail = iteraDataDetail.next();
           dataDetailSet.add(tempDatadetail);
       }

       return dataDetailSet;
    }

    @Override
    public DataDetail findById(Long l) {
        return null;
    }

    @Override
    public void deleteAll(Long track) {
        Set<DataDetail> dataDetailSet = getDataDetail();
        List<Long> valores = new ArrayList<>();
        for (DataDetail datatemp: dataDetailSet) {
            if (track == datatemp.getTrackObject().getId()) {
                valores.add(datatemp.getId());
            }
        }
        if (valores.size() > 0) {
            for (Long va: valores) {
                dataDetailRepository.deleteById(va);
            }
        }
    }

    @Override
    public void deleteById(Long l) {
        dataDetailRepository.deleteById(l);
    }

    @Override
    public List<DataGridObject> listDataGridObjects() {
        Set<DataDetail> dataDetailSet = getDataDetail();
        List<DataGridObject> listDataGridObjects = new ArrayList<>();
        List<DataGridObject> listDataGridObjects2 = new ArrayList<>();

        if ( (dataDetailSet == null) || (dataDetailSet.size() == 0) ) {
            return null;
        }

        DataGridObject dataGridObject = null;
        for (DataDetail dataDett: dataDetailSet) {
            dataGridObject = new DataGridObject();
            dataGridObject.setId(dataDett.getTrackObject().getId().toString());
            dataGridObject.setTurno(dataDett.getSessionPeriod().toString());
            dataGridObject.setHourInit(dataDett.getHourInit().toString());
            dataGridObject.setHourFin(dataDett.getHourFin().toString());
            dataGridObject.setDescription(dataDett.getDataInput().getDescription());
            listDataGridObjects.add(dataGridObject);
        }

        Collections.sort(listDataGridObjects, new sortStructureOutputFileCommand(true, true));

        String tracki = listDataGridObjects.get(0).getId();
        boolean foundg = false;
        for (int i = 0; i < listDataGridObjects.size(); i++) {
            DataGridObject dataGridObjectt = listDataGridObjects.get(i);
            listDataGridObjects2.add(dataGridObjectt);
            if (!tracki.equals(dataGridObjectt.getId())) {
                DataGridObject dataGridObjectTemp = listDataGridObjects.get(i-1);
                //if (dataGridObjectTemp.getTurno().equals("Afternoon")) {
                    dataGridObject = new DataGridObject();
                    dataGridObject.setId(dataGridObjectTemp.getId());
                    dataGridObject.setTurno("Afternoon");

                    String HoraInitEven1 = dataDetailCommandToDataDetail.getNewHour(dataGridObjectTemp.getHourFin(), 5);

                    try {
                        if (dataGridObjectTemp.getTurno().equals("Morning")) {
                            HoraInitEven1 = "04:00";
                        }
                        else {
                            DateFormat dateFormato = new SimpleDateFormat("HH:mm");
                            Date horaNewFin = dateFormato.parse(HoraInitEven1);

                            if (horaNewFin.getTime() > (dateFormato.parse("05:00")).getTime()) {
                                HoraInitEven1 = "05:00";
                            }
                            if (horaNewFin.getTime() < (dateFormato.parse("04:00")).getTime()) {
                                HoraInitEven1 = "04:00";
                            }
                        }
                    }
                    catch (Exception ex) {
                    }

                    dataGridObject.setHourInit(HoraInitEven1);
                    dataGridObject.setHourFin(HoraInitEven1);
                    dataGridObject.setDescription("Meet Your Colleagues Event");
                    listDataGridObjects2.add(dataGridObject);
                //}

                dataGridObject = new DataGridObject();
                dataGridObject.setId(dataGridObjectTemp.getId());
                dataGridObject.setTurno("Morning");
                dataGridObject.setHourInit("12:00");
                dataGridObject.setHourFin("01:00");
                dataGridObject.setDescription("Lunch");
                listDataGridObjects2.add(dataGridObject);

                tracki = dataGridObjectt.getId();
                foundg = true;
            }
        }

        Collections.sort(listDataGridObjects2, new sortStructureOutputFileCommand(true, true));

        /*
        if (    (listDataGridObjects2.get(listDataGridObjects2.size()-1).getTurno().equals("Afternoon")) &&
                (!listDataGridObjects2.get(listDataGridObjects2.size()-1).getDescription().equals("Meet Your Colleagues Event") )
                )
        {
         */

       // if ( (!foundg == true)   )
       // {
            DataGridObject dataGridObjectp = listDataGridObjects2.get(listDataGridObjects2.size()-1);
            dataGridObject = new DataGridObject();
            dataGridObject.setId(dataGridObjectp.getId());
            //dataGridObject.setHourInit("05:00");

            String HoraInitE1 = dataDetailCommandToDataDetail.getNewHour(dataGridObjectp.getHourFin(), 5);

            try {
                if (dataGridObjectp.getTurno().equals("Morning")) {
                    HoraInitE1 = "04:00";
                }
                else {
                    DateFormat dateFormato = new SimpleDateFormat("HH:mm");
                    Date horaNewFin = dateFormato.parse(HoraInitE1);

                    if (horaNewFin.getTime() < (dateFormato.parse("04:00")).getTime()) {
                        HoraInitE1 = "04:00";
                    }

                    if (horaNewFin.getTime() > (dateFormato.parse("05:00")).getTime()) {
                        HoraInitE1 = "05:00";
                    }
                }
            }
            catch (Exception ex) {    }

            dataGridObject.setHourInit(HoraInitE1);
            dataGridObject.setHourFin(HoraInitE1);

            //dataGridObject.setHourFin(dataGridObjectp.getHourFin());
            dataGridObject.setTurno("Afternoon");
            dataGridObject.setDescription("Meet Your Colleagues Event");
            listDataGridObjects2.add(dataGridObject);

            dataGridObject = new DataGridObject();
            dataGridObject.setId(dataGridObjectp.getId());
            dataGridObject.setTurno("Morning");
            dataGridObject.setHourInit("12:00");
            dataGridObject.setHourFin("01:00");
            dataGridObject.setDescription("Lunch");
            listDataGridObjects2.add(dataGridObject);
       // }

        Collections.sort(listDataGridObjects2, new sortStructureOutputFileCommand(true, true));

        return listDataGridObjects2;
    }

    /*
    @Override
    public List<DataGridObject> listDataGridObjects() {
        Set<DataDetail> dataDetailSet = getDataDetail();
        List<DataGridObject> listDataGridObjects = new ArrayList<>();
        List<DataGridObject> listDataGridObjects2 = new ArrayList<>();

        if ( (dataDetailSet == null) || (dataDetailSet.size() == 0) ) {
            return null;
        }

        DataGridObject dataGridObject = null;
        for (DataDetail dataDett: dataDetailSet) {
            dataGridObject = new DataGridObject();
            dataGridObject.setId(dataDett.getTrackObject().getId().toString());
            dataGridObject.setTurno(dataDett.getSessionPeriod().toString());
            dataGridObject.setHourInit(dataDett.getHourInit().toString());
            dataGridObject.setHourFin(dataDett.getHourFin().toString());
            dataGridObject.setDescription(dataDett.getDataInput().getDescription());
            listDataGridObjects.add(dataGridObject);
        }

        Collections.sort(listDataGridObjects, new sortStructureOutputFileCommand(true, true));
        String tracki = listDataGridObjects.get(0).getId();
        boolean foundg = false;
        for (int i = 0; i < listDataGridObjects.size(); i++) {
            DataGridObject dataGridObjectt = listDataGridObjects.get(i);
            listDataGridObjects2.add(dataGridObjectt);
            if (!tracki.equals(dataGridObjectt.getId())) {
                DataGridObject dataGridObjectTemp = listDataGridObjects.get(i-1);
                if (dataGridObjectTemp.getTurno().equals("Afternoon")) {
                    dataGridObject = new DataGridObject();
                    dataGridObject.setId(dataGridObjectTemp.getId());
                    dataGridObject.setTurno("Afternoon");
                    //cambiar esto
                    dataGridObject.setHourInit("05:00");
                    dataGridObject.setHourFin(dataGridObjectTemp.getHourFin());
                    dataGridObject.setDescription("Meet Your Colleagues Event");
                    listDataGridObjects2.add(dataGridObject);
                }
                tracki = dataGridObjectt.getId();
                foundg = true;
            }
        }

        Collections.sort(listDataGridObjects2, new sortStructureOutputFileCommand(true, true));

        if (    (listDataGridObjects2.get(listDataGridObjects2.size()-1).getTurno().equals("Afternoon")) &&
            (!listDataGridObjects2.get(listDataGridObjects2.size()-1).getDescription().equals("Meet Your Colleagues Event") )
            )
    {
        DataGridObject dataGridObjectp = listDataGridObjects2.get(listDataGridObjects2.size()-1);
        dataGridObject = new DataGridObject();
        dataGridObject.setId(dataGridObjectp.getId());
        dataGridObject.setHourInit("05:00");
        dataGridObject.setTurno("Afternoon");
        dataGridObject.setHourFin(dataGridObjectp.getHourFin());
        dataGridObject.setDescription("Meet Your Colleagues Event");
        listDataGridObjects2.add(dataGridObject);
    }
        Collections.sort(listDataGridObjects2, new sortStructureOutputFileCommand(true, true));
        return listDataGridObjects2;
}
     */

    @Override
    //@Transactional
    public DataDetailCommand saveDataDetail(DataDetailCommand command) {
        System.out.println("saveDataDetailCommand. starting convert");
        DataDetail detachedDataDetail = dataDetailCommandToDataDetail.convert(command);

        //Verify bussines rules
        String messageEr = null;

        messageEr = validaHourLimit(detachedDataDetail);
        if (messageEr != null) {
            command.setMessageError(messageEr);
            return command;
        }

        messageEr = validaTheSameActivityUsedInTracks(getDataDetail(), detachedDataDetail);
        if (messageEr != null) {
            command.setMessageError(messageEr);
            return command;
        }

        messageEr = validaTheHour(getDataDetail(), detachedDataDetail);
        if (messageEr != null) {
            command.setMessageError(messageEr);
            return command;
        }

        System.out.println("saveDataDetailCommand. calling save");
        DataDetail savedDataDetail = dataDetailRepository.save(detachedDataDetail);
        System.out.println("Saved dataDetail with Id: " + savedDataDetail.getId());

        return command;
    }

    @Override
    public List<DataGridObject> getMyTable(Long trackId, String sessionValue) {
        Set<DataDetail> dataDetailSet = getDataDetail();

        List<DataGridObject> dataDataGridObjectSet = new ArrayList<>();
        //Set<DataGridObject> dataDataGridObjectSet = new HashSet<>();

        if (dataDetailSet != null && dataDetailSet.size() > 0) {
            for (DataDetail recordDataDetail: dataDetailSet ) {
                if (recordDataDetail.getTrackObject().getId() == trackId) {
                    String valorPer = recordDataDetail.getSessionPeriod().toString().trim();
                    if (valorPer.equals(sessionValue)) {
                        DataGridObject rowD = new DataGridObject();
                        rowD.setId(recordDataDetail.getId().toString());
                        rowD.setTurno(recordDataDetail.getSessionPeriod().toString());
                        rowD.setHourInit(recordDataDetail.getHourInit().toString());
                        rowD.setHourFin(recordDataDetail.getHourFin().toString());
                        rowD.setDescription(recordDataDetail.getDataInput().getDescription());
                        rowD.setDataInputId(recordDataDetail.getDataInput().getId().toString());
                        dataDataGridObjectSet.add(rowD);
                    }
                }
            }
        }

        Collections.sort(dataDataGridObjectSet, new sortStructureOutputFileCommand(true, false));
        return dataDataGridObjectSet;
    }

    private String validaHourLimit(DataDetail detachedDataDetail) {
        String messageEr = null;
        if (detachedDataDetail == null) {
            return messageEr;
        }

        try {
            DateFormat dateFormato = new SimpleDateFormat("HH:mm");
            //date new
            Date horaNewFin = dateFormato.parse(detachedDataDetail.getHourFin().toString());

            if (    (detachedDataDetail.getSessionPeriod().toString().trim().equals("Morning")) &&
                    (horaNewFin.getTime() > (dateFormato.parse("12:00")).getTime())
                    )
            {
                messageEr = "You can not select this hour, because you will have your lunch";
                return messageEr;
            }

            if (    (detachedDataDetail.getSessionPeriod().toString().trim().equals("Afternoon")) &&
                    (horaNewFin.getTime() > (dateFormato.parse("05:00")).getTime())
                    )
            {
                messageEr = "You can not select this hour, because the duration of this activity exceeds 5 o'clock";
                return messageEr;
            }
        }
        catch (Exception ex) {
            System.out.println("exception");
        }

        return messageEr;
    }

    private String validaTheHour(Set<DataDetail> dataDetailSet, DataDetail detachedDataDetail) {
        String messageEr = null;
        if ((dataDetailSet == null) || (dataDetailSet.size() == 0)) {
            return messageEr;
        }

        for (DataDetail recordDataDetail: dataDetailSet ) {
            if (recordDataDetail.getTrackObject().getId() == detachedDataDetail.getTrackObject().getId()) {
                if (recordDataDetail.getHourInit().toString().trim().equals(detachedDataDetail.getHourInit().toString().trim())){
                    messageEr = "You've put one activity in this time!";
                    return messageEr;
                }

                try {

                    DateFormat dateFormato = new SimpleDateFormat("HH:mm");

                    //dates saved
                    Date horaSavedIni = dateFormato.parse(recordDataDetail.getHourInit().toString());
                    Date horaSavedFin = dateFormato.parse(recordDataDetail.getHourFin().toString());

                    //dates new
                    Date horaNewIni = dateFormato.parse(detachedDataDetail.getHourInit().toString());
                    Date horaNewFin = dateFormato.parse(detachedDataDetail.getHourFin().toString());

                    if ((horaNewIni.getTime() >= horaSavedIni.getTime()) && (horaNewIni.getTime() <= horaSavedFin.getTime())){
                        messageEr = "You can not select this hour, because it is bussy";
                        return messageEr;
                    }

                    if ((horaNewFin.getTime() >= horaSavedIni.getTime()) && (horaNewFin.getTime() <= horaSavedFin.getTime())){
                        messageEr = "You can not select this hour, because it is bussy";
                        return messageEr;
                    }
                }
                catch (Exception ex) {
                    System.out.println("exception");
                }
            }
        }

        return messageEr;
    }

    private String validaTheSameActivityUsedInTracks(Set<DataDetail> dataDetailSet, DataDetail detachedDataDetail) {
        String messageEr = null;
        if ((dataDetailSet == null) || (dataDetailSet.size() == 0)) {
            return messageEr;
        }

        for (DataDetail dataDeta: dataDetailSet) {
            if (detachedDataDetail.getDataInput().getId() == dataDeta.getDataInput().getId()) {
                messageEr = "You've already used this activity in one track.";
                return messageEr;
            }
        }
        return messageEr;
    }

}
