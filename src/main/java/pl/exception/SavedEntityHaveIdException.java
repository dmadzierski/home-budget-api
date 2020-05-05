package pl.exception;


@Deprecated(forRemoval = true)
public class SavedEntityHaveIdException extends RuntimeException {
  public SavedEntityHaveIdException (Class clazz) {
    super("New " + clazz.getSimpleName() + " can not have id");
  }
}
