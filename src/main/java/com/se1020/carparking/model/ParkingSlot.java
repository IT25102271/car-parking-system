package com.se1020.carparking.model;

public class ParkingSlot {

    private String slotId;
    private String slotNumber;
    private String floor;
    private String type;   // CAR, BIKE, VAN
    private String status; // AVAILABLE, OCCUPIED, MAINTENANCE
    private double ratePerHour;

    public ParkingSlot() {}

    public ParkingSlot(String slotId, String slotNumber, String floor,
                       String type, String status, double ratePerHour) {
        this.slotId = slotId;
        this.slotNumber = slotNumber;
        this.floor = floor;
        this.type = type;
        this.status = status;
        this.ratePerHour = ratePerHour;
    }

    public String getSlotId() { return slotId; }
    public void setSlotId(String slotId) { this.slotId = slotId; }

    public String getSlotNumber() { return slotNumber; }
    public void setSlotNumber(String slotNumber) { this.slotNumber = slotNumber; }

    public String getFloor() { return floor; }
    public void setFloor(String floor) { this.floor = floor; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getRatePerHour() { return ratePerHour; }
    public void setRatePerHour(double ratePerHour) { this.ratePerHour = ratePerHour; }
}