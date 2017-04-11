package com.infoshare.junit.banking;

public class InvalidTransactionException extends Exception {
    public static final String NOT_ENOUGH_FUNDS = "NOT_ENOUGH_FUNDS";
    public static final String EMPTY_TRANSACTION = "EMPTY_TRANSACTION";
    public static final String NEGATIVE_AMOUNT = "NEGATIVE_AMOUNT";

    public InvalidTransactionException() {
        super();
    }

    public InvalidTransactionException(String message) {
        super(message);
    }
}
