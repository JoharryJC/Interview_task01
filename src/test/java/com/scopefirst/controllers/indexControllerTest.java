package com.scopefirst.controllers;

import com.scopefirst.commands.DataDetailCommand;
import com.scopefirst.domain.DataInput;
import com.scopefirst.domain.DataTrack;
import com.scopefirst.services.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class indexControllerTest {

    @Mock
    ReadTextFileService readTextFileService;

    @Mock
    WriteTextFileService writeTextFileService;

    @Mock
    DataInputService dataInputService;

    @Mock
    DataDetailService dataDetailService;

    @Mock
    DataTrackService dataTrackService;

    IndexController indexController;

    MockMvc mockMvc;

    @Mock
    Model model;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        indexController = new IndexController(readTextFileService, writeTextFileService, dataInputService, dataDetailService, dataTrackService);
        mockMvc = MockMvcBuilders.standaloneSetup(indexController)
                .build();
    }

    @Test
    public void writeTextFileTest() {
    }

    @Test
    public void deleteTrackByIdTest() {
    }

    @Test
    public void deleteDataDetailByIdTest() throws Exception {
        mockMvc.perform(get("/dataDetail/1/1/1/delete"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("track"));

        verify(dataDetailService, times(1)).deleteById(anyLong());
    }

    @Test
    public void getIndexPageTest() {
        //given
        Set<DataTrack> dataTracks = new HashSet<>();

        DataTrack dataTrack = new DataTrack();
        dataTrack.setId(1L);
        dataTrack.setDateCreated(new Date());

        dataTracks.add(dataTrack);

        when(dataTrackService.getDataTrackAll()).thenReturn(dataTracks);

        ArgumentCaptor<Set<DataTrack>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String viewName = indexController.getIndexPage(model);

        //then
        assertEquals("index", viewName);
        verify(dataTrackService, times(1)).getDataTrackAll();
        verify(model, times(1)).addAttribute(eq("listTracks"), argumentCaptor.capture());
    }

    @Test
    public void newTrackTest() throws Exception {

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


        DataTrack trackObject = new DataTrack();
        trackObject.setId(1L);
        trackObject.setDateCreated(new Date());

        when(dataTrackService.saveDataTrack()).thenReturn(trackObject);
        when(dataDetailService.saveDataDetail(any())).thenReturn(command);

        mockMvc.perform(post("/track/new")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        );
    }

    @Test
    public void saveOrUpdateTest() {
    }
}