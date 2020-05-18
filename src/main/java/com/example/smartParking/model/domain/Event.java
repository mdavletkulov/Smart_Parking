package com.example.smartParking.model.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "parking_event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "place_id")
    private Place place;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "automobile_id")
    private Automobile automobile;
    @NotBlank(message = "Время начала парковки не может быть пустым")
    private Timestamp startTime;
    private Timestamp endTime;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
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

    public boolean isPassViolation() {
        return automobile.getPerson().getPassNum() == null
                || automobile.getPerson().getPassEndDate().getTime() < getStartTime().getTime();
    }

    public boolean isSpecialStatusViolation() {
        return place.isSpecialStatus() && !automobile.getPerson().isSpecialStatus();
    }
}
