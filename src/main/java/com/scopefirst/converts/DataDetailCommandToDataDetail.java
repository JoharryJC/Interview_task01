package com.scopefirst.converts;

import com.scopefirst.commands.DataDetailCommand;
import com.scopefirst.domain.DataDetail;
import com.scopefirst.domain.DataInput;
import com.scopefirst.domain.DataTrack;
import com.scopefirst.domain.SessionPeriod;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/*
Created by Joharry Correa 16/09/2018
 */
@Component
public class DataDetailCommandToDataDetail implements Converter<DataDetailCommand, DataDetail> {

    @Synchronized
    @Nullable
    @Override
    public DataDetail convert(DataDetailCommand source) {
        if (source == null) {
            return null;
        }

        final DataDetail dataDetail = new DataDetail();
        DataTrack trackObj = new DataTrack();
        trackObj.setId(source.getId());
        dataDetail.setTrackObject(trackObj);

        DataInput dataInput = new DataInput();
        Integer amountOfMinutes = 0;
        for (DataInput dat: source.getListActivities()) {
            dataInput.setId(dat.getId());
            dataInput.setDescription(dat.getDescription());
            amountOfMinutes = dat.getTimeActivity();
        }

        dataDetail.setDataInput(dataInput);
        dataDetail.setSessionPeriod(SessionPeriod.valueOf(source.getSessionPeriod()));

        String tempHour = (Integer.valueOf(source.getTimeSelectHour()) < 10) ? "0" + Integer.valueOf(source.getTimeSelectHour()).toString() : Integer.valueOf(source.getTimeSelectHour()).toString() ;
        String tempMinu = (Integer.valueOf(source.getTimeSelectMinute()) < 10) ? "0" + Integer.valueOf(source.getTimeSelectMinute()).toString() : Integer.valueOf(source.getTimeSelectMinute()).toString() ;

        dataDetail.setHourInit(tempHour + ":" + tempMinu);
        dataDetail.setHourFin(getNewHour((tempHour + ":" + tempMinu), amountOfMinutes));
        dataDetail.setDateCreated(new Date());

        return dataDetail;
    }

    public String getNewHour(String hourTemp, int amountOfMinutes) {
        String returnValue = "";

        try {
            DateFormat dateFormat = new SimpleDateFormat("HH:mm");
            Date horaF;
            horaF = dateFormat.parse(hourTemp);  //horaF = dateF.parse("15:00");
            Date dateF = addMinutes(horaF, amountOfMinutes);

            String Hou = (dateF.getHours() <10)? "0" + Integer.valueOf(dateF.getHours()).toString(): Integer.valueOf(dateF.getHours()).toString() ;
            String minut = (dateF.getMinutes() <10)? "0" + Integer.valueOf(dateF.getMinutes()).toString(): Integer.valueOf(dateF.getMinutes()).toString() ;

            returnValue = Hou + ":" + minut;
        }
        catch (Exception ex) {
            System.out.println("exception");
        }

        return returnValue;
    }

    private Date addMinutes(Date date, int amountMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, amountMinutes);
        //System.out.println("calendar F = " + calendar.getTime());
        return calendar.getTime();
    }
}
