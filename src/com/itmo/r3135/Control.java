package com.itmo.r3135;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Класс хранящий в себе команды.
 */
public class Control {
    private static final int SCRIPT_LIMIT = 20;

    private File jsonFile;
    private HashSet<Product> products;
    private Gson gson;
    private Date DateInitialization;
    private Date DateSave;
    private Date DateChange;
    private static int scriptCounter;
    //control methods

    static {
        scriptCounter = 0;
    }

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
        load_collection();
        DateInitialization = DateSave = DateChange = new Date();

    }

    /**
     * Выводит список доступных команд.
     */
    public void help() {
        System.out.printf("%-25s%5s%n", "add {element}", "Добавить новый элемент в коллекцию");
        System.out.printf("%-25s%5s%n", "update_id id", "Обновить значение элемента коллекции, id которого равен заданному");
        System.out.printf("%-25s%5s%n", "remove_greater {element}", "Удалить из коллекции все элементы, превышающие заданный");
        System.out.printf("%-25s%5s%n", "add_if_min {element}", "Добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции");
        System.out.printf("%-25s%5s%n", "remove_by_id id", "Удалить элемент из коллекции по его id");
        System.out.printf("%-25s%5s%n", "execute_script file_name", "Считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");
        System.out.printf("%-30s%5s%n", "group_counting_by_coordinates", "Сгруппировать элементы коллекции по значению поля coordinates, вывести количество элементов в каждой группе\n");
        System.out.printf("%-25s%5s%n", "filter_contains_name name", "Вывести элементы, значение поля name которых содержит заданную подстроку");
        System.out.printf("%-25s%5s%n", "print_field_descending_price price", "вывести значения поля price в порядке убывания");
        System.out.printf("%-25s%5s%n", "clear", "Очистить коллекцию");
        System.out.printf("%-25s%5s%n", "save", "Сохранить коллекцию в файл");
        System.out.printf("%-25s%5s%n", "exit", "Завершить программу (без сохранения в файл)");
    }

    /**
     * Функция выводит на экран все элементы коллекции.
     */
    public void show() {
        if (products.size() != 0)
            for (Product p : products) System.out.println(gson.toJson(p));
        else System.out.println("Коллекция пуста.");
    }

    /**
     * Добавляет элемент в коллекцию.
     * @param s строка в элемента в формате json.
     */
    public void add(String s) {
        try {
            Product addProduct = gson.fromJson(s, Product.class);
            addProduct.setCreationDate(java.time.LocalDateTime.now());
            addProduct.setId(uniqueoIdGeneration(products));
            if (checkNull(addProduct)) {
                System.out.println("Элемент не удовлетворяет требованиям коллекции");
            } else if (products.add(addProduct)) {
                System.out.println("Элемент успешно добавлен.");
            }
        } catch (JsonSyntaxException ex) {
            System.out.println("Возникла ошибка синтаксиса Json. Элемент не был добавлен");
        }
        DateChange = new Date();
    }

    //проверка на null-поля
    private boolean checkNull(Product product) {
        try {
            return product.getName() == null || (product.getName() != null && product.getName().isEmpty()) || product.getCoordinates() == null ||
                    (product.getCoordinates() != null && (product.getCoordinates().getX() == null || (product.getCoordinates().getX() != null && product.getCoordinates().getY() <= -50))) ||
                    product.getCreationDate() == null || (product.getPrice() != null && product.getPrice() <= 0) ||
                    product.getPartNumber() == null || (product.getPartNumber() != null && product.getPartNumber().length() < 21) ||
                    product.getManufactureCost() == null || product.getUnitOfMeasure() == null || product.getOwner() == null ||
                    product.getOwner().getName() == null || (product.getOwner().getName() != null && product.getOwner().getName().isEmpty()) ||
                    product.getOwner().getBirthday() == null || product.getOwner().getEyeColor() == null || product.getOwner().getHairColor() == null;
        } catch (Exception ex) {
            System.out.println("В процессе проверки объекта произошла ошибка");
            return true;
        }

    }

    //генератор незанятого id
    private int uniqueoIdGeneration(HashSet<Product> products) {
        Random r = new Random();
        int newId;
        int counter;
        while (true) {
            counter = 0;
            newId = Math.abs(r.nextInt());
            for (Product product : products) {
                if (product.getId() == newId) {
                    break;
                } else counter++;
            }
            if (counter == products.size()) {
                return newId;
            }
        }
    }

    /**
     * Заменяет в колеекции элемент с определенным id.
     * @param s строка, содержащая id заменяемого элемента коллекции и новый элементт в формате json. id и документ json разделены пробелом.
     */
    public void update_id(String s) {
        try {
            String id = s.split(" ", 2)[0];
            String elem = s.split(" ", 2)[1];
            //  System.out.println("ID:" +id);
            //  System.out.println("Элемент" + elem);
            Product newProduct = gson.fromJson(elem, Product.class);
            int startSize = products.size();
            if (checkNull(newProduct)) {
                System.out.println("Элемент не удовлетворяет требованиям коллекции");
            } else {
                remove_by_id(id);
                newProduct.setCreationDate(java.time.LocalDateTime.now());
                newProduct.setId(Integer.parseInt(id));
                if (startSize - products.size() == 1)
                    if (products.add(newProduct)) {
                        System.out.println("Элемент успешно обновлён.");
                    } else System.out.println("При замене элементов что-то пошло не так");
            }
        } catch (JsonSyntaxException ex) {
            System.out.println("Возникла ошибка при замене элемента");
        }
        DateChange = new Date();
    }

    /**
     * Удаляет элемент по его id.
     * @param s id удаляемого элемента.
     */
    public void remove_by_id(String s) {
        int startSize = products.size();
        if (products.size() > 0) {
            for (Product p : products) {
                if (p.getId() == Integer.parseInt(s)) {
                    products.remove(p);
                    System.out.println("Элемент коллекции успешно удалён.");
                    break;
                }
            }
            if (startSize == products.size()) {
                System.out.println("Элемент с id " + s + " не существует.");
            }
        } else System.out.println("Коллекция пуста.");
        DateChange = new Date();
    }

    /**
     * Очищает коллекцию.
     */
    public void clear() {
        products.clear();
        System.out.println("Коллекция очищена.");
        DateChange = new Date();
    }

    /**
     * Сохраняет все изменения коллекции в открытый файл.
     * @throws IOException
     */
    public void save() throws IOException {
        FileWriter fileWriter = new FileWriter(jsonFile);
        try {
            fileWriter.write(gson.toJson(products));
            //System.out.println(gson.toJson(products));
            fileWriter.flush();
            System.out.println("Файл успешно сохранён.");
        } catch (Exception ex) {
            System.out.println("При записи файла что-то пошло не так.");
        } finally {
            fileWriter.close();
        }
        DateSave = new Date();
    }

    /**
     * Выполняет скрипт записанный в файле.
     * В программе стоит ограничение на выполнение рекурсивных итераций в цикле - 20 вложенных циклов. Мы не рекомендуем вызывать скрипты в самом скрипте.
     * @param addres адрес скрипта в системе.
     * @throws IOException
     */
    public void execute_script(String addres) throws IOException {
        if (scriptCounter < SCRIPT_LIMIT) {
            int thisCount = scriptCounter;
            File script = new File(addres);

            if (!script.exists() || !script.isFile()) {
                System.out.println(("Файл по указанному пути (" + script.getAbsolutePath() + ") не существует."));
                return;
            }
            if (!script.canRead()) {
                System.out.println("Файл защищён от чтения.");
                return;
            }
            if (script.length() == 0) {
                System.out.println("Скрипт не содержит команд.");
                return;
            }
            try (BufferedReader scriptReader = new BufferedReader(new FileReader(script))) {
                scriptCounter++;
                String scriptCommand = scriptReader.readLine();
                String[] finalScriptCommand;
                while (scriptCommand != null) {
                    finalScriptCommand = scriptCommand.trim().split(" ", 2);
                    try {
                        switch (finalScriptCommand[0]) {
                            case "":
                                break;
                            case "help":
                                this.help();
                                break;
                            case "info":
                                this.info();
                                break;
                            case "show":
                                this.show();
                                break;
                            case "add":
                                this.add(finalScriptCommand[1]);
                                break;
                            case "update_id":
                                this.update_id(finalScriptCommand[1]);
                                break;
                            case "remove_by_id":
                                this.remove_by_id(finalScriptCommand[1]);
                                break;
                            case "clear":
                                this.clear();
                                break;
                            case "save":
                                this.save();
                                break;
                            case "execute_script":
                                this.execute_script(finalScriptCommand[1]);
                                break;
                            case "exit":
                                System.exit(0);
                                //  scriptCommand = null;
                                return;
                            case "add_if_min ":
                                this.add_if_min(finalScriptCommand[1]);
                                break;
                            case "remove_greater":
                                this.remove_greater(finalScriptCommand[1]);
                                break;
                            case "remove_lower":
                                this.remove_lower(finalScriptCommand[1]);
                                break;
                            case "group_counting_by_coordinates":
                                this.group_counting_by_coordinates();
                                break;
                            case "filter_contains_name":
                                this.filter_contains_name(finalScriptCommand[1]);
                                break;
                            case "print_field_descending_price":
                                this.print_field_descending_price();
                                break;
                            default:
                                System.out.println("Неопознанная команда.");
                        }
                    } catch (ArrayIndexOutOfBoundsException ex) {
                        System.out.println("Отсутствует аргумент." + finalScriptCommand[0]);
                    }
                    scriptCommand = scriptReader.readLine();
                }
                if (thisCount == 0) {
                    scriptCounter = 0;
                    System.out.println("Скрипт выполнен");
                }
            }
        } else {
            System.out.println("Не делай так. Количество вложенных скриптов превысило " + SCRIPT_LIMIT + ". Вызов вложенных скриптов остановлен.");
        }
    }

    /**
     * Добавляет новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции.
     * @param s сторка элемента в формате json.
     */
    public void add_if_min(String s) {
        try {
            if (products.size() != 0) {
                Product addProduct = gson.fromJson(s, Product.class);
                Product minElem = Collections.min(products);
                if (addProduct.compareTo(minElem) < 0) {
                    add(s);
                } else System.out.println("Элемент не минимальный!");
            } else System.out.println("Коллекция пуста, минимальный элемент отсутствует");
        } catch (JsonSyntaxException ex) {
            System.out.println("Возникла ошибка синтаксиса Json. Элемент не был добавлен");
        }
        DateChange = new Date();
    }

    /**
     * Удаляет из коллекции все элементы, превышающие заданный.
     * @param s сторка элемента в формате json.
     */
    public void remove_greater(String s) {
        try {
            int startSize = products.size();
            if (startSize != 0) {
                Product maxProduct = gson.fromJson(s, Product.class);
                //    for (Product product : products) {
                //  if (product.compareTo(maxProduct) > 0) {
                //  System.out.println("Элемент с id " + product.getId() + " удалён из коллекции");
                //         products.remove(product);
                products.removeIf(p -> (p != null && p.compareTo(maxProduct) > 0));
                System.out.println("Удалено " + (startSize - products.size()) + " элементов");
                // }
                //   }
            } else System.out.println("Коллекция пуста.");
        } catch (JsonSyntaxException ex) {
            System.out.println("Возникла ошибка синтаксиса Json.");
        }
        DateChange = new Date();
    }

    /**
     * Удаляет из коллекции все элементы, меньшие, чем заданный.
     * @param s сторка элемента в формате json.
     */
    public void remove_lower(String s) {
        try {
            int startSize = products.size();
            if (startSize != 0) {
                Product maxProduct = gson.fromJson(s, Product.class);
                // for (Product product : products) {
                //     if (product.compareTo(maxProduct) < 0) {
                //         System.out.println("Элемент с id " + product.getId() + " удалён из коллекции");
                //         products.remove(product);
                //     }
                //  }
                products.removeIf(p -> (p != null && p.compareTo(maxProduct) < 0));
                System.out.println("Удалено " + (startSize - products.size()) + " элементов");
            } else System.out.println("Коллекция пуста");
        } catch (JsonSyntaxException ex) {
            System.out.println("Возникла ошибка синтаксиса Json.");
        }
        DateChange = new Date();
    }

    /**
     * Группирует элементы коллекции по кординатам на 4 четверти.
     */
    public void group_counting_by_coordinates() {
        if (!products.isEmpty()) {
            for (int i = 1; i <= 4; i++) {
                System.out.println("Элементы четверти " + i);
                for (Product p : products) {
                    float x = p.getCoordinates().getX();
                    double y = p.getCoordinates().getY();
                    switch (i) {
                        case 1:
                            if (x >= 0 & y >= 0)
                                System.out.println(gson.toJson(p));
                            break;
                        case 2:
                            if (x < 0 & y >= 0)
                                System.out.println(gson.toJson(p));
                            break;
                        case 3:
                            if (x < 0 & y < 0)
                                System.out.println(gson.toJson(p));
                            break;
                        case 4:
                            if (x >= 0 & y < 0)
                                System.out.println(gson.toJson(p));
                            break;
                    }
                }
            }
        } else System.out.println("Коллекция пуста.");
    }

    /**
     * Выводит элементы, значение поля name которых содержит заданную подстроку.
     * @param s значение name для поиска.
     */
    public void filter_contains_name(String s) {
        int findProdukts = 0;
        if (products.size() > 0) {
            if (!s.isEmpty() && s != null) {
                for (Product p : products) {
                    if (p.getName().contains(s)) {
                        System.out.println(gson.toJson(p));
                        findProdukts++;
                    }
                }
                System.out.println("Всего найдено " + findProdukts + " элементов.");
            } else System.out.println("Ошибка ввода имени.");
        } else System.out.println("Коллекция пуста.");
    }

    /**
     * Выводит коллекцию, отсортированную по цене в порядке убывания.
     */
    public void print_field_descending_price() {
        if (!products.isEmpty()) {
            ArrayList<Product> list = new ArrayList<>();
            for (Product p : products) {
                list.add(p);
            }
            list.sort((o1, o2) -> (int) ((o1.getPrice() - o2.getPrice()) * 100));
            System.out.printf("%-12s%5s%n", "ID", "Price");
            for (Product p : list) {
                System.out.printf("%-12d%5.2f%n", p.getId(), p.getPrice());
            }
        } else System.out.println("Коллекция пуста.");
    }

    /**
     * Выводит информацию о загруженной коллекции.
     */
    public void info() {
        System.out.println("Дата загрузки: " + DateInitialization +
                "\nДата сохранения: " + DateSave +
                "\nДата изменения: " + DateChange +
                "\nТип коллекции: " + products.getClass() +
                "\nКоличество элементов: " + products.size());
    }

    public void load_collection() throws IOException {
        int startSize = products.size();
        if (!jsonFile.exists()) {
            System.out.println(("Файл по указанному пути (" + jsonFile.getAbsolutePath() + ") не существует."));
            return;
        }
        if (!jsonFile.canRead() || !jsonFile.canWrite()) {
            System.out.println("Файл защищён от чтения и(или) записи. Для работы коректной программы нужны оба разрешения.");
            return;
        }
        if (jsonFile.length() == 0) {
            System.out.println("Файл пуст. Возможно только добавление элементов в коллекцию.");
            return;
        }
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(jsonFile))) {
            System.out.println("Идет загрузка коллекции из файла " + jsonFile.getAbsolutePath());
            StringBuilder stringBuilder = new StringBuilder();
            String nextString;
            while ((nextString = bufferedReader.readLine()) != null) {
                stringBuilder.append(nextString);
            }
            Type typeOfCollectoin = new TypeToken<HashSet<Product>>() {
            }.getType();
            try {
                HashSet<Product> addedProduct = gson.fromJson(stringBuilder.toString(), typeOfCollectoin);
                for (Product p : addedProduct) {
                    if (checkNull(p)) {
                        throw new JsonSyntaxException("");
                    }
                    products.add(p);
                }
            } catch (JsonSyntaxException e) {
                System.out.println("Ошибка синтаксиса Json. Файл не может быть загружен.");
                System.exit(666);
            }
            System.out.println("Коллекций успешно загружена. Добавлено " + (products.size() - startSize) + " элементов.");
        }
    }

    /**
     * Закрывает программу без сохранения.
     */
    public void exit(){

    }


}
