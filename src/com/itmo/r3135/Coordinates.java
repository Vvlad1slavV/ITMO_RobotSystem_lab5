package com.itmo.r3135;

public class Coordinates {
    private Float x; //Значение поля должно быть больше -50, Поле не может быть null
    private double y;

    public Coordinates(Float x, double y) {
        this.x = x;
        this.y = y;
    }

    public Float getX() {
        return x;
    }

    public void setX(Float x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getY() {
        return y;
    }
}