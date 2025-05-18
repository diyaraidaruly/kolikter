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

    public Car(int vin, String brand, String model, int year, String color,
               double engineVolume, int mileage, String transmission,
               String driveType, String bodyType, double price, String fuelType) {
        this.vin = vin;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.color = color;
        this.engineVolume = engineVolume;
        this.mileage = mileage;
        this.transmission = transmission;
        this.driveType = driveType;
        this.bodyType = bodyType;
        this.price = price;
        this.fuelType = fuelType;
        this.status = Status.AVAILABLE;
    }


}
