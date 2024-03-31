package com.example.garage.controller;

import com.example.garage.common.Constants;
import com.example.garage.common.exceptions.GarageIsFullException;
import com.example.garage.model.Ticket;
import com.example.garage.common.VehicleType;
import com.example.garage.model.apimodel.ParkModel;
import com.example.garage.model.apimodel.ResultModel;
import com.example.garage.model.apimodel.StatusModel;
import com.example.garage.model.apimodel.TicketStatusModel;
import com.example.garage.service.GarageService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController()
@RequestMapping(path = "/garage")
public class GarageController {
    private final GarageService garageService;

    public GarageController(GarageService garageService) {
        this.garageService = garageService;
    }

    @PostMapping(path = "/park")
    public ParkModel park(@RequestParam String plate, @RequestParam String color, @RequestParam VehicleType type){
        ParkModel res = new ParkModel();
        try {
            Ticket ticket = garageService.park(plate,color,type);
            StringBuilder sb = new StringBuilder();
            sb.append("Allocated ");
            int slots = type.getParkingSlots();
            sb.append(slots);
            if(slots > 1){
                sb.append(" slots.");
            }else{
                sb.append(" slot.");
            }
            res.setTicket(ticket);
            res.setCode(Constants.ST_SUCCESS);
            res.setMessage(sb.toString());
        } catch (GarageIsFullException e) {
           res.setCode(Constants.ST_PARK_ERR);
           res.setMessage(e.getMessage());
        } catch (Exception e) {
            res.setCode(Constants.ST_ERR);
            res.setMessage(e.getMessage());
        }
        return res;
    }

    @DeleteMapping("/leave/{ticketNo}")
    public ResultModel leave(@PathVariable(value = "ticketNo") int ticketNo){
        ResultModel res = new ResultModel();
        try {
            garageService.leave(ticketNo);
            res.setCode(Constants.ST_SUCCESS);
            res.setMessage(Constants.ST_SUCCESS_STR);
        } catch (GarageIsFullException e) {
            res.setCode(Constants.ST_LEAVE_ERR);
            res.setMessage(e.getMessage());
        } catch (Exception e) {
            res.setCode(Constants.ST_ERR);
            res.setMessage(e.getMessage());
        }
        return res;
    }

    @GetMapping(path = "/status")
    public StatusModel status(){
        StatusModel res = new StatusModel();
        try {
            List<TicketStatusModel> list =  garageService.getStatus();
            res.setStatus(list);
            res.setCode(Constants.ST_SUCCESS);
            res.setMessage(Constants.ST_SUCCESS_STR);
        } catch (Exception e) {
            res.setCode(Constants.ST_ERR);
            res.setMessage(e.getMessage());
        }
        return res;
    }

}
