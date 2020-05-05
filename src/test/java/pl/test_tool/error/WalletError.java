package pl.test_tool.error;

public enum WalletError implements HibernateConstraintError {
  ID_NULL("New wallet can not have id", "id"),
  NAME_NOT_NULL("Wallet must have name", "name"),
  BALANCE_NOT_NULL("Wallet must have balance", "balance");

  private String message;
  private String fieldName;

  WalletError (String message, String fieldName) {
    this.message = message;
    this.fieldName = fieldName;
  }

  @Override
  public String getMessage () {
    return this.message;
  }

  @Override
  public String getFieldName () {
    return fieldName;
  }
}
