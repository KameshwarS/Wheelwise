package com.infosys.Wheelwise.Model;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    // Getters and setters
    public enum BookingStatus {
        PENDING, CONFIRMED, CANCELLED, COMPLETED // add other statuses as necessary
    }

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

    public User getCustomer() {
        return user;
    }

    public void setCustomer(User user) {
        this.user = user;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public Booking(Vehicle vehicle, User user, LocalDateTime startDate, LocalDateTime endDate,
                   BigDecimal totalPrice, BookingStatus status, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        super();
        this.vehicle = vehicle;
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = totalPrice;
        this.status = status;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public Booking() {
        super();
    }

}
