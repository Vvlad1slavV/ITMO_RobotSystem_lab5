package com.itmo.r3135;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.*;
import java.util.HashSet;
import java.util.Iterator;

public class Control {

    private File jsonFile;
    private HashSet<Product> products;

    //control methods
    {
        products = new HashSet();
    }

    public Control(String filePath) {
        if (filePath == null) {
            System.out.println("Путь к файлу json не обнаружен.");
            System.exit(1);
        }
        File jsonPath = new File(filePath);

        if (jsonPath.exists()) {
            this.jsonFile = jsonPath;
            System.out.println("Файл " + this.jsonFile.toString() + " успешно обнаружен");
        } else {
            System.out.println("Файл по указанному пути не существует.");
            System.exit(1);
        }
    }

    public void help() {
        System.out.println("Список доступных команд\n" +
                "---------------------------------------------\n" +
                "help : вывести справку по доступным командам\n" +
                "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                "add {element} : добавить новый элемент в коллекцию\n" +
                "update id {element} : обновить значение элемента коллекции, id которого равен заданному\n" +
                "remove_by_id id : удалить элемент из коллекции по его id\n" +
                "clear : очистить коллекцию\n" +
                "save : сохранить коллекцию в файл\n" +
                "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
                "exit : завершить программу (без сохранения в файл)\n" +
                "add_if_min {element} : добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции\n" +
                "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный\n" +
                "remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный\n" +
                "group_counting_by_coordinates : сгруппировать элементы коллекции по значению поля coordinates, вывести количество элементов в каждой группе\n" +
                "filter_contains_name name : вывести элементы, значение поля name которых содержит заданную подстроку\n" +
                "print_field_descending_price price : вывести значения поля price в порядке убывания");
    }

    public void show() {
    }

    public void add(String s) {
    }

    public void update_id(String s) {
    }

    public void remove_by_id(String s) {
    }

    public void clear() {
    }

    public void save() {
    }

    public void execute_script(String s) {
    }

    public void add_if_min(String s) {
    }

    public void remove_greater(String s) {
    }

    public void remove_lower(String s) {
    }

    public void group_counting_by_coordinates() {
    }

    public void filter_contains_name(String s) {
    }

    public void print_field_descending_price(String s) {
    }

    public void info() {
    }
}
