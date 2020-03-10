package com.itmo.r3135.Cantrol;

/**
 * Выводит список доступных команд.
 */
public class HelpCommand extends AbstractCommand {
    public HelpCommand(Control control) {
        super(control);
    }

    @Override
    public void activate(String input) {
        System.out.printf("%-30s%5s%n", "add {element}", "Добавить новый элемент в коллекцию");
        System.out.printf("%-30s%5s%n", "update_id id", "Обновить значение элемента коллекции, id которого равен заданному");
        System.out.printf("%-30s%5s%n", "remove_greater {element}", "Удалить из коллекции все элементы, превышающие заданный");
        System.out.printf("%-30s%5s%n", "add_if_min {element}", "Добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции");
        System.out.printf("%-30s%5s%n", "remove_by_id id", "Удалить элемент из коллекции по его id");
        System.out.printf("%-30s%5s%n", "execute_script file_name", "Считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");
        System.out.printf("%-30s%5s%n", "group_counting_by_coordinates", "Сгруппировать элементы коллекции по значению поля coordinates, вывести количество элементов в каждой группе");
        System.out.printf("%-30s%5s%n", "filter_contains_name name", "Вывести элементы, значение поля name которых содержит заданную подстроку");
        System.out.printf("%-30s%5s%n", "print_field_descending_price", "Вывести значения поля price в порядке убывания");
        System.out.printf("%-30s%5s%n", "clear", "Очистить коллекцию");
        System.out.printf("%-30s%5s%n", "save", "Сохранить коллекцию в файл");
        System.out.printf("%-30s%5s%n", "exit", "Завершить программу (без сохранения в файл)");
    }
}
