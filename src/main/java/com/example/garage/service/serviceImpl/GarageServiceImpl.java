package com.example.garage.service.serviceImpl;

import com.example.garage.common.VehicleType;
import com.example.garage.common.exceptions.TicketNotFoundException;
import com.example.garage.model.*;
import com.example.garage.common.exceptions.GarageIsFullException;
import com.example.garage.model.apimodel.TicketStatusModel;
import com.example.garage.service.GarageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static com.example.garage.common.Constants.MAX_SLOTS;

@Slf4j
@Service
public class GarageServiceImpl implements GarageService {
    final Garage garage;
    private final ConcurrentMap<Integer, Ticket> ticketMap;

    public GarageServiceImpl() {
        this.garage = new Garage();
        this.ticketMap = new ConcurrentHashMap<>();
    }
    @Override
    public Ticket park(String plateNumber, String color, VehicleType vehicleType) {
        int requiredSlots = vehicleType.getRequiredSlots();
        int startIndex = findAvailableSlot(requiredSlots);
        if (startIndex != -1) {
            Ticket ticket = new Ticket(plateNumber, color, vehicleType);
            List<GarageSlot> slots = garage.getSlots();
            int endIndex = startIndex + requiredSlots;
            for (int i = startIndex; i < endIndex; i++) {
                if (i != endIndex - 1) {
                    slots.get(i).setVehicleType(vehicleType);
                    slots.get(i).setTicketId(ticket.getTicketId());
                }
                slots.get(i).setAvailable(false);
            }
            log.info("new Ticket created. TicketNo: {}, StartSlot: {}, Size:{}, VehicleType: {}, Plate: {}, Color:{}",
                    ticket.getTicketId(), startIndex, requiredSlots, vehicleType, plateNumber, color);
            ticketMap.put(ticket.getTicketId(), ticket);
            return ticket;
        } else {
            throw new GarageIsFullException(GarageIsFullException.GARAGE_IS_FULL);
        }
    }

    @Override
    public void leave(int ticketId) {
        log.info("ticket id : {}", ticketId);
        int ticketStartIndex = -1;
        int i = 0;
        int requiredSlots = 0;
        List<GarageSlot> slots = garage.getSlots();
        log.info("slots: {}", slots);
        while (i < slots.size()) {
            if (ticketStartIndex == -1) {
                GarageSlot garageSlot = slots.get(i);
                if (garageSlot.getTicketId() == ticketId) {
                    ticketStartIndex = i;
                    requiredSlots = garageSlot.getVehicleType().getRequiredSlots();
                    garageSlot.clear();
                }
            } else {
                if (i - ticketStartIndex < requiredSlots) {
                    GarageSlot garageSlot = slots.get(i);
                    garageSlot.clear();
                } else {
                    break;
                }
            }

            i++;
        }
        if(ticketMap.isEmpty()) {
            throw new TicketNotFoundException(TicketNotFoundException.CAR_NOT_EXIST);
        } else {
            ticketMap.remove(ticketId);
        }
    }
    @Override
    public List<TicketStatusModel> getStatus() {
        return ticketMap.values().stream().map(ticket -> {
                    int[] slotNumbers = garage.getSlots().stream().filter(slot -> slot.getTicketId() == ticket.getTicketId()).mapToInt(GarageSlot::getSlotNumber).toArray();
                    return new TicketStatusModel(ticket, slotNumbers);
                }).sorted(Comparator.comparingInt(o -> o.getSlots()[0])).
                toList();
    }

    public int findAvailableSlot(int requiredSlots) {
        int count = 0;
        for (int i = 0; i < MAX_SLOTS; i++) {
            if (garage.getSlots().get(i).isAvailable()) {
                count++;
                if (count == requiredSlots) {
                    int slot = i - count + 1;
                    return switch (slot) {
                        case 1 -> 0;
                        case MAX_SLOTS -> -1;
                        default -> slot;
                    };
                }
            } else {
                count = 0;
            }
        }
        return -1;
    }

}
