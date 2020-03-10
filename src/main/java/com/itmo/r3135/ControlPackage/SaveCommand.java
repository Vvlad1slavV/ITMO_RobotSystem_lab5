package com.itmo.r3135.ControlPackage;

import com.google.gson.Gson;
import com.itmo.r3135.World.Product;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;

public class SaveCommand extends AbstractCommand {
    public SaveCommand(Control control) {
        super(control);
    }

    /**
     * Сохраняет все изменения коллекции в открытый файл.
     */
    @Override
    public void activate(String input) {
        HashSet<Product> products = control.getProducts();
        File jsonFile = control.getJsonFile();
        Gson gson = new Gson();
        try {
            if (!jsonFile.exists()) {
                System.out.println(("Невозможно сохранить файл. Файл по указанному пути (" + jsonFile.getAbsolutePath() + ") не существует."));
            } else if (!jsonFile.canRead() || !jsonFile.canWrite()) {
                System.out.println("Невозможно сохранить файл. Файл защищён от чтения и(или) записи.");
            } else {
                FileWriter fileWriter = new FileWriter(jsonFile);
                try {
                    fileWriter.write(gson.toJson(products));
                    fileWriter.flush();
                    System.out.println("Файл успешно сохранён.");
                } catch (Exception ex) {
                    System.out.println("При записи файла что-то пошло не так.");
                } finally {
                    fileWriter.close();
                }
            }
            control.dateSave = new Date();
        } catch (IOException e) {
            System.out.println("Возникла ошибка работы с файлом");
        }
    }
}
