package com.example.garage.model;

import com.example.garage.common.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GarageSlot {
    private int slotNumber;
    private int ticketId;
    private VehicleType vehicleType;
    private boolean available;

    public void clear(){
        ticketId=-1;
        vehicleType = null;
        available = true;
    }
}