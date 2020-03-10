package com.itmo.r3135;

import com.google.gson.Gson;
import com.itmo.r3135.Exception.RecursionCycleException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Stack;

public class VirtualStack {
    private LinkedList<File> activeScriptList;
    private ArrayList<String> virtualstack;

    {
        activeScriptList = new LinkedList<>();
        virtualstack = new ArrayList<>(1000);
    }

    public ArrayList stackgenerate(String scriptAddress) throws IOException {
        Integer i = 0;
        if (checkFile(scriptAddress)==null){
            return null;
        };
        virtualstack.addAll(i, readfile(checkFile(scriptAddress)));
        Integer w = virtualstack.size();
        try {
            while (i < w) {
                if (commandCheck(virtualstack.get(i))) {
                    scriptAddress = getAddressScript(virtualstack.get(i));
                    virtualstack.remove(virtualstack.get(i));
                    if (checkFile(scriptAddress)==null){
                        System.out.println("Битая ссылка на скрипт " + scriptAddress);
                        break;
                    };
                    insertScript(readfile(checkFile(scriptAddress)), i);
                    w = virtualstack.size();
                } else i++;
            }
        } catch (RecursionCycleException e){
            System.out.println(e);
            return null;
        }
        return virtualstack;
    }

    private LinkedList readfile(File script) throws IOException, RecursionCycleException {
        LinkedList<String> CommandList = new LinkedList<>();
        if (activeScriptList.lastIndexOf(script) == -1) {
            activeScriptList.add(script);
            try (BufferedReader scriptReader = new BufferedReader(new FileReader(script))) {
                String scriptCommand = scriptReader.readLine();;
                while (scriptCommand != null) {
                    CommandList.addLast(scriptCommand);
                    scriptCommand = scriptReader.readLine();
                }
            }
        } else {
            throw new RecursionCycleException();
        }
        return CommandList;
    }


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
        if (command==null) return false;
        String[] trimScriptCommand;
        trimScriptCommand = command.trim().split(" ", 2);
        if (trimScriptCommand[0].equals("execute_script")) {
            return true;
        } else
            return false;
    }

    private void insertScript(LinkedList commandList, Integer index) {
        virtualstack.addAll(index, commandList);
    }


}