package com.infosys.Wheelwise.Model;

import jakarta.persistence.*;

@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_path")
    private String imagePath;



    @ManyToOne

    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    public Image() {
    }

    public Image(Long id,String imagePath, Vehicle vehicle) {
        this.id = id;
        this.vehicle = vehicle;
        this.imagePath=imagePath;
    }
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}
