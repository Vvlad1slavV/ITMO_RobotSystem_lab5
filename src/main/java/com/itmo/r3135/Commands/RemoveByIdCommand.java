package com.itmo.r3135.Commands;

import com.itmo.r3135.Control;
import com.itmo.r3135.World.Product;

import java.util.HashSet;

public class RemoveByIdCommand extends AbstractCommand {


    public RemoveByIdCommand(Control control) {
        super(control);
    }
    /**
     * Удаляет элемент по его id.
     *
     * @param id id удаляемого элемента.
     */
    @Override
    public void activate(String input) {
        HashSet<Product> products = control.getProducts();
        int startSize = products.size();
        if (products.size() > 0) {
            int id = Integer.parseInt(input);
            for (Product p : products) {
                if (p.getId() == id) {
                    products.remove(p);
                    System.out.println("Элемент коллекции успешно удалён.");
                    break;
                }
            }
            if (startSize == products.size()) {
                System.out.println("Элемент с id " + id + " не существует.");
            }
        } else System.out.println("Коллекция пуста.");
    }
}
