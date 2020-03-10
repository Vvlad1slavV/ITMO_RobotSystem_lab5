package com.itmo.r3135;

import com.google.gson.*;
import com.itmo.r3135.Commands.*;
import com.itmo.r3135.World.Product;

import java.io.*;
import java.util.*;

/**
 * Класс хранящий в себе команды.
 */
public class Control implements Mediator {
    private LinkedList<File> activeScriptList;
    private Gson gson;
    private File jsonFile;
    private HashSet<Product> products;
    private Date dateInitialization;
    public Date dateSave;
    private Date dateChange;

    private LoadCollectionCommand loadCollectionCommand;
    private AddCommand addCommand;
    private ShowCommand showCommand;
    private UpdeteIdCommand updeteIdCommand;
    private HelpCommand helpCommand;
    private RemoveByIdCommand removeByIdCommand;
    private GroupCountingByCoordinatesCommand groupCountingByCoordinatesCommand;
    private AddIfMinCommand addIfMinCommand;
    private ClearCommand clearCommand;
    private PrintFieldDescendingPriceCommand printFieldDescendingPriceCommand;
    private FilterContainsNameCommand filterContainsNameCommand;
    private RemoveLowerCommand removeLowerCommand;
    private RemoveGreaterCommand removeGreaterCommand;
    private ExitCommand exitCommand;
    private ExecuteScriptCommand executeScriptCommand;
    private SaveCommand saveCommand;

    //control methods
    {
        activeScriptList = new LinkedList<>();
        gson = new Gson();
        products = new HashSet();

        addCommand = new AddCommand(this);
        showCommand = new ShowCommand(this);
        updeteIdCommand = new UpdeteIdCommand(this);
        helpCommand = new HelpCommand(this);
        removeByIdCommand = new RemoveByIdCommand(this);
        groupCountingByCoordinatesCommand = new GroupCountingByCoordinatesCommand(this);
        addIfMinCommand = new AddIfMinCommand(this);
        loadCollectionCommand = new LoadCollectionCommand(this);
        clearCommand = new ClearCommand(this);
        printFieldDescendingPriceCommand = new PrintFieldDescendingPriceCommand(this);
        filterContainsNameCommand = new FilterContainsNameCommand(this);
        removeLowerCommand = new RemoveLowerCommand(this);
        removeGreaterCommand = new RemoveGreaterCommand(this);
        exitCommand = new ExitCommand(this);
        executeScriptCommand = new ExecuteScriptCommand(this);
        saveCommand = new SaveCommand(this);
    }

    public Control(String filePath) {
        if (filePath == null) {
            System.out.println("Путь к файлу json не обнаружен.");
            System.exit(1);
        }
        File jsonPath = new File(filePath);
        if (jsonPath.exists()) {
            this.jsonFile = jsonPath;
            System.out.println("Адрес " + this.jsonFile.toString() + " успешно обнаружен");
        } else {
            System.out.println("Указанного пути не существует.");
            System.exit(1);
        }
        if (!jsonPath.isFile()) {
            System.out.println("Путь " + jsonPath.toString() + " не содержит имени файла");
            System.exit(1);
        } else {
            System.out.println("Файл " + jsonPath.toString() + " успещно обнаружен.");
        }
        if (!(filePath.lastIndexOf(".json") == filePath.length() - 5)) {
            System.out.println("Заданный файл не в формате .json");
            System.exit(1);
        }
        loadCollectionCommand.activate();
        dateInitialization = dateSave = dateChange = new Date();
    }

    /**
     * Выводит информацию о загруженной коллекции.
     */
    public void info() {
        System.out.println("Дата загрузки: " + dateInitialization +
                "\nДата сохранения: " + dateSave +
                "\nДата изменения: " + dateChange +
                "\nТип коллекции: " + products.getClass() +
                "\nКоличество элементов: " + products.size());
    }

    @Override
    public void notify(String textCommand) {
        String[] trimCommand = textCommand.trim().split(" ", 2);
        try {
            try {
                switch (trimCommand[0]) {
                    case "":
                        System.out.println("Команда отсутствует");
                        break;
                    case "help":
                        helpCommand.activate();
                        break;
                    case "info":
                        info();
                        break;
                    case "show":
                        showCommand.activate();
                        break;
                    case "add":
                        addCommand.activate(trimCommand[1]);
                        dateChange = new Date();
                        break;
                    case "update":
                        updeteIdCommand.activate(trimCommand[1]);
                        dateChange = new Date();
                        break;
                    case "remove_by_id":
                        removeByIdCommand.activate(trimCommand[1]);
                        dateChange = new Date();
                        break;
                    case "clear":
                        clearCommand.activate();
                        dateChange = new Date();
                        break;
                    case "save":
                        saveCommand.activate();
                        break;
                    case "execute_script":
                        executeScriptCommand.activate(trimCommand[1]);
                        break;
                    case "exit":
                        exitCommand.activate();
                        break;
                    case "add_if_min":
                        addIfMinCommand.activate(trimCommand[1]);
                        dateChange = new Date();
                        break;
                    case "remove_greater":
                        removeGreaterCommand.activate(trimCommand[1]);
                        dateChange = new Date();
                        break;
                    case "remove_lower":
                        removeLowerCommand.activate(trimCommand[1]);
                        dateChange = new Date();
                        break;
                    case "group_counting_by_coordinates":
                        groupCountingByCoordinatesCommand.activate();
                        break;
                    case "filter_contains_name":
                        filterContainsNameCommand.activate(trimCommand[1]);
                        break;
                    case "print_field_descending_price":
                        printFieldDescendingPriceCommand.activate();
                        break;
                    default:
                        System.out.println("Неопознанная команда. Наберите 'help' для получения доступных команд.");
                }
            } catch (NumberFormatException ex) {
                System.out.println("Где-то проблема с форматом записи числа.Команда не выполнена");
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Отсутствует аргумент.");
        }
    }

    public File getJsonFile() {
        return jsonFile;
    }

    public HashSet<Product> getProducts() {
        return products;
    }

}
