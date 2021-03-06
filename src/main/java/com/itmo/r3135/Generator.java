package com.itmo.r3135;

import com.google.gson.Gson;
import com.itmo.r3135.World.*;

import java.time.LocalDateTime;
import java.util.Random;

/**
 * Класс генератор элемента в формате JSON
 */
public class Generator {
    static String[] personNames = {"Аркадий", "Петрович", "Партия", "Ленин", "Сталин", "Колхоз", "МЕГА(ИТМО)", "Царь", "Красные", "Буржуй"};
    static UnitOfMeasure[] units = {UnitOfMeasure.PCS, UnitOfMeasure.LITERS, UnitOfMeasure.GRAMS, UnitOfMeasure.MILLIGRAMS};
    static Color[] colors = {Color.GREEN, Color.RED, Color.BLACK, Color.BLUE, Color.YELLOW};

    static char[] chs = "ZXCVBNMASDFGHJKLQWERTYUIOP1234567890zxcvbnmasdfghjklqwertyuiop".toCharArray();
    static Gson gson = new Gson();
    static Random random = new Random();

    /**
     * Метод возвращает объект класса Product в формате json
     */
    public static String nextGsonProduct() {
        return gson.toJson(nextProduct());
    }

    public static Product nextProduct() {
        return new Product(nextName(), nextCoordinates(), nextPrice(), nextPartNumber(), nextManufactureCost(), nextUnitOfMeasure(), nextPerson());
    }

    private static UnitOfMeasure nextUnitOfMeasure() {
        return units[random.nextInt(units.length)];
    }

    private static Float nextManufactureCost() {
        return random.nextFloat();
    }

    private static String nextName() {
        return "Неизвестный продукт №: " + Math.abs(random.nextInt());
    }

    private static Double nextPrice() {
        return random.nextDouble();
    }

    private static String nextPartNumber() {
        String number = new String();
        for (int i = 0; i < 22; i++) {
            number = number + (chs[random.nextInt(chs.length)]);
        }
        return number;
    }

    private static Color nextColor() {
        return colors[random.nextInt(colors.length)];
    }

    private static Person nextPerson() {
        return new Person(nextPersonName(), LocalDateTime.now(), nextColor(), nextColor());
    }

    private static String nextPersonName() {
        return personNames[random.nextInt(personNames.length)];
    }

    private static Coordinates nextCoordinates() {
        return new Coordinates(random.nextFloat() * 500 - 250, random.nextDouble() * 500 - 250);
    }
}
