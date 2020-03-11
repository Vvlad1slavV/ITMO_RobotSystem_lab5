package com.itmo.r3135;

import java.util.Scanner;

/**
 * Класс для читывания команд из командной строки.
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
     */
    public void interactiveMod(){
        try (Scanner commandReader = new Scanner(System.in)) {
            System.out.print("//: ");
            while (!command.equals("exit")) {
                if (!commandReader.hasNextLine()) {
                    break;
                }
                else {
                    command = commandReader.nextLine();
                }
                control.processing(command);
                System.out.print("//: ");
            }
        }
    }
}