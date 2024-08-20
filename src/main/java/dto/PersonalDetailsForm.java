package dto;

public class PersonalDetailsForm {
    public final String name;
    public final String surName;

    public final String address;

    public final String station;

    public final String phone;

    public PersonalDetailsForm(String name, String surName, String address, String station, String phone) {
        this.name = name;
        this.surName = surName;
        this.address = address;
        this.station = station;
        this.phone = phone;
    }
}
