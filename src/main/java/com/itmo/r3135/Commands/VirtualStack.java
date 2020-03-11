package com.itmo.r3135.Commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Класс для извлечения команд из скрипта.
 */
public class VirtualStack {
    private ArrayList<File> activeScriptList;
    private ArrayList<String> commandStack;
    private File currentFile;

    {
        activeScriptList = new ArrayList<>();
        commandStack = new ArrayList<>(1000);
    }

    /**
     * Класс для извлечения команд из скрипта.
     */
    protected ArrayList stackGenerate(String scriptAddress) throws IOException {
        int i = 0;
        if (checkFile(scriptAddress) == null) {
            return commandStack;
        }
        commandStack.addAll(i, readFile(checkFile(scriptAddress)));
        int stackSize = commandStack.size();
        while (i < stackSize) {
            if (commandCheck(commandStack.get(i))) {
                scriptAddress = getAddressScript(commandStack.get(i));
                commandStack.remove(commandStack.get(i));
                if (checkFile(scriptAddress) == null) {
                    commandStack.remove(i);
                } else {
                    insertScript(readFile(checkFile(scriptAddress)), i);
                }
                stackSize = commandStack.size();
            } else i++;
        }
        return commandStack;
    }

    private LinkedList readFile(File script) throws IOException {
        currentFile = script;
        LinkedList<String> CommandList = new LinkedList<>();
        if (activeScriptList.indexOf(script) == -1) {
            activeScriptList.add(script);
            try (BufferedReader scriptReader = new BufferedReader(new FileReader(script))) {
                String scriptCommand = scriptReader.readLine();
                while (scriptCommand != null) {
                    if (commandCheck(scriptCommand)) {
                        scriptCommand = relativeToAbsolutePath(scriptCommand);
                    }
                    CommandList.addLast(scriptCommand);
                    scriptCommand = scriptReader.readLine();
                }
            }
        } else {
            System.out.println("Обнаружен цикл!");
            while (activeScriptList.size() - activeScriptList.indexOf(script) > 2)
                activeScriptList.remove(activeScriptList.size() - 1);
        }
        return CommandList;
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

    private String getAddressScript(String command) {
        String[] trimScriptCommand;
        trimScriptCommand = command.trim().split(" ", 2);
        return trimScriptCommand[1];
    }

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