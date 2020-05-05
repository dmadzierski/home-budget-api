package pl.test_tool.error;

public enum ServerError implements HibernateConstraintError {
  IS_NOT_YOU_PROPERTY("There is no your property", "Server Error");


  private String message;
  private String fileName;

  ServerError (String message, String fieldName) {
    this.message = message;
    this.fileName = fieldName;
  }

  @Override
  public String getMessage () {
    return message;
  }

  @Override
  public String getFieldName () {
    return fileName;
  }
}
