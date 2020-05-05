package pl.exception;

public class CanNotNotAddOldFriendException extends RuntimeException {
  public CanNotNotAddOldFriendException (String message) {
    super(message);
  }
}
