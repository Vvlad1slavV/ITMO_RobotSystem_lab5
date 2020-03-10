package com.itmo.r3135.Commands;

import com.itmo.r3135.Control;
import com.itmo.r3135.World.Product;

import java.util.HashSet;

public class ClearCommand extends AbstractCommand {
    public ClearCommand(Control control) {
        super(control);
    }
    /**
     * Очищает коллекцию.
     */
    @Override
    public void activate(String input) {
        HashSet<Product> products = control.getProducts();
        products.clear();
        System.out.println("Коллекция очищена.");
    }
}
