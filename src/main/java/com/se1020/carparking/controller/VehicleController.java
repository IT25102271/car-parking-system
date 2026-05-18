package com.se1020.carparking.controller;

import com.se1020.carparking.model.User;
import com.se1020.carparking.service.VehicleService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

//sasa
@Controller
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    // READ - User views own vehicles.json
    @GetMapping("/user/vehicles")
    public String myVehicles(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedUser");
        model.addAttribute("vehicles", vehicleService.getVehiclesByOwner(user.getUserId()));
        return "vehicle/my-vehicles";
    }

    // READ - Admin views all vehicles.json
    @GetMapping("/admin/vehicles")
    public String allVehicles(Model model) {
        model.addAttribute("vehicles", vehicleService.getAllVehicles());
        return "vehicle/admin-vehicle-list";
    }

    // CREATE - Add vehicle page
    @GetMapping("/user/vehicles/add")
    public String addVehiclePage() {
        return "vehicle/add-vehicle";
    }

    // CREATE - Add vehicle submit
    @PostMapping("/user/vehicles/add")
    public String addVehicle(@RequestParam String plateNumber,
                             @RequestParam String make,
                             @RequestParam String model,
                             @RequestParam String color,
                             @RequestParam(required = false, defaultValue = "") String vehicleUrl,
                             @RequestParam String type,
                             HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");
        vehicleService.addVehicle(user.getUserId(), plateNumber, make, model, color, vehicleUrl, type);
        return "redirect:/user/vehicles";
    }

    // UPDATE - Edit vehicle page
    @GetMapping("/user/vehicles/edit/{vehicleId}")
    public String editVehiclePage(@PathVariable String vehicleId, Model model) {
        model.addAttribute("vehicle", vehicleService.getVehicleById(vehicleId));
        return "vehicle/edit-vehicle";
    }

    // UPDATE - Edit vehicle submit
    @PostMapping("/user/vehicles/update")
    public String updateVehicle(@RequestParam String vehicleId,
                                @RequestParam String plateNumber,
                                @RequestParam String make,
                                @RequestParam String model,
                                @RequestParam String color,
                                @RequestParam(required = false, defaultValue = "") String vehicleUrl,
                                @RequestParam String type) {
        vehicleService.updateVehicle(vehicleId, plateNumber, make, model, color, vehicleUrl, type);
        return "redirect:/user/vehicles";
    }

    // DELETE - Delete vehicle
    @PostMapping("/user/vehicles/delete/{vehicleId}")
    public String deleteVehicle(@PathVariable String vehicleId) {
        vehicleService.deleteVehicle(vehicleId);
        return "redirect:/user/vehicles";
    }
}