package com.itmo.r3135.Exception;

public class RecursionCycleException extends RuntimeException {
    public RecursionCycleException(){
        super("Обнаружен рекрсивный цикл в скрипах.");
    }
}
