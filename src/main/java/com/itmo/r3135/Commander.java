package com.itmo.r3135;

import java.io.IOException;
import java.util.Scanner;

/**
 * Класс для управления командами.
 */
public class Commander {

    private Control control;
    private String command;

    {
        command = "";
    }

    public Commander(Control control) {
        this.control = control;
    }

    /**
     * Промежуточный метод для использования команд управления коллекцией.
     * Для выхода используйте команду exit
     *
     * @throws IOException
     */
    public void interactiveMod() throws IOException {
        try (Scanner commandReader = new Scanner(System.in)) {
            System.out.print("//: ");
            while (!command.equals("exit")) {
                if (!commandReader.hasNextLine()) {
                } else {
                    command = commandReader.nextLine();
                }
                control.notify(command);
                System.out.print("//: ");
            }
        }
    }
}
//123