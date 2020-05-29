package pl.wallet;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.exception.EntityNotFoundException;
import pl.exception.ThereIsNoYourPropertyException;
import pl.user.User;
import pl.wallet.transaction.Transaction;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class WalletService {

  private WalletRepository walletRepository;

  public Wallet saveWallet (Wallet wallet) {
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

  public Wallet updateBalance (Wallet wallet, Transaction transaction) {
    wallet.addTransaction(transaction);
    return saveWallet(wallet);
  }

  public Wallet saveDefaultWallet (User user) {
    Wallet defaultWallet = createDefaultWallet();
    defaultWallet.addUser(user);
    defaultWallet.setOwnerEmail(user.getEmail());
    this.saveWallet(defaultWallet);
    return defaultWallet;
  }

  Wallet createDefaultWallet () {
    Wallet wallet = new Wallet();
    wallet.setName("Wallet");
    wallet.setBalance(BigDecimal.ZERO);
    return wallet;
  }
}
