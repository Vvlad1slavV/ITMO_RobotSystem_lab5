
package com.itmo.r3135.Commands;

import com.itmo.r3135.Control;
import com.itmo.r3135.World.Product;

import java.util.ArrayList;
import java.util.HashSet;

public class PrintFieldDescendingPriceCommand extends AbstractCommand {

    public PrintFieldDescendingPriceCommand(Control control) {
        super(control);
    }

    /**
     * Выводит коллекцию, отсортированную по цене в порядке убывания.
     */
    @Override
    public void activate(String input) {
        HashSet<Product> products = control.getProducts();
        if (!products.isEmpty()) {
            ArrayList<Product> list = new ArrayList<>();
            for (Product p : products) {
                list.add(p);
            }
            list.sort((o1, o2) -> (int) ((o1.getPrice() - o2.getPrice()) * 100));
            System.out.printf("%-12s%5s%n", "ID", "Price");
            for (Product p : list) {
                System.out.printf("%-12d%5.2f%n", p.getId(), p.getPrice());
            }
        } else System.out.println("Коллекция пуста.");
    }
}
