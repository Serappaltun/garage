package com.example.garage.model.apimodel;

import com.example.garage.model.Ticket;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TicketStatusModel {

    private String plateNumber;
    private String color;
    private int[] slots;

    public TicketStatusModel(Ticket ticket, int... slots){
        this.plateNumber = ticket.getPlateNumber();
        this.color = ticket.getColor();
        this.slots = slots;
    }
}
