package pl.test_tool.error;

public enum UserError implements HibernateConstraintError {
  ID_NOT_NULL("New user can not have id", "id"),
  EMAIL_UNIQUE("Email is used by another account", "email"),
  EMAIL_WELL_FORMED("Must be a well-formed email address", "email"),
  EMAIL_NOT_NULL("User must have email", "email"),
  PASSWORD_MIN_LENGTH("Password length must be longer than 5", "password"),
  PASSWORD_NOT_NULL("User must have password", "password");


  private String message;
  private String fieldName;

  UserError (String message, String fieldName) {
    this.message = message;
    this.fieldName = fieldName;
  }

  @Override
  public String getMessage () {
    return message;
  }

  @Override
  public String getFieldName () {
    return fieldName;
  }
}
