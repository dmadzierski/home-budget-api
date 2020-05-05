package pl.test_tool.error;

public enum TransactionError implements HibernateConstraintError {
  ID_NOT_NULL("New transaction can not have id", "id"),
  NAME_NOT_NULL("Transaction must have name", "name"),
  ;

  private String fieldName;
  private String message;

  TransactionError (String message, String fieldName) {
    this.fieldName = fieldName;
    this.message = message;
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
