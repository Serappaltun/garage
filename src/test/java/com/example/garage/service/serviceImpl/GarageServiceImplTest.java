package com.example.garage.service.serviceImpl;

import com.example.garage.common.exceptions.GarageIsFullException;
import com.example.garage.model.Ticket;
import com.example.garage.common.VehicleType;
import com.example.garage.model.apimodel.TicketStatusModel;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class GarageServiceImplTest {
    @InjectMocks
    private GarageServiceImpl garageServiceImpl;

    @Test
    void park() {
        Ticket expectedTicket = new Ticket("ABC123", "Red", VehicleType.CAR);
        Ticket actualTicket = garageServiceImpl.park("ABC123", "Red", VehicleType.CAR);
        assertEquals(expectedTicket.getCarType(), actualTicket.getCarType());
        assertEquals(expectedTicket.getPlateNumber(), actualTicket.getPlateNumber());
        assertEquals(expectedTicket.getColor(), actualTicket.getColor());
    }

    @Test
    void leave() {
        Ticket ticket = garageServiceImpl.park("ABC123", "Red", VehicleType.CAR);
        garageServiceImpl.leave(ticket.getTicketId());
    }

    @Test
    void getStatus() {
        Ticket ticket0 = garageServiceImpl.park("ABC123", "Red", VehicleType.CAR);
        Ticket ticket1 = garageServiceImpl.park("ABC124", "Blue", VehicleType.JEEP);
        List<TicketStatusModel> ticketStatusModels = garageServiceImpl.getStatus();
        assert(ticketStatusModels.size()==2);
        assert(ticketStatusModels.get(0).getPlateNumber().equals(ticket0.getPlateNumber()));
        assert(ticketStatusModels.get(1).getPlateNumber().equals(ticket1.getPlateNumber()));

    }

    @Test
    public void testParkJeepJeepTruck(){
        Ticket ticket0 = garageServiceImpl.park("ABC123", "Red", VehicleType.JEEP);
        Ticket ticket1 = garageServiceImpl.park("ABC124", "Blue", VehicleType.JEEP);
        assert(ticket0.getColor().equals("Red"));
        assert(ticket1.getColor().equals("Blue"));

        Assertions.assertThrows(GarageIsFullException.class, ()-> garageServiceImpl.park("ABC125", "Blue", VehicleType.TRUCK),
                GarageIsFullException.GARAGE_IS_FULL);
    }

    @Test
    public void testParkJeepTruckCarLeaveJeepParkCar(){
        Ticket ticket0 = garageServiceImpl.park("ABC123", "Red", VehicleType.JEEP);
        garageServiceImpl.park("ABC124", "Blue", VehicleType.TRUCK);
        garageServiceImpl.park("ABC125", "Blue", VehicleType.CAR);
        garageServiceImpl.leave(ticket0.getTicketId());
        Ticket ticket3 = garageServiceImpl.park("ABC126", "White", VehicleType.CAR);
        assert(ticket0.getColor().equals("Red"));
        assert(ticket3.getColor().equals("White"));
    }

    @Test
    public void testParkCarTruckJeepTruckLeaveJeepParkCar(){
        Ticket ticket0 = garageServiceImpl.park("34-SO-1988", "Black", VehicleType.CAR);
        garageServiceImpl.park("34-BO-1987", "Red", VehicleType.TRUCK);
        Ticket ticket2 = garageServiceImpl.park("34-VO-2018", "Blue", VehicleType.JEEP);
        Assertions.assertThrows(GarageIsFullException.class, ()-> garageServiceImpl.park(" 34-HBO-2020", "Black", VehicleType.TRUCK),
                GarageIsFullException.GARAGE_IS_FULL);
        garageServiceImpl.leave(ticket2.getTicketId());

        Ticket ticket3 = garageServiceImpl.park("34-LO-2000", "White", VehicleType.CAR);
        List<TicketStatusModel> status = garageServiceImpl.getStatus();
        log.info(status.toString());
        assert(ticket0.getColor().equals("Black"));
        assert(ticket3.getColor().equals("White"));
    }
}