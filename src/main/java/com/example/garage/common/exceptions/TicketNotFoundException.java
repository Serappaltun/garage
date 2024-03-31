package com.example.garage.common.exceptions;

public class TicketNotFoundException extends RuntimeException {
    public static final String CAR_NOT_EXIST = "Ticket not found";

    public TicketNotFoundException(String message){
        super(message);
    }

}
