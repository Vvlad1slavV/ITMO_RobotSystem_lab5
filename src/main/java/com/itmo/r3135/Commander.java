package com.itmo.r3135;

import java.io.IOException;
import java.util.Scanner;

/**
 * Класс для управления командами.
 */
public class Commander {

    private Control control;
    private String command;
    private String[] trimCommand;

    {
        command = "";
    }

    public Commander(Control control) {
        this.control = control;
    }

    /**
     * Промежуточный метод для использования команд управления коллекцией.
     * Для выхода используйте команду exit
     * @throws IOException
     */
    public void interactiveMod() throws IOException {
        try (Scanner commandReader = new Scanner(System.in)) {
            while (!command.equals("exit")) {
                command = commandReader.nextLine();
                trimCommand = command.trim().split(" ", 2);
                try {
                    try {
                        switch (trimCommand[0]) {
                            case "":
                                break;
                            case "help":
                                control.help();
                                break;
                            case "info":
                                control.info();
                                break;
                            case "show":
                                control.show();
                                break;
                            case "add":
                                control.add(trimCommand[1]);
                                break;
                            case "update":
                                control.update_id(trimCommand[1]);
                                break;
                            case "remove_by_id":
                                control.remove_by_id(trimCommand[1]);
                                break;
                            case "clear":
                                control.clear();
                                break;
                            case "save":
                                control.save();
                                break;
                            case "execute_script":
                                control.execute_script(trimCommand[1]);
                                break;
                            case "exit":
                                break;
                            case "add_if_min":
                                control.add_if_min(trimCommand[1]);
                                break;
                            case "remove_greater":
                                control.remove_greater(trimCommand[1]);
                                break;
                            case "remove_lower":
                                control.remove_lower(trimCommand[1]);
                                break;
                            case "group_counting_by_coordinates":
                                control.group_counting_by_coordinates();
                                break;
                            case "filter_contains_name":
                                control.filter_contains_name(trimCommand[1]);
                                break;
                            case "print_field_descending_price":
                                control.print_field_descending_price();
                                break;
                            default:
                                System.out.println("Неопознанная команда. Наберите 'help' для получения доступных команд.");
                        }
                    } catch ( NumberFormatException ex) {
                        System.out.println("Где-то проблема с форматом записи числа.");
                    }
                } catch (ArrayIndexOutOfBoundsException ex) {
                    System.out.println("Отсутствует аргумент.");
                }
            }
        }
    }
}
