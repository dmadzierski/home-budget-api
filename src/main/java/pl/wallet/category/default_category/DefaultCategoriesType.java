package pl.wallet.category.default_category;

import pl.wallet.category.Category;
import pl.wallet.transaction.TransactionType;

import static pl.wallet.transaction.TransactionType.EXPENSE;
import static pl.wallet.transaction.TransactionType.REVENUES;

public enum DefaultCategoriesType {
  FOOD("Food", "Good expense", EXPENSE),
  SALARY("Salary", REVENUES);


  private Category category;

  DefaultCategoriesType (String name, TransactionType transactionType) {
    this.category = new Category();
    category.setName(name);
    category.setTransactionType(transactionType);
  }

  DefaultCategoriesType (String name, String description, TransactionType transactionType) {
    this(name, transactionType);
    category.setDescription(description);
  }

  public Category getCategory () {
    return category;
  }
}
