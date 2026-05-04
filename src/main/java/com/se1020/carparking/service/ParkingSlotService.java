package com.se1020.carparking.service;

import com.se1020.carparking.model.ParkingSlot;
import com.se1020.carparking.repository.ParkingSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ParkingSlotService {

    @Autowired
    private ParkingSlotRepository parkingSlotRepository;

    public List<ParkingSlot> getAllSlots() {
        return parkingSlotRepository.findAll();
    }

    public List<ParkingSlot> getAvailableSlots() {
        return parkingSlotRepository.findByStatus("AVAILABLE");
    }

    public ParkingSlot getSlotById(String slotId) {
        return parkingSlotRepository.findById(slotId);
    }

    public void addSlot(String slotNumber, String floor, String type, double ratePerHour) {
        String slotId = UUID.randomUUID().toString();
        ParkingSlot slot = new ParkingSlot(slotId, slotNumber, floor, type, "AVAILABLE", ratePerHour);
        parkingSlotRepository.save(slot);
    }

    public void updateSlot(String slotId, String slotNumber, String floor,
                           String type, String status, double ratePerHour) {
        ParkingSlot slot = parkingSlotRepository.findById(slotId);
        slot.setSlotNumber(slotNumber);
        slot.setFloor(floor);
        slot.setType(type);
        slot.setStatus(status);
        slot.setRatePerHour(ratePerHour);
        parkingSlotRepository.update(slot);
    }

    public void updateSlotStatus(String slotId, String status) {
        ParkingSlot slot = parkingSlotRepository.findById(slotId);
        slot.setStatus(status);
        parkingSlotRepository.update(slot);
    }

    public void deleteSlot(String slotId) {
        parkingSlotRepository.delete(slotId);
    }
}