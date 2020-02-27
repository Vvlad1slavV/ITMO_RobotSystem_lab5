package com.itmo.r3135;

import java.time.LocalDateTime;
import java.util.Comparator;

/**
 * Класс Product.
 */
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

    /**
     * Конструктор для класса Product
     * @param name - имя
     * @param coordinates - класса координат
     * @param price - цена
     * @param partNumber - номер партии
     * @param manufactureCost - цена производства
     * @param unitOfMeasure - единицы измерения продукта
     * @param owner - владелец
     */
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

    /**
     * Устанавливает id орпеделенному элементу коллекции.
     * @param id - id предмета
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Возвращает уровень вложенности, если при выполнении скриптов они ссылаются на другие скрипты внутри себя.
     * @return число соответсвуещее вложенности выполения.
     */
    public static int getIdCounter() {
        return idCounter;
    }

    /**
     * Возвращает id элемента.
     * @return id элемента коллекции.
     */
    public int getId() {
        return id;
    }

    /**
     * Воозвращает поле name элемента коллекции.
     * @return - имя
     */
    public String getName() {
        return name;
    }

    /**
     * Устанавливает дату создания.
     * @param creationDate - дата создания.
     */
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Возвращает координаты в формате класса Coordinates.
     * @return - класс Coordinates
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Возвращает дату создания
     * @return - дата создания.
     */
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * Возвращает поле price элемента коллекции.
     * @return - цена.
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Возращает
     * @return -
     */
    public String getPartNumber() {
        return partNumber;
    }

    /**
     * Возвращает поле manufactureCost элемента коллекции.
     * @return - цена производства
     */
    public Float getManufactureCost() {
        return manufactureCost;
    }

    /**
     * Возвращает поле unitOfMeasure элемента коллекции.
     * @return -
     */
    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    /**
     * Возвращает поле owner элемента коллекции.
     * @return - владелец предмета.
     */
    public Person getOwner() {
        return owner;
    }

    @Override
    public int compareTo(Product o) {
        return (int) ((this.getPrice() - o.getPrice()) * 100);
    }

    @Override
    public String toString() {
        return  "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", price=" + price +
                ", partNumber='" + partNumber + '\'' +
                ", manufactureCost=" + manufactureCost +
                ", unitOfMeasure=" + unitOfMeasure +
                ", owner=" + owner;
    }
}
