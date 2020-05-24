package pl.wallet;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.exception.EntityNotFoundException;
import pl.exception.ThereIsNoYourPropertyException;
import pl.user.User;
import pl.wallet.transaction.Transaction;

import java.util.List;

@Service
@AllArgsConstructor
public class WalletService {

  private WalletRepository walletRepository;

  Wallet saveWallet (Wallet wallet) {
    return walletRepository.save(wallet);
  }

  List<Wallet> getWalletsByUser (User user) {
    return walletRepository.getByUsers(user);
  }

  void removeWallet (Long walletId) {
    walletRepository.deleteById(walletId);
  }

  Wallet getWalletById (Long walletId) {
    return walletRepository.getById(walletId).orElseThrow(() -> new EntityNotFoundException(walletId, Wallet.class));
  }

  public Wallet isUserWallet (User user, Long walletId) {
    if(this.getWalletsByUser(user).stream().anyMatch(userWallet -> userWallet.getId().equals(walletId)))
      return getWalletById(walletId);
    throw new ThereIsNoYourPropertyException();
  }

  public Wallet addFriendToWallet (User friend, Long walletId) {
    Wallet wallet = getWalletById(walletId);
    wallet.addUser(friend);
    return walletRepository.save(wallet);
  }

  public Wallet removeFriendFromWallet (User friend, Long walletId) {
    Wallet wallet = getWalletById(walletId);
    wallet.removeUser(friend);
    return walletRepository.save(wallet);
  }

  public Wallet changeBalance (Wallet wallet, Transaction transaction) {
    wallet.setBalance(wallet.getBalance().subtract(transaction.getPrice()));
    return saveWallet(wallet);
  }
}
