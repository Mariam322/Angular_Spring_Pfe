package tn.iit.exception;

public class LignePieceCommercialeNotFoundException extends RuntimeException {
    public LignePieceCommercialeNotFoundException() {
        super();
    }

    public LignePieceCommercialeNotFoundException(String message) {
        super(message);
    }
}