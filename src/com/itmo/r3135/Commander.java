package com.itmo.r3135;

import java.io.IOException;
import java.util.Scanner;

public class Commander {

    private Control control;
    private String userCommand;
    private String[] finalUserCommand;

    {
        userCommand = "";
    }

    public Commander() {}

    public void interactiveMod() throws IOException {
        try(Scanner commandReader = new Scanner(System.in)) {
            while (!userCommand.equals("exit")) {
                userCommand = commandReader.nextLine();
                finalUserCommand = userCommand.trim().split(" ", 2);
                try {
                    switch (finalUserCommand[0]) {
                        case "": break;
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
                            control.add(finalUserCommand[1]);
                            break;
                        case "update id":
                            control.update_id(finalUserCommand[1]);
                            break;
                        case "remove_by_id":
                            control.remove_by_id(finalUserCommand[1]);
                            break;
                        case "clear":
                            control.clear();
                            break;
                        case "save":
                            control.save();
                            break;
                        case "execute_script":
                            control.execute_script(finalUserCommand[1]);
                            break;
                        case "exit":
//                            exit without save
                            break;
                        case "add_if_min ":
                            control.add_if_min (finalUserCommand[1]);
                            break;
                        case "remove_greater":
                            control.remove_greater(finalUserCommand[1]);
                            break;
                        case "remove_lower":
                            control.remove_lower(finalUserCommand[1]);
                            break;
                        case "group_counting_by_coordinates":
                            control.group_counting_by_coordinates();
                            break;
                        case "filter_contains_name":
                            control.filter_contains_name(finalUserCommand[1]);
                            break;
                        case "print_field_descending_price":
                            control.print_field_descending_price(finalUserCommand[1]);
                            break;
                        default:
                            System.out.println("Неопознанная команда. Наберите 'help' для справки.");
                    }
                }
                catch (ArrayIndexOutOfBoundsException ex) {
                    System.out.println("Отсутствует аргумент.");
                }
            }
        }
    }


}
