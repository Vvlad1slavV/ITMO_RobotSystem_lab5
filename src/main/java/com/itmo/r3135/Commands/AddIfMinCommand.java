package com.itmo.r3135.Commands;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.itmo.r3135.Control;
import com.itmo.r3135.World.Product;

import java.util.Collections;
import java.util.HashSet;

public class AddIfMinCommand extends AbstractCommand {
    public AddIfMinCommand(Control control) {
        super(control);
    }
    /**
     * Добавляет новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции.
     *
     * @param jsonString сторка элемента в формате json.
     */
    @Override
    public void activate(String jsonString) {
        HashSet<Product> products = control.getProducts();
        Gson gson = new Gson();
        try {
            if (products.size() != 0) {
                Product addProduct = gson.fromJson(jsonString, Product.class);
                Product minElem = Collections.min(products);
                if (addProduct.compareTo(minElem) < 0) {
                    control.processing("add " + jsonString);
                } else System.out.println("Элемент не минимальный!");
            } else System.out.println("Коллекция пуста, минимальный элемент отсутствует.");
        } catch (JsonSyntaxException ex) {
            System.out.println("Возникла ошибка синтаксиса Json. Элемент не был добавлен");
        }
    }
}
