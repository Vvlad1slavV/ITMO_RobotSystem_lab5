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
            System.out.print("//: ");
            while (!command.equals("exit")) {
                if (!commandReader.hasNextLine()) {
                    break;
                }
                else {
                    command = commandReader.nextLine();
                }
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
                                control.updateId(trimCommand[1]);
                                break;
                            case "remove_by_id":
                                control.removeById(trimCommand[1]);
                                break;
                            case "clear":
                                control.clear();
                                break;
                            case "save":
                                control.save();
                                break;
                            case "execute_script":
                                control.executeScript(trimCommand[1]);
                                break;
                            case "exit":
                                break;
                            case "add_if_min":
                                control.addIfMin(trimCommand[1]);
                                break;
                            case "remove_greater":
                                control.removeGreater(trimCommand[1]);
                                break;
                            case "remove_lower":
                                control.removeLower(trimCommand[1]);
                                break;
                            case "group_counting_by_coordinates":
                                control.groupCountingByCoordinates();
                                break;
                            case "filter_contains_name":
                                control.filterContainsName(trimCommand[1]);
                                break;
                            case "print_field_descending_price":
                                control.printFieldDescendingPrice();
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
                System.out.print("//: ");
            }
        }
    }
}
//123