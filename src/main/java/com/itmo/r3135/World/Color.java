package com.itmo.r3135.World;

/**
 * Цвета
 */
public enum Color {
    GREEN("Зелёный"),
    RED("Красный"),
    BLACK("Чёрный"),
    BLUE("Синий"),
    YELLOW("Жёлтый");

    String name;

    Color(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
