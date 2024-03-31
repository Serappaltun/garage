package com.example.garage.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static com.example.garage.common.Constants.MAX_SLOTS;

@Getter
public class Garage {
    private final List<GarageSlot> slots;

    public Garage(){
        this.slots = new ArrayList<>();
        for (int i = 0; i < MAX_SLOTS; i++) {
            slots.add(new GarageSlot(i+1,-1,null,true));
        }
    }
}