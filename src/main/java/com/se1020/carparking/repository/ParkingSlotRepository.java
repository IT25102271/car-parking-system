package com.se1020.carparking.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.se1020.carparking.model.ParkingSlot;
import com.se1020.carparking.repository.support.JsonDataAccess;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ParkingSlotRepository {

    private static final String CLASSPATH = "data/slots.json";
    private static final String FILE_NAME = "slots.json";

    public List<ParkingSlot> findAll() {
        return JsonDataAccess.readList(CLASSPATH, FILE_NAME, new TypeReference<List<ParkingSlot>>() {});
    }

    public void saveAll(List<ParkingSlot> slots) {
        JsonDataAccess.writeList(FILE_NAME, slots);
    }

    public ParkingSlot findById(String slotId) {
        for (ParkingSlot slot : findAll()) {
            if (slot.getSlotId().equals(slotId)) {
                return slot;
            }
        }
        return null;
    }

    public List<ParkingSlot> findByStatus(String status) {
        List<ParkingSlot> result = new ArrayList<>();
        for (ParkingSlot slot : findAll()) {
            if (slot.getStatus().equals(status)) {
                result.add(slot);
            }
        }
        return result;
    }

    public void save(ParkingSlot slot) {
        List<ParkingSlot> slots = findAll();
        slots.add(slot);
        saveAll(slots);
    }

    public void update(ParkingSlot updatedSlot) {
        List<ParkingSlot> slots = findAll();
        for (int i = 0; i < slots.size(); i++) {
            if (slots.get(i).getSlotId().equals(updatedSlot.getSlotId())) {
                slots.set(i, updatedSlot);
                break;
            }
        }
        saveAll(slots);
    }

    public void delete(String slotId) {
        List<ParkingSlot> slots = findAll();
        slots.removeIf(s -> s.getSlotId().equals(slotId));
        saveAll(slots);
    }
}
