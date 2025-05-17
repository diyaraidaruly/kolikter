package org.example.kolikter.model;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    private int vin;
    private String brand;
    private String model;
    private int year;
    private String color;
    private double engineVolume;
    private int mileage;
    private String transmission;
    private String driveType;
    private String bodyType;
    private double price;
    private String fuelType;
    private Status status;

}
