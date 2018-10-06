package com.scopefirst.controllers;

import com.scopefirst.commands.DataDetailCommand;
import com.scopefirst.domain.*;
import com.scopefirst.services.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

/*
Created by Joharry Correa 15/09/2018
*/

//@SessionAttributes("mysessionErr")
@Slf4j
@Controller
public class IndexController {

    private final ReadTextFileService readTextFileService;
    private final WriteTextFileService writeTextFileService;
    private final DataInputService dataInputService;
    private final DataDetailService dataDetailService;
    private final DataTrackService dataTrackService;

    private static final String ListTracks_URL = "dataDetailObject/listTracks";

    public IndexController(ReadTextFileService readTextFileService, WriteTextFileService writeTextFileService, DataInputService dataInputService, DataDetailService dataDetailService, DataTrackService dataTrackService) {
        this.readTextFileService = readTextFileService;
        this.writeTextFileService = writeTextFileService;
        this.dataInputService = dataInputService;
        this.dataDetailService = dataDetailService;
        this.dataTrackService = dataTrackService;
    }

    @GetMapping("writeFile")
    public String writeTextFile() {
        List<DataGridObject> listDataGridObjects = dataDetailService.listDataGridObjects();
        if ((listDataGridObjects != null) && (listDataGridObjects.size() > 0)) {
            writeTextFileService.writeTextFile(listDataGridObjects);
        }
        return "redirect:/";
    }

    @GetMapping("track/{id}/delete")
    public String deleteTrackById(@PathVariable String id) {
        System.out.println("Deleting detail activities in track");
        dataDetailService.deleteAll(Long.valueOf(id));

        System.out.println("Deleting track id: " + id);
        dataTrackService.deleteById(Long.valueOf(id));
        return "redirect:/";
    }

    @GetMapping("dataDetail/{id}/{id_detail}/{id2}/delete")
    public String deleteDataDetailById(@PathVariable String id, @PathVariable String id_detail, @PathVariable String id2, Model model) {
        System.out.println("Deleting dataDetail id: " + id_detail);
        id_detail = id_detail.replace("actID_", "");
        dataDetailService.deleteById(Long.valueOf(id_detail.trim()));

        DataDetailCommand command = new DataDetailCommand();
        command.setId(Long.valueOf(id));

        model.addAttribute("timeSelectMinute", TimeSelectMinute.getListMinute());
        model.addAttribute("timeSelectHour", TimeSelectHour.getListHour());
        model.addAttribute("sessionPeriod", TimeSelectHour.getListHour());

        command.setListActivities(dataInputService.getDataInputAll());
        command.setMyTableMorning(dataDetailService.getMyTable(command.getId(), SessionPeriod.Morning.toString().trim()));
        command.setMyTableAfternoon(dataDetailService.getMyTable(command.getId(), SessionPeriod.Afternoon.toString().trim()));

        model.addAttribute("dataDetailObject", command);
        return "track";
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(Model model) {
        Set<DataTrack> listTracks = dataTrackService.getDataTrackAll();
        model.addAttribute("listTracks", listTracks);
        //model.addAttribute("mysessionErr", null);
        return "index";
    }

    @RequestMapping({"/returnBack"})
    public String getReturnBack(Model model) {
        //model.addAttribute("mysessionErr", null);
        return "redirect:/index";
    }

    //@GetMapping("track/new")
    @RequestMapping({"track/new"})
    public String newTrack(Model model) {

        model.addAttribute("timeSelectMinute", TimeSelectMinute.getListMinute());
        model.addAttribute("timeSelectHour", TimeSelectHour.getListHour());
        model.addAttribute("sessionPeriod", TimeSelectHour.getListHour());

        DataDetailCommand dataDetailCommand = new DataDetailCommand();
        dataDetailCommand.setId(0L);
        dataDetailCommand.setListActivities(dataInputService.getDataInputAll());

        if (!model.containsAttribute("mysessionErr")) {
            dataDetailCommand.setMessageError(null);
            model.addAttribute("mysessionErr", null);
        }

        model.addAttribute("dataDetailObject", dataDetailCommand);
        return "track";
    }

    @PostMapping
    @RequestMapping("dataDetailObject")
    public String saveOrUpdate(@Valid @ModelAttribute("dataDetailObject") DataDetailCommand command, Model model, BindingResult bindingResult) {

        model.addAttribute("mysessionErr", null);
        command.setMessageError(null);

        DataTrack dataTrack = new DataTrack();
        boolean createdNow = false;
        //command.setCreatedNow(false);
        if (command.getId() == 0) {
            dataTrack = dataTrackService.saveDataTrack();
            command.setId(dataTrack.getId());
            createdNow = true;
            //command.setCreatedNow(true);
        }
        else {
            dataTrack.setId(0L);
            createdNow = false;
            //command.setCreatedNow(false);
        }

        DataDetailCommand savedCommand = dataDetailService.saveDataDetail(command);

        if (savedCommand.getMessageError() != null) {
            //if (command.getCreatedNow == true) {
            if (createdNow == true) {
                dataTrackService.deleteById(dataTrack.getId());
                command.setId(0L);
                command.setMessageError(savedCommand.getMessageError() );

                model.addAttribute("mysessionErr", savedCommand.getMessageError());
            }
            else {
                //new
                command.setMessageError(savedCommand.getMessageError() );
                model.addAttribute("mysessionErr", savedCommand.getMessageError());
            }
        }

        model.addAttribute("timeSelectMinute", TimeSelectMinute.getListMinute());
        model.addAttribute("timeSelectHour", TimeSelectHour.getListHour());
        model.addAttribute("sessionPeriod", TimeSelectHour.getListHour());

        command.setListActivities(dataInputService.getDataInputAll());
        command.setMyTableMorning(dataDetailService.getMyTable(command.getId(), SessionPeriod.Morning.toString().trim()));
        command.setMyTableAfternoon(dataDetailService.getMyTable(command.getId(), SessionPeriod.Afternoon.toString().trim()));

        model.addAttribute("dataDetailObject", command);
        return "track";
    }


}
