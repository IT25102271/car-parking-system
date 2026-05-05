package com.se1020.carparking.controller;

import com.se1020.carparking.model.User;
import com.se1020.carparking.service.ParkingSlotService;
import com.se1020.carparking.service.ReservationService;
import com.se1020.carparking.service.VehicleService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ParkingSlotService parkingSlotService;

    @Autowired
    private VehicleService vehicleService;

    // READ - User views own reservations
    @GetMapping("/user/reservations")
    public String myReservations(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedUser");
        model.addAttribute("reservations", reservationService.getReservationsByUser(user.getUserId()));
        return "reservation/my-reservations";
    }

    // READ - Admin views all reservations
    @GetMapping("/admin/reservations")
    public String allReservations(Model model) {
        model.addAttribute("reservations", reservationService.getAllReservations());
        return "reservation/admin-reservation-list";
    }

    // CREATE - Book slot page
    @GetMapping("/user/reservations/book")
    public String bookPage(@RequestParam(required = false) String slotId,
                           HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedUser");
        model.addAttribute("slots", parkingSlotService.getAvailableSlots());
        model.addAttribute("vehicles", vehicleService.getVehiclesByOwner(user.getUserId()));
        model.addAttribute("preselectedSlotId", slotId);
        return "reservation/book-slot";
    }

    // CREATE - Book slot submit
    @PostMapping("/user/reservations/book")
    public String bookSlot(@RequestParam String vehiclePlate,
                           @RequestParam String slotId,
                           @RequestParam String reservationDate,
                           @RequestParam String startTime,
                           @RequestParam int durationHours,
                           HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");
        reservationService.createReservation(user.getUserId(), vehiclePlate,
                slotId, reservationDate, startTime, durationHours);
        return "redirect:/user/reservations";
    }

    // UPDATE - Edit reservation page
    @GetMapping("/user/reservations/edit/{reservationId}")
    public String editReservationPage(@PathVariable String reservationId, Model model) {
        model.addAttribute("reservation", reservationService.getReservationById(reservationId));
        return "reservation/edit-reservation";
    }

    // UPDATE - Edit reservation submit
    @PostMapping("/user/reservations/update")
    public String updateReservation(@RequestParam String reservationId,
                                    @RequestParam String startTime,
                                    @RequestParam int durationHours) {
        reservationService.updateReservation(reservationId, startTime, durationHours);
        return "redirect:/user/reservations";
    }

    // DELETE - Cancel reservation
    @PostMapping("/user/reservations/cancel/{reservationId}")
    public String cancelReservation(@PathVariable String reservationId) {
        reservationService.cancelReservation(reservationId);
        return "redirect:/user/reservations";
    }

    // DELETE - Admin hard deletes reservation
    @PostMapping("/admin/reservations/delete/{reservationId}")
    public String deleteReservation(@PathVariable String reservationId) {
        reservationService.deleteReservation(reservationId);
        return "redirect:/admin/reservations";
    }
}