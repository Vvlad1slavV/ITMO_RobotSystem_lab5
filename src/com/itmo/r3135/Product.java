package com.itmo.r3135;

import java.time.LocalDateTime;
import java.util.Comparator;

public class Product implements Comparable<Product> {
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Double price; //Поле может быть null, Значение поля должно быть больше 0
    private String partNumber; //Длина строки должна быть не меньше 21, Поле не может быть null
    private Float manufactureCost; //Поле не может быть null
    private UnitOfMeasure unitOfMeasure; //Поле не может быть null
    private Person owner; //Поле не может быть null

    private static int idCounter;

    static {
        idCounter = 1;
    }

    {
        creationDate = java.time.LocalDateTime.now();
    }

    public Product(String name, Coordinates coordinates, Double price, String partNumber, Float manufactureCost, UnitOfMeasure unitOfMeasure, Person owner) {
        this.id = idCounter;
        this.name = name;
        this.coordinates = coordinates;
        this.price = price;
        this.partNumber = partNumber;
        this.manufactureCost = manufactureCost;
        this.unitOfMeasure = unitOfMeasure;
        this.owner = owner;
        idCounter++;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static int getIdCounter() {
        return idCounter;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Double getPrice() {
        return price;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public Float getManufactureCost() {
        return manufactureCost;
    }

    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public Person getOwner() {
        return owner;
    }

    @Override
    public int compareTo(Product o) {
        return (int) ((this.getPrice() - o.getPrice()) * 100);
    }
}
