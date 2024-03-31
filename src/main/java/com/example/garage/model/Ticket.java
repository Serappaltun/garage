package com.example.garage.model;

import com.example.garage.common.VehicleType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Ticket {
    private static int ticketIdCounter = 1;
    private int ticketId;
    private String plateNumber;
    private String color;
    private VehicleType carType;

    public Ticket(String plateNumber, String color, VehicleType carType) {
        this.ticketId = ticketIdCounter++;
        this.plateNumber = plateNumber;
        this.color = color;
        this.carType = carType;
    }

}