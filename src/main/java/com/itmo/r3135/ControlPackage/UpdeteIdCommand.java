package com.itmo.r3135.ControlPackage;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.itmo.r3135.World.Product;

import java.util.HashSet;

public class UpdeteIdCommand extends AbstractCommand {
    public UpdeteIdCommand(Control control) {
        super(control);
    }

    /**
     * Заменяет в колеекции элемент с определенным id.
     *
     * @param s строка, содержащая id заменяемого элемента коллекции и новый элементт в формате json. id и документ json разделены пробелом.
     */
    @Override
    public void activate(String s) {
        HashSet<Product> products = control.getProducts();
        Gson gson = new Gson();
        try {
            String id = s.split(" ", 2)[0];
            String elem = s.split(" ", 2)[1];
            Product newProduct = gson.fromJson(elem, Product.class);
            int startSize = products.size();
            if (newProduct.checkNull()) {
                System.out.println("Элемент не удовлетворяет требованиям коллекции");
            } else {
                control.notify("remove_by_id " + id);
                newProduct.setCreationDate(java.time.LocalDateTime.now());
                newProduct.setId(Integer.parseInt(id));
                if (startSize - products.size() == 1)
                    if (products.add(newProduct)) {
                        System.out.println("Элемент успешно обновлён.");
                    } else System.out.println("При замене элементов что-то пошло не так");
            }
        } catch (JsonSyntaxException ex) {
            System.out.println("Возникла ошибка при замене элемента");
        }
    }
}
