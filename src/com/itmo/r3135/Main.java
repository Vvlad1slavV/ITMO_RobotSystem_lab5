package com.itmo.r3135;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        picture.gamepad();
        Commander commander = new Commander(new Control("D:\\java\\TOP_SECRET\\testing\\data.json"));
        commander.interactiveMod();
    }
}
