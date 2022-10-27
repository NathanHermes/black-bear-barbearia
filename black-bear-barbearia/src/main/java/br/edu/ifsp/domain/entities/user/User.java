package br.edu.ifsp.domain.entities.user;

public class User {
    private int id;
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

    public User(int id, String fullName, String email, String phone, String address, String number, String complement, String district, String city, String login, String passwordHash, boolean active) {
        this.id = id;
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
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public void setEmail(String email) {
        this.email = email;
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

    public boolean isValidPassWord(String password) {
        if (password.equals(passwordHash))
            return true;
        return false;
    }

    public void clearPasswordHash() {

    }

    public void addRole(Role role) {}

    public void removeRole(Role role) {}

    public boolean hasRole(Role role) {return false;}
}
