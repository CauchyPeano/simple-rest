package de.konoplyanko.test.processing;

public class RecordProcessingError extends RuntimeException {

    public RecordProcessingError(String message, Throwable cause) {
        super(message, cause);
    }
}
