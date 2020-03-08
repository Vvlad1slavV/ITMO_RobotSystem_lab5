package com.itmo.r3135.World;

/**
 * Единицы измерения.
 */
public enum UnitOfMeasure {
    PCS("Штуки"),
    LITERS("Литры"),
    GRAMS("Граммы"),
    MILLIGRAMS("Милиграммы");

    String name;

     UnitOfMeasure(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
