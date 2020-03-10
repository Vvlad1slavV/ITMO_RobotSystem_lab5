package com.itmo.r3135.Commands;

import com.itmo.r3135.Control;

public class ExitCommand extends AbstractCommand {
    public ExitCommand(Control control) {
        super(control);
    }

    /**
     * Закрывает программу без сохранения.
     */
    @Override
    public void activate(String input) {
        System.out.println("Работа программы завершена");
        System.exit(0);
    }
}
