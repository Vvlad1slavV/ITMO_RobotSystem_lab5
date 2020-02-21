package com.itmo.r3135;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

public class Control {
    private File jsonFile;
    private HashSet<Product> products;
    private Gson gson;

    //control methods
    {
        gson = new Gson();
        products = new HashSet();
    }

    public Control(String filePath) throws IOException {
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
        System.out.printf("%-25s%5s%n", "add {element}", "Добавить новый элемент в коллекцию");
        System.out.printf("%-25s%5s%n", "update id {element}", "Обновить значение элемента коллекции, id которого равен заданному");
        System.out.printf("%-25s%5s%n", "remove_greater {element}", "Удалить из коллекции все элементы, превышающие заданный");
        System.out.printf("%-25s%5s%n", "add_if_min {element}", "Добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции");
        System.out.printf("%-25s%5s%n", "remove_by_id id", "Удалить элемент из коллекции по его id");
        System.out.printf("%-25s%5s%n", "execute_script file_name", "Считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");
        System.out.printf("%-30s%5s%n", "group_counting_by_coordinate", "Сгруппировать элементы коллекции по значению поля coordinates, вывести количество элементов в каждой группе\n");
        System.out.printf("%-25s%5s%n", "filter_contains_name name", "Вывести элементы, значение поля name которых содержит заданную подстроку");
        System.out.printf("%-25s%5s%n", "print_field_descending_price price", "вывести значения поля price в порядке убывания");
        System.out.printf("%-25s%5s%n", "clear", "Очистить коллекцию");
        System.out.printf("%-25s%5s%n", "save", "Сохранить коллекцию в файл");
        System.out.printf("%-25s%5s%n", "exit", "Завершить программу (без сохранения в файл)");
    }

    public void show() {
        if (products.size() != 0)
            for (Product p : products) System.out.println(gson.toJson(p));
        else System.out.println("Коллекция пуста.");
    }

    public void add(String s) {
        {
            try {
                if (products.add(gson.fromJson(s, Product.class))) {

                    //Добавить проверку совместимости элемента
                    System.out.println("Элемент успешно добавлен.");
                    save();
                }
            } catch (JsonSyntaxException | IOException ex) {
                System.out.println("Возникла ошибка синтаксиса Json. Элемент не был добавлен");
            }
        }
    }

    public void update_id(String s) {

    }

    public void remove_by_id(String s) {
        for (Product p : products) {
            if (p.getId() == Integer.parseInt(s)) {
            }
        }
    }

    public void clear() throws IOException {
        products.clear();
        System.out.print("Коллекция очищена.");
        save();
    }

    public void save() throws IOException {
        FileWriter fileWriter = new FileWriter(jsonFile);
        try {
            fileWriter.write(gson.toJson(products));
            System.out.println(gson.toJson(products));
            fileWriter.flush();
            System.out.println("Файл успешно сохранён");
        } catch (Exception ex) {
            System.out.println("При записи файла что-то пошло не так");
        }finally {
            fileWriter.close();
        }
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

    public void load_collection() throws IOException {

    }


}
