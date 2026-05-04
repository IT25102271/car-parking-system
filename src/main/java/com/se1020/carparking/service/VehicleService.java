package com.se1020.carparking.service;

import com.se1020.carparking.model.Vehicle;
import com.se1020.carparking.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public List<Vehicle> getVehiclesByOwner(String ownerId) {
        return vehicleRepository.findByOwnerId(ownerId);
    }

    public Vehicle getVehicleById(String vehicleId) {
        return vehicleRepository.findById(vehicleId);
    }

    public void addVehicle(String ownerId, String plateNumber, String make,
                           String model, String color, String vehicleUrl, String type) {
        String vehicleId = UUID.randomUUID().toString();
        Vehicle vehicle = new Vehicle(vehicleId, ownerId, plateNumber, make, model, color, vehicleUrl, type);
        vehicleRepository.save(vehicle);
    }

    public void updateVehicle(String vehicleId, String plateNumber, String make,
                              String model, String color, String vehicleUrl, String type) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId);
        vehicle.setPlateNumber(plateNumber);
        vehicle.setMake(make);
        vehicle.setModel(model);
        vehicle.setColor(color);
        vehicle.setVehicleUrl(vehicleUrl);
        vehicle.setType(type);
        vehicleRepository.update(vehicle);
    }

    public void deleteVehicle(String vehicleId) {
        vehicleRepository.delete(vehicleId);
    }
}