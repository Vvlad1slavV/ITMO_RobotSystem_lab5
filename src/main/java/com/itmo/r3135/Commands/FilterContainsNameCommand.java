package com.itmo.r3135.Commands;

import com.itmo.r3135.Control;
import com.itmo.r3135.World.Product;

import java.util.HashSet;

/**
 * Класс обработки комадны filter_contains_name
 */
public class FilterContainsNameCommand extends AbstractCommand {
    public FilterContainsNameCommand(Control control) {
        super(control);
    }

    /**
     * Выводит элементы, значение поля name которых содержит заданную подстроку.
     *
     * @param name значение name для поиска.
     */
    @Override
    public void activate(String name) {
        HashSet<Product> products = control.getProducts();
        int findProdukts = 0;
        if (products.size() > 0) {
            if (!name.isEmpty() && name != null) {
                for (Product p : products) {
                    if (p.getName().contains(name)) {
                        System.out.println(p);
                        findProdukts++;
                    }
                }
                System.out.println("Всего найдено " + findProdukts + " элементов.");
            } else System.out.println("Ошибка ввода имени.");
        } else System.out.println("Коллекция пуста.");
    }
}
