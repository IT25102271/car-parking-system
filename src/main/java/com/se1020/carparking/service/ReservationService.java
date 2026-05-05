package com.se1020.carparking.service;

import com.se1020.carparking.model.ParkingSlot;
import com.se1020.carparking.model.Reservation;
import com.se1020.carparking.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ParkingSlotService parkingSlotService;

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public List<Reservation> getReservationsByUser(String userId) {
        return reservationRepository.findByUserId(userId);
    }

    public Reservation getReservationById(String reservationId) {
        return reservationRepository.findById(reservationId);
    }

    public void createReservation(String userId, String vehiclePlate, String slotId,
                                  String reservationDate, String startTime, int durationHours) {
        ParkingSlot slot = parkingSlotService.getSlotById(slotId);
        String reservationId = UUID.randomUUID().toString();
        Reservation reservation = new Reservation(reservationId, userId, vehiclePlate,
                slotId, slot.getSlotNumber(), reservationDate, startTime, durationHours, "ACTIVE");
        reservationRepository.save(reservation);
        parkingSlotService.updateSlotStatus(slotId, "OCCUPIED");
    }

    public void updateReservation(String reservationId, String startTime, int durationHours) {
        Reservation reservation = reservationRepository.findById(reservationId);
        reservation.setStartTime(startTime);
        reservation.setDurationHours(durationHours);
        reservationRepository.update(reservation);
    }

    public void cancelReservation(String reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId);
        reservation.setStatus("CANCELLED");
        reservationRepository.update(reservation);
        parkingSlotService.updateSlotStatus(reservation.getSlotId(), "AVAILABLE");
    }

    public void deleteReservation(String reservationId) {
        reservationRepository.delete(reservationId);
    }
}