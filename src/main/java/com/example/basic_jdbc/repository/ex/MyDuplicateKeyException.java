package com.example.basic_jdbc.repository.ex;

public class MyDuplicateKeyException extends MyDbException {
    public MyDuplicateKeyException(String message) {
        super(message);
    }

    public MyDuplicateKeyException(Throwable cause) {
        super(cause);
    }

    public MyDuplicateKeyException() {
        super();
    }

    public MyDuplicateKeyException(String message, Throwable cause) {
        super(message, cause);
    }
}
