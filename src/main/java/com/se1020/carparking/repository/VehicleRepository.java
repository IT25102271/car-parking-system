package com.se1020.carparking.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.se1020.carparking.model.Vehicle;
import com.se1020.carparking.repository.support.JsonDataAccess;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VehicleRepository {

    private static final String CLASSPATH = "data/vehicles.json";
    private static final String FILE_NAME = "vehicles.json";

    public List<Vehicle> findAll() {
        return JsonDataAccess.readList(CLASSPATH, FILE_NAME, new TypeReference<List<Vehicle>>() {});
    }

    public void saveAll(List<Vehicle> vehicles) {
        JsonDataAccess.writeList(FILE_NAME, vehicles);
    }

    public Vehicle findById(String vehicleId) {
        for (Vehicle v : findAll()) {
            if (v.getVehicleId().equals(vehicleId)) {
                return v;
            }
        }
        return null;
    }

    public List<Vehicle> findByOwnerId(String ownerId) {
        return findAll().stream().filter(v -> v.getOwnerId().equals(ownerId)).toList();
    }

    public void save(Vehicle vehicle) {
        List<Vehicle> vehicles = findAll();
        vehicles.add(vehicle);
        saveAll(vehicles);
    }

    public void update(Vehicle updatedVehicle) {
        List<Vehicle> vehicles = findAll();
        for (int i = 0; i < vehicles.size(); i++) {
            if (vehicles.get(i).getVehicleId().equals(updatedVehicle.getVehicleId())) {
                vehicles.set(i, updatedVehicle);
                break;
            }
        }
        saveAll(vehicles);
    }

    public void delete(String vehicleId) {
        List<Vehicle> vehicles = findAll();
        vehicles.removeIf(v -> v.getVehicleId().equals(vehicleId));
        saveAll(vehicles);
    }
}
