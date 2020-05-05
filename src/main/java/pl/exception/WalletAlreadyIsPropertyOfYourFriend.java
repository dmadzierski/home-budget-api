package pl.exception;

public class WalletAlreadyIsPropertyOfYourFriend extends RuntimeException {
  public WalletAlreadyIsPropertyOfYourFriend (String message) {
    super(message);
  }
}
