package com.itmo.r3135;

import com.google.gson.Gson;

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
        virtualstack = new ArrayList<>();
    }

    public String stackgenerate(String scriptAddress) throws IOException {
        File script = new File(scriptAddress);
        if (!script.exists() || !script.isFile()) {
            System.out.println(("Файл по указанному пути (" + script.getAbsolutePath() + ") не существует."));
            return "";
        }
        if (!script.canRead()) {
            System.out.println("Файл защищён от чтения.");
            return "";
        }

        if (activeScriptList.lastIndexOf(script) == -1) {
            activeScriptList.add(script);
            try (BufferedReader scriptReader = new BufferedReader(new FileReader(script))) {
                String scriptCommand = scriptReader.readLine();
                while (scriptCommand != null) {
                    virtualstack.add(scriptCommand);
                    }
                    scriptCommand = scriptReader.readLine();
                }
                System.out.println("Выполнен скрипт " + script.toString());
                activeScriptList.removeLast();
            } else System.out.println("Обнаружен цикл в исполнии скриптов.");
        return "";
        }


    private LinkedList readfile(File script) throws IOException{
    LinkedList<String> CommandList = new LinkedList<>();
        if (activeScriptList.lastIndexOf(script) == -1) {
            activeScriptList.add(script);
            try (BufferedReader scriptReader = new BufferedReader(new FileReader(script))) {
                String scriptCommand = "";
                while (scriptCommand != null) {
                    scriptCommand = scriptReader.readLine();
                    CommandList.addLast(scriptCommand);
                }
                System.out.println("Выполнен скрипт " + script.toString());
                activeScriptList.removeLast();
            }
        } else System.out.println("Обнаружен цикл в исполнии скриптов.");
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




}