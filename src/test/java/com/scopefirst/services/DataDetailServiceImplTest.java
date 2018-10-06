package com.scopefirst.services;

import com.scopefirst.commands.DataDetailCommand;
import com.scopefirst.converts.DataDetailCommandToDataDetail;
import com.scopefirst.domain.*;
import com.scopefirst.repositories.DataDetailRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class DataDetailServiceImplTest {

    DataDetailServiceImpl dataDetailService;

    @Mock
    DataDetailRepository dataDetailRepository;

    @Mock
    DataDetailCommandToDataDetail dataDetailCommandToDataDetail;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        dataDetailService = new DataDetailServiceImpl(dataDetailRepository, dataDetailCommandToDataDetail);
    }

    @Test
    public void getDataDetailTest() {
        DataDetail dataDetail = new DataDetail();
        HashSet dataDetailData = new HashSet();
        dataDetailData.add(dataDetail);

        when(dataDetailService.getDataDetail()).thenReturn(dataDetailData);
        Set<DataDetail> dataDetailSet = dataDetailService.getDataDetail();
        assertEquals(dataDetailSet.size(), 1);
        verify(dataDetailRepository, times(1)).findAll();

    }

    @Test
    public void deleteByIdTest() {
        //given
        Long trackIdToDelete = Long.valueOf(2L);
        //when
        dataDetailService.deleteById(trackIdToDelete);
        //then
        verify(dataDetailRepository, times(1)).deleteById(anyLong());
    }

    @Test
    public void saveDataDetailTest() {

        //given
        DataDetailCommand command = new DataDetailCommand();
        command.setId(1L);
        command.setTrackId(1L);
        command.setDataInputId(1L);
        command.setSessionPeriod("Morning");
        command.setHourInit("10:00");
        command.setHourFin("10:50");
        command.setDateCreated(new Date());
        command.setTimeSelectHour("09");
        command.setTimeSelectMinute("15");

        DataInput dat = new DataInput();
        dat.setId(1L);
        dat.setDescription("Create better mocks for Spring Boot 65min");
        dat.setTimeActivity(65);
        Set<DataInput> dataInputs = new HashSet<>();
        dataInputs.add(dat);

        command.setListActivities(dataInputs);
        command.setMyTableMorning(null);
        command.setMyTableAfternoon(null);
        command.setMessageError(null);

        DataDetail dataDetail = new DataDetail();

        dataDetail.setId(1L);
        DataTrack trackObject = new DataTrack();
        trackObject.setId(1L);
        trackObject.setDateCreated(new Date());
        dataDetail.setTrackObject(trackObject);
        dataDetail.setDataInput(dat);
        dataDetail.setSessionPeriod(SessionPeriod.Morning);
        dataDetail.setHourInit("10:00");
        dataDetail.setHourFin("10:50");
        dataDetail.setDateCreated(new Date());

        when(dataDetailCommandToDataDetail.convert(any())).thenReturn(dataDetail);

        when(dataDetailRepository.save(any())).thenReturn(dataDetail);

        //when
        DataDetailCommand savedCommand = dataDetailService.saveDataDetail(command);

        //then
        assertEquals(Long.valueOf(1L), savedCommand.getId());

    }

}