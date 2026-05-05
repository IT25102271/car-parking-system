package com.se1020.carparking.model;

public class Bill {

    private String billId;
    private String reservationId;
    private String userId;
    private String slotNumber;
    private String vehiclePlate;
    private int durationHours;
    private double ratePerHour;
    private double totalAmount;
    private double discount;
    private double finalAmount;
    private String status; // UNPAID, PAID
    private String createdDate;

    public Bill() {}

    public Bill(String billId, String reservationId, String userId, String slotNumber,
                String vehiclePlate, int durationHours, double ratePerHour,
                double totalAmount, double discount, double finalAmount,
                String status, String createdDate) {
        this.billId = billId;
        this.reservationId = reservationId;
        this.userId = userId;
        this.slotNumber = slotNumber;
        this.vehiclePlate = vehiclePlate;
        this.durationHours = durationHours;
        this.ratePerHour = ratePerHour;
        this.totalAmount = totalAmount;
        this.discount = discount;
        this.finalAmount = finalAmount;
        this.status = status;
        this.createdDate = createdDate;
    }

    public String getBillId() { return billId; }
    public void setBillId(String billId) { this.billId = billId; }

    public String getReservationId() { return reservationId; }
    public void setReservationId(String reservationId) { this.reservationId = reservationId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getSlotNumber() { return slotNumber; }
    public void setSlotNumber(String slotNumber) { this.slotNumber = slotNumber; }

    public String getVehiclePlate() { return vehiclePlate; }
    public void setVehiclePlate(String vehiclePlate) { this.vehiclePlate = vehiclePlate; }

    public int getDurationHours() { return durationHours; }
    public void setDurationHours(int durationHours) { this.durationHours = durationHours; }

    public double getRatePerHour() { return ratePerHour; }
    public void setRatePerHour(double ratePerHour) { this.ratePerHour = ratePerHour; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public double getDiscount() { return discount; }
    public void setDiscount(double discount) { this.discount = discount; }

    public double getFinalAmount() { return finalAmount; }
    public void setFinalAmount(double finalAmount) { this.finalAmount = finalAmount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getCreatedDate() { return createdDate; }
    public void setCreatedDate(String createdDate) { this.createdDate = createdDate; }
}