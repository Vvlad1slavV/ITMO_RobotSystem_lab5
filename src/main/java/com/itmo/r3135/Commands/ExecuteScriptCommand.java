package com.itmo.r3135.Commands;

import com.itmo.r3135.Control;

import java.io.IOException;
import java.util.ArrayList;
/**
 * Класс обработки комадны execute_script
 */
public class ExecuteScriptCommand extends AbstractCommand {
    public ExecuteScriptCommand(Control control) {
        super(control);
    }

    /**
     * Выполняет скрипт записанный в файле.
     * В программе стоит ограничение на выполнение рекурсивных итераций в цикле - 20 вложенных циклов. Мы не рекомендуем вызывать скрипты в самом скрипте.
     *
     * @param addres адрес скрипта в системе.
     */
    @Override
    public void activate(String addres) {
        try {
            System.out.println("Начинается анализ скрипта. Это может занять некоторое время");
            VirtualStack virtualStack = new VirtualStack();
            ArrayList<String> commands = virtualStack.stackGenerate(addres);
            if (!commands.isEmpty()) {
                System.out.println("Запуск выполнения скрипта.");
                for (String command : commands) {
                    control.processing(command);
                }
                System.out.println("Скрипт выполнен.");
            } else {
                System.out.println("Невозможно прочитать скрипт или скрипт пуст.");
            }
        } catch (IOException e) {
            System.out.println("Ошибка работы с файлами.");
        }
    }

    /*
    public void executeScript(String addres) throws IOException {
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
        if (activeScriptList.lastIndexOf(script) == -1) {
            activeScriptList.add(script);
            try (BufferedReader scriptReader = new BufferedReader(new FileReader(script))) {
                String scriptCommand = scriptReader.readLine();
                String[] trimScriptCommand;
                while (scriptCommand != null) {
                    trimScriptCommand = scriptCommand.trim().split(" ", 2);
                    try {
                        switch (trimScriptCommand[0]) {
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
                                this.add(trimScriptCommand[1]);
                                break;
                            case "update_id":
                                this.updateId(trimScriptCommand[1]);
                                break;
                            case "remove_by_id":
                                this.removeById(trimScriptCommand[1]);
                                break;
                            case "clear":
                                this.clear();
                                break;
                            case "save":
                                this.save();
                                break;
                            case "execute_script":
                                this.executeScript(trimScriptCommand[1]);
                                break;
                            case "exit":
                                this.exit();
                                break;
                            case "add_if_min ":
                                this.addIfMin(trimScriptCommand[1]);
                                break;
                            case "remove_greater":
                                this.removeGreater(trimScriptCommand[1]);
                                break;
                            case "remove_lower":
                                this.removeLower(trimScriptCommand[1]);
                                break;
                            case "group_counting_by_coordinates":
                                this.groupCountingByCoordinates();
                                break;
                            case "filter_contains_name":
                                this.filterContainsName(trimScriptCommand[1]);
                                break;
                            case "print_field_descending_price":
                                this.printFieldDescendingPrice();
                                break;
                            default:
                                System.out.println("Неопознанная команда.");
                        }
                    } catch (ArrayIndexOutOfBoundsException ex) {
                        System.out.println("Отсутствует аргумент." + trimScriptCommand[0]);
                    }
                    scriptCommand = scriptReader.readLine();
                }
                System.out.println("Выполнен скрипт " + script.toString());
                activeScriptList.removeLast();
            }
        } else System.out.println("Обнаружен цикл в исполнии скриптов. Тело цикла выполнено один раз.");
    }

     */
}
