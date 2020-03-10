package com.itmo.r3135.Commands;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.itmo.r3135.Control;
import com.itmo.r3135.World.Product;

import java.util.HashSet;
import java.util.Random;

public class AddCommand extends AbstractCommand {
    public AddCommand(Control control) {
        super(control);
    }
    /**
     * Добавляет элемент в коллекцию.
     *
     * @param jsonString строка в элемента в формате json.
     */
    @Override
    public void activate(String jsonString) {
        HashSet<Product> products = this.control.getProducts();
        Gson gson = new Gson();
        try {
            Product addProduct = gson.fromJson(jsonString, Product.class);
            addProduct.setCreationDate(java.time.LocalDateTime.now());
            addProduct.setId(uniqueoIdGeneration(products));
            if (addProduct.checkNull()) {
                System.out.println("Элемент не удовлетворяет требованиям коллекции");
            } else if (products.add(addProduct)) {
                System.out.println("Элемент успешно добавлен.");
            }
        } catch (JsonSyntaxException ex) {
            System.out.println("Возникла ошибка синтаксиса Json. Элемент не был добавлен");
        }
    }

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

}
