package pl.exception;


@Deprecated(forRemoval = true)
public class SavedEntityCanNotHaveIdException extends RuntimeException {
  public SavedEntityCanNotHaveIdException (Class clazz) {
    super("New " + clazz.getSimpleName() + " can not have id");
  }
}
