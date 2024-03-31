package com.example.garage.common.exceptions;

public class GarageIsFullException extends RuntimeException {
    public static final String GARAGE_IS_FULL = "Garage is full";
    public GarageIsFullException(String message){
        super(message);
    }

}
