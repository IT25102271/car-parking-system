package com.se1020.carparking.model;

public class Vehicle {

    private String vehicleId;
    private String ownerId;
    private String plateNumber;
    private String make;
    private String model;
    private String color;
    private String vehicleUrl;
    private String type; // CAR, BIKE, VAN

    public Vehicle() {}

    public Vehicle(String vehicleId, String ownerId, String plateNumber,
                   String make, String model, String color, String vehicleUrl, String type) {
        this.vehicleId = vehicleId;
        this.ownerId = ownerId;
        this.plateNumber = plateNumber;
        this.make = make;
        this.model = model;
        this.color = color;
        this.vehicleUrl = vehicleUrl;
        this.type = type;
    }

    public String getVehicleId() { return vehicleId; }
    public void setVehicleId(String vehicleId) { this.vehicleId = vehicleId; }

    public String getOwnerId() { return ownerId; }
    public void setOwnerId(String ownerId) { this.ownerId = ownerId; }

    public String getPlateNumber() { return plateNumber; }
    public void setPlateNumber(String plateNumber) { this.plateNumber = plateNumber; }

    public String getMake() { return make; }
    public void setMake(String make) { this.make = make; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getVehicleUrl() { return vehicleUrl; }
    public void setVehicleUrl(String vehicleUrl) { this.vehicleUrl = vehicleUrl; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}