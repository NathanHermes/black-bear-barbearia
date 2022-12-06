package br.ifsp.edu.blackbearbarbearia.domain.entities.user;

public class Address {
    private Integer id;
    private String address;
    private String number;
    private String complement;
    private String district;
    private String city;

    public Address() {

    }
    public Address(Integer id, String address, String number, String complement, String district, String city) {
        this.id = id;
        this.address = address;
        this.number = number;
        this.complement = complement;
        this.district = district;
        this.city = city;
    }

    public Address(String address, String number, String complement, String district, String city) {
        new Address(null, address, number, complement, district, city);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public String toString() {
        return address + ", " + number + " - " + district + ", " + city + " - " + complement;
    }
}
