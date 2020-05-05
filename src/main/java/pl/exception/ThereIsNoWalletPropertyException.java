package pl.exception;

public class ThereIsNoWalletPropertyException extends RuntimeException {
  public ThereIsNoWalletPropertyException (String message) {
    super(message);
  }
}
