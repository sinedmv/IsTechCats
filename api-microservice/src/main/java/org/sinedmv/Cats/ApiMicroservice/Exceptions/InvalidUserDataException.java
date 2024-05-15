package org.sinedmv.Cats.ApiMicroservice.Exceptions;

public class InvalidUserDataException extends Exception {
    public InvalidUserDataException(String message) {
        super(message);
    }
}
