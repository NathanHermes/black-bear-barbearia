package br.ifsp.edu.blackbearbarbearia.domain.entities.user;

import java.util.ArrayList;
import java.util.List;

public class User {
    private Integer id;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String number;
    private String complement;
    private String district;
    private String city;
    private String login;
    private String passwordHash;
    private boolean active;
    private List<Role> roles;
    private List<Day> days;
    private String lastPassword;
    public User(String login, String passwordHash) {
        this.login = login;
        this.passwordHash = passwordHash;
    }

    public User(String fullName, String email, String phone, String address, String number, String complement, String district, String city, String login, String passwordHash, boolean active) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.number = number;
        this.complement = complement;
        this.district = district;
        this.city = city;
        this.login = login;
        this.passwordHash = passwordHash;
        this.active = active;
        this.roles = new ArrayList<Role>();
        this.days = new ArrayList<Day>();
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isValidPassword(String password) {
        return !password.equals(lastPassword);
    }

    public void clearPasswordHash() {
        if(this.passwordHash != null)
            this.passwordHash = null;
    }

    public void addRole(Role role) {
        if(!roles.contains(role)) roles.add(role);
    }

    public void removeRole(Role role) {
        roles.remove(role);
    }

    public boolean hasRole(Role role) {
        if(roles.contains(role)) return true;
        return false;
    }

    public void addDay(Day day) {
        if(!days.contains(day) && days.size() < 6) days.add(day);
    }

    public void removeDay(Day day) {
        days.remove(day);
    }
}
