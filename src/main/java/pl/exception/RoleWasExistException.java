package pl.exception;

public class RoleWasExistException extends RuntimeException {
  public RoleWasExistException () {
    super("Role is exist");
  }
}
