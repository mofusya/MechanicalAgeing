package net.mofusya.mechanical_ageing.handlers;

public class IllegalHandlerGetterException extends RuntimeException {
    public IllegalHandlerGetterException() {
        this("");
    }

    public IllegalHandlerGetterException(String message) {
        super(message);
    }
}
