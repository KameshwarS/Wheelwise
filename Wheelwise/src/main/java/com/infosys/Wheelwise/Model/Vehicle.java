package com.infosys.Wheelwise.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @JsonIgnore
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
    private List<Image> images=new ArrayList<>();


    @Column(name = "company_name")
    private String companyName;
    private String numberPlate;
    private String model;
    private String type;
    private int capacity;
    private BigDecimal pricePerDay;
    private Year manufacturingYear;
    private Byte rating;

    public List<String> getImagePaths() {
        return imagePaths;
    }

    public void setImagePaths(List<String> imagePaths) {
        this.imagePaths = imagePaths;
    }

    @ElementCollection
    private List<String> imagePaths=new ArrayList<>();

    private String location; // New field for vehicle location
    private String transmissionType;
    private String fuelType;

    public String getTransmissionType() {
        return transmissionType;
    }

    public void setTransmissionType(String transmissionType) {
        this.transmissionType = transmissionType;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public void setType(String type) {
        this.type = type;
    }


    // Default constructor
    public Vehicle() {}

    public Vehicle(Long id, List<Image> images, String companyName, String model, String numberPlate, String type, int capacity, String location, String transmissionType, String fuelType, BigDecimal pricePerDay, Year manufacturingYear, Byte rating) {
        this.id = id;
        this.images = images;
        this.companyName = companyName;
        this.model = model;
        this.numberPlate = numberPlate;
        this.type = type;
        this.capacity = capacity;
        this.location = location;
        this.transmissionType = transmissionType;
        this.fuelType = fuelType;
        this.pricePerDay = pricePerDay;
        this.manufacturingYear = manufacturingYear;
        this.rating = rating;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }


    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public BigDecimal getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(BigDecimal pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public Year getManufacturingYear() {
        return manufacturingYear;
    }

    public void setManufacturingYear(Year manufacturingYear) {
        this.manufacturingYear = manufacturingYear;
    }

    public Byte getRating() {
        return rating;
    }

    public void setRating(Byte rating) {
        this.rating = rating;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


}
