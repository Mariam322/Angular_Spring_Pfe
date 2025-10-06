package tn.iit.exception;

public class LigneDepenseNotFoundException extends RuntimeException {
    public LigneDepenseNotFoundException() {
        super();
    }

    public LigneDepenseNotFoundException(String message) {
        super(message);
    }
}