package com.se1020.carparking.model;

public class RegularUser extends User {

    private String licenseNumber;

    public RegularUser() {
        super();
    }

    public RegularUser(String userId, String name, String email, String password, String phone, String licenseNumber) {
        super(userId, name, email, password, phone, "USER");
        this.licenseNumber = licenseNumber;
    }

    public String getLicenseNumber() { return licenseNumber; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }

    @Override
    public String getDashboardPath() {
        return "/user/dashboard";
    }
}