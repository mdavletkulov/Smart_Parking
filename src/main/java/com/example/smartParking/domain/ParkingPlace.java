package com.example.smartParking.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "parking_place")
public class ParkingPlace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parking_id")
    private Parking parking;
    @NotBlank(message = "Номер места не может быть пустым")
    private int placeNumber;
    @NotBlank(message = "Статус парковочного места не может быть пустым")
    private boolean specialStatus;

    public int getPlaceNumber() {
        return placeNumber;
    }

    public void setPlaceNumber(int placeNumber) {
        this.placeNumber = placeNumber;
    }

    public boolean isSpecialStatus() {
        return specialStatus;
    }

    public void setSpecialStatus(boolean specialStatus) {
        this.specialStatus = specialStatus;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Parking getParking() {
        return parking;
    }

    public void setParking(Parking parking) {
        this.parking = parking;
    }
}
