package com.example.garage.service;

import com.example.garage.model.Ticket;
import com.example.garage.common.VehicleType;
import com.example.garage.model.apimodel.TicketStatusModel;

import java.util.List;

public interface GarageService {

    Ticket park(String plateNumber, String color, VehicleType vehicleType);
    void leave(int ticketId);
    List<TicketStatusModel> getStatus();
}
