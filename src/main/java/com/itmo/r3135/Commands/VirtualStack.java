package com.itmo.r3135.Commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Класс для извлечения команд из скрипта для последующего выполнения.
 */
public class VirtualStack {
    private LinkedList<File> activeScriptList;
    private ArrayList<String> commandStack;
    private File currentFile;
    private String marker;
    {
        marker = " ..%!marker!.. ";
        activeScriptList = new LinkedList<>();
        commandStack = new ArrayList<>(100000);

    }

    /**
     * Омновной метод
     */
    protected ArrayList stackGenerate(String scriptAddress) throws IOException {
        int i = 0;
        if (checkFile(scriptAddress) == null) {
            return commandStack;
        }
        currentFile = new File(scriptAddress);
        activeScriptList.add(currentFile);
        commandStack.addAll(i, readFile(checkFile(scriptAddress)));
        int stackSize = commandStack.size();
        while (i < stackSize) {
            if (commandCheck(commandStack.get(i))) {
                String marker = getMarker(commandStack.get(i));
                String execute = deMarker(commandStack.get(i));
                File executeFile = new File(getAddressScript(execute));
                if (currentFile.equals(new File(marker))) {
                    if (activeScriptList.lastIndexOf(executeFile) == -1) {
                        activeScriptList.add(executeFile);
                        scriptAddress = getAddressScript(execute);
                        commandStack.remove(commandStack.get(i));
                        if (checkFile(scriptAddress) != null) {
                            currentFile = executeFile;
                            insertScript(readFile(checkFile(scriptAddress)), i);
                        }
                    } else {
                        System.out.println("Обнаружен цикл!");

                        commandStack.remove(i);
                    }
                } else {
                    activeScriptList.removeLast();
                    currentFile = activeScriptList.getLast();
                }
                stackSize = commandStack.size();
            } else i++;
        }
        return commandStack;
    }
    /**
     * Метод, формирующий список команд из файла
     */
    private LinkedList readFile(File script) throws IOException {
        // currentFile = script;
        LinkedList<String> CommandList = new LinkedList<>();

        try (BufferedReader scriptReader = new BufferedReader(new FileReader(script))) {
            System.out.println("Анализ файла " + script.getAbsolutePath());
            String scriptCommand = scriptReader.readLine();
            while (scriptCommand != null) {
                if (commandCheck(scriptCommand)) {
                    scriptCommand = relativeToAbsolutePath(scriptCommand);
                    scriptCommand = parentFileMarker(scriptCommand);
                }
                CommandList.addLast(scriptCommand);
                scriptCommand = scriptReader.readLine();
            }
        }

        return CommandList;
    }
    /**
     * Метод устанавливает метку файла, вызвавшего execute_script
     */
    private String parentFileMarker(String execute) {
        String markedString = execute + marker + currentFile;
        return markedString;
    }
    /**
     * Метод снимает метку файла, вызвавшего execute_script
     */
    private String deMarker(String execute) {
        String deMarkedString = execute.trim().split(marker, 2)[0];
        return deMarkedString;
    }
    /**
     * Метод возвращает метку файла, вызвавшего execute_script
     */
    private String getMarker(String execute) {
        String MarkedString = ((execute.trim().split(marker, 2))[1]);
        return MarkedString;
    }

    /**
     * Метод заменяет абсолютный путь на относителный
     */
    private String relativeToAbsolutePath(String nextExecute) {
        String nextFilePath = nextExecute.trim().split(" ", 2)[1];
        if (nextFilePath != null) {
            File nextFile = new File(nextFilePath);
            if (!nextFile.isAbsolute()) {
                String newExecute = "execute_script " + currentFile.getAbsolutePath().replace(currentFile.getName(), nextFile.getPath());
                return newExecute;
            }
        }
        return nextExecute;
    }

    /**
     * Метод проверки файла возвращает null, если есть проблемы
     */
    private File checkFile(String addres) {
        File script = new File(addres);

        if (!script.exists() || !script.isFile()) {
            System.out.println(("Файл по указанному пути (" + script.getAbsolutePath() + ") не существует."));
            return null;
        }
        if (!script.canRead()) {
            System.out.println("Файл защищён от чтения.");
            return null;
        }
        if (script.length() == 0) {
            System.out.println("Скрипт не содержит команд.");
            return null;
        }
        return script;
    }
    /**
     * Метод выделяет адрес файла из команды
     */
    private String getAddressScript(String command) {
        String[] trimScriptCommand;
        trimScriptCommand = command.trim().split(" ", 2);
        return trimScriptCommand[1];
    }
    /**
     * Метод проверяет команду на соответствие execute_script
     */
    private Boolean commandCheck(String command) {
        if (command != null) {
            String[] trimScriptCommand;
            trimScriptCommand = command.trim().split(" ", 2);
            if (trimScriptCommand[0].equals("execute_script"))
                return true;
        }
        return false;
    }

    private void insertScript(LinkedList commandList, Integer index) {
        commandStack.addAll(index, commandList);
    }


}