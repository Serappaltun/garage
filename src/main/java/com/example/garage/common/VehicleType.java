package com.example.garage.common;

public enum VehicleType {
    CAR(1),
    JEEP(2),
    TRUCK(4);

    private final int slots;
    VehicleType(int val){
        this.slots = val;
    }

    public int getParkingSlots() {
        return slots;
    }
    public int getRequiredSlots() {
        return slots+1;
    }
}
