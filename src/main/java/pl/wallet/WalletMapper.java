package pl.wallet;

import pl.wallet.transaction.Transaction;
import pl.wallet.transaction.TransactionMapper;

import java.util.List;
import java.util.stream.Collectors;

public class WalletMapper {
  private WalletMapper () {
  }

  public static WalletDto toDto (Wallet wallet) {
    return WalletDto.builder()
      .id(wallet.getId())
      .balance(wallet.getBalance())
      .name(wallet.getName())
      .build();
  }

  public static WalletDto toDtoWithTransactions (Wallet wallet) {
    return WalletDto.builder()
      .id(wallet.getId())
      .balance(wallet.getBalance())
      .name(wallet.getName())
      .transactions(wallet.getTransactions().stream().map(TransactionMapper::toDto).collect(Collectors.toList()))
      .build();
  }

  public static WalletDto toDtoWithTransaction (Wallet wallet, List<Transaction> transactions) {
    return WalletDto.builder()
      .id(wallet.getId())
      .balance(wallet.getBalance())
      .name(wallet.getName())
      .transactions(transactions.stream().map(TransactionMapper::toDto).collect(Collectors.toList()))
      .build();
  }

  public static Wallet toEntity (WalletDto walletDto) {
    Wallet wallet = new Wallet();
    wallet.setName(walletDto.getName());
    wallet.setBalance(walletDto.getBalance());
    return wallet;
  }

}
