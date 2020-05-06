package com.example.smartParking.domain;

public enum TypeJobPosition {
    PPS("ППС"), AUP("АУП");

    public String type;

    TypeJobPosition(String type) {
        this.type = type;
    }
}
