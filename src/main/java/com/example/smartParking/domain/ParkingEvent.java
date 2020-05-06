package com.example.smartParking.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Entity
@Table(name = "parking_event")
public class ParkingEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "place_id")
    private ParkingPlace place;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "automobile_id")
    private Automobile automobile;
    @NotBlank(message = "Время начала парковки не может быть пустым")
    private Timestamp startTime;
    private Timestamp endTime;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public ParkingPlace getPlace() {
        return place;
    }

    public void setPlace(ParkingPlace place) {
        this.place = place;
    }

    public Automobile getAutomobile() {
        return automobile;
    }

    public void setAutomobile(Automobile automobile) {
        this.automobile = automobile;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }
}
