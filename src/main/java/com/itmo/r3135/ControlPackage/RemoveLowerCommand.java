package com.itmo.r3135.ControlPackage;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.itmo.r3135.World.Product;

import java.util.HashSet;

public class RemoveLowerCommand extends AbstractCommand {

    public RemoveLowerCommand(Control control) {
        super(control);
    }

    /**
     * Удаляет из коллекции все элементы, меньшие, чем заданный.
     *
     * @param jsonString сторка элемента в формате json.
     */
    @Override
    public void activate(String jsonString) {
        HashSet<Product> products = control.getProducts();
        Gson gson = new Gson();
        try {
            int startSize = products.size();
            if (startSize != 0) {
                Product maxProduct = gson.fromJson(jsonString, Product.class);
                products.removeIf(p -> (p != null && p.compareTo(maxProduct) < 0));
                System.out.println("Удалено " + (startSize - products.size()) + " элементов");
            } else System.out.println("Коллекция пуста");
        } catch (JsonSyntaxException ex) {
            System.out.println("Возникла ошибка синтаксиса Json.");
        }
    }
}
