package com.se1020.carparking.controller;

import com.se1020.carparking.model.ParkingSlot;
import com.se1020.carparking.service.ParkingSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class ParkingSlotController {

    /** Floors selectable when creating or editing a slot (ordering: low → high). */
    private static final List<String> FORM_FLOOR_OPTIONS = List.of(
            "Ground",
            "Level 1",
            "Level 2",
            "Level 3",
            "Basement",
            "Rooftop"
    );

    @Autowired
    private ParkingSlotService parkingSlotService;

    // READ - Admin views all slots
    @GetMapping("/admin/slots")
    public String getAllSlots(Model model) {
        putSlotsGroupedByFloor(model, parkingSlotService.getAllSlots());
        return "slot/admin-slot-list";
    }

    // READ - User views available slots
    @GetMapping("/user/slots")
    public String getAvailableSlots(Model model) {
        putSlotsGroupedByFloor(model, parkingSlotService.getAvailableSlots());
        return "slot/available-slots";
    }

    /** Keeps floors in discovery order and sorts bays within each floor for a stable map layout */
    private void putSlotsGroupedByFloor(Model model, List<ParkingSlot> slots) {
        model.addAttribute("slots", slots);
        Map<String, List<ParkingSlot>> byFloor = slots.stream()
                .collect(Collectors.groupingBy(ParkingSlot::getFloor, LinkedHashMap::new, Collectors.toList()));
        byFloor.values().forEach(list -> list.sort(Comparator.comparing(ParkingSlot::getSlotNumber)));
        model.addAttribute("slotsByFloor", byFloor);
    }

    // CREATE - Add slot page
    @GetMapping("/admin/slots/add")
    public String addSlotPage(Model model) {
        model.addAttribute("floors", FORM_FLOOR_OPTIONS);
        return "slot/add-slot";
    }

    // CREATE - Add slot submit
    @PostMapping("/admin/slots/add")
    public String addSlot(@RequestParam String slotNumber,
                          @RequestParam String floor,
                          @RequestParam String type,
                          @RequestParam double ratePerHour) {
        parkingSlotService.addSlot(slotNumber, floor, type, ratePerHour);
        return "redirect:/admin/slots";
    }

    // UPDATE - Edit slot page
    @GetMapping("/admin/slots/edit/{slotId}")
    public String editSlotPage(@PathVariable String slotId, Model model) {
        model.addAttribute("slot", parkingSlotService.getSlotById(slotId));
        model.addAttribute("floors", FORM_FLOOR_OPTIONS);
        return "slot/edit-slot";
    }

    // UPDATE - Edit slot submit
    @PostMapping("/admin/slots/update")
    public String updateSlot(@RequestParam String slotId,
                             @RequestParam String slotNumber,
                             @RequestParam String floor,
                             @RequestParam String type,
                             @RequestParam String status,
                             @RequestParam double ratePerHour) {
        parkingSlotService.updateSlot(slotId, slotNumber, floor, type, status, ratePerHour);
        return "redirect:/admin/slots";
    }

    // DELETE - Delete slot
    @PostMapping("/admin/slots/delete/{slotId}")
    public String deleteSlot(@PathVariable String slotId) {
        parkingSlotService.deleteSlot(slotId);
        return "redirect:/admin/slots";
    }
}