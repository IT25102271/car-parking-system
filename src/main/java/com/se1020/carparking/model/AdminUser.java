package com.se1020.carparking.model;

public class AdminUser extends User {

    private String adminCode;

    public AdminUser() {
        super();
    }

    public AdminUser(String userId, String name, String email, String password, String phone, String adminCode) {
        super(userId, name, email, password, phone, "ADMIN");
        this.adminCode = adminCode;
    }

    public String getAdminCode() { return adminCode; }
    public void setAdminCode(String adminCode) { this.adminCode = adminCode; }

    @Override
    public String getDashboardPath() {
        return "/admin/dashboard";
    }
}