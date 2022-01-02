package com.antra_kacena.calculator;

public enum OperatorType {
    PLUS("+"),
    MINUS("-"),
    MULTIPLY("*"),
    DIVIDE("/"),
    NONE("");

    public final String character;

    private OperatorType(String character) {
        this.character = character;
    }
}
