package com.se1020.carparking.service;

import com.se1020.carparking.model.Bill;
import com.se1020.carparking.model.ParkingSlot;
import com.se1020.carparking.model.Reservation;
import com.se1020.carparking.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ParkingSlotService parkingSlotService;

    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    public List<Bill> getBillsByUser(String userId) {
        return billRepository.findByUserId(userId);
    }

    public Bill getBillById(String billId) {
        return billRepository.findById(billId);
    }

    public void generateBill(String reservationId) {
        Reservation reservation = reservationService.getReservationById(reservationId);
        ParkingSlot slot = parkingSlotService.getSlotById(reservation.getSlotId());

        double totalAmount = slot.getRatePerHour() * reservation.getDurationHours();
        String billId = UUID.randomUUID().toString();

        Bill bill = new Bill(billId, reservationId, reservation.getUserId(),
                reservation.getSlotNumber(), reservation.getVehiclePlate(),
                reservation.getDurationHours(), slot.getRatePerHour(),
                totalAmount, 0, totalAmount, "UNPAID",
                LocalDate.now().toString());

        billRepository.save(bill);
    }

    public void applyDiscount(String billId, double discount) {
        Bill bill = billRepository.findById(billId);
        bill.setDiscount(discount);
        bill.setFinalAmount(bill.getTotalAmount() - discount);
        billRepository.update(bill);
    }

    public void payBill(String billId) {
        Bill bill = billRepository.findById(billId);
        bill.setStatus("PAID");
        billRepository.update(bill);
    }

    public void deleteBill(String billId) {
        billRepository.delete(billId);
    }
}