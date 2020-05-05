package pl.wallet;

import pl.test_tool.RandomTool;

import java.security.Principal;

public class WalletTool {

  public static WalletDto randomWallet () {
    return walletBuilderWithNecessaryValue().build();
  }

  public static WalletDto.WalletDtoBuilder walletBuilderWithNecessaryValue () {
    return WalletDto.builder()
      .name(RandomTool.getRandomString())
      .balance(RandomTool.id());
  }

  @Deprecated
  public static WalletDto saveRandomWallet (Principal principal, WalletController walletController) {
    return walletController.addWallet(principal, randomWallet());
  }

  public static WalletDto saveRandomWallet (Principal principal, WalletResource walletResource) {
    return walletResource.addWallet(principal, randomWallet()).getBody();
  }
}
