package com.example.FlightBookingSystem.Model;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class Admin extends  User {

    String name;
    String email;

    AdminType type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AdminType getType() {
        return type;
    }

    public void setType(AdminType type) {
        this.type = type;
    }

    public Admin(String name, String email, AdminType type) {
        this.name = name;
        this.email = email;
        this.type = type;
    }

    public Admin() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return name.equals(admin.name) && email.equals(admin.email) && type == admin.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, type);
    }
}
