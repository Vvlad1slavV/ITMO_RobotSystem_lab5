package com.itmo.r3135.Commands;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.itmo.r3135.Control;
import com.itmo.r3135.World.Product;

import java.util.HashSet;
import java.util.Random;

/**
 * Класс обработки комадны add
 */
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
                printRequest();
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

    private void printRequest() {
        System.out.println("------------------------");
        System.out.println("Требования к элементу:");
        System.out.println("------------------------");
        System.out.println("Product: {\n" +
                "    String name --- Поле не может быть null, Строка не может быть пустой\n" +
                "    Coordinates coordinates; --- Поле не может быть null\n" +
                "    Long price --- Поле может быть null, Значение поля должно быть больше 0\n" +
                "    String partNumber --- Длина строки должна быть не меньше 21, Поле не может быть null\n" +
                "    manufactureCost --- Поле не может быть null;\n" +
                "    UnitOfMeasure unitOfMeasure --- Поле не может быть null\n" +
                "    Person owner --- Поле не может быть null\n" +
                "}\n" +
                "Coordinates: {\n" +
                "    Float x --- Максимальное значение поля: 82, Поле не может быть null\n" +
                "    Long y --- Значение поля должно быть больше -244, Поле не может быть null\n" +
                "}\n" +
                "Person: {\n" +
                "    String name --- Поле не может быть null, Строка не может быть пустой\n" +
                "    java.time.LocalDateTime birthday --- Поле не может быть null\n" +
                "    Color eyeColor --- Поле не может быть null\n" +
                "    Color hairColor --- Поле не может быть null\n" +
                "}\n" +
                "UnitOfMeasure: {\n" +
                "    PCS,\n" +
                "    LITERS,\n" +
                "    GRAMS,\n" +
                "    MILLIGRAMS;\n" +
                "}\n" +
                "Color: {\n" +
                "    GREEN,\n" +
                "    RED,\n" +
                "    BLACK,\n" +
                "    BLUE,\n" +
                "    YELLOW;\n}");
    }

}
