package pl.wallet;

class WalletMapper {

   public static WalletDto toDto(Wallet wallet) {
      return WalletDto.builder()
         .id(wallet.getId())
         .balance(wallet.getBalance())
         .name(wallet.getName())
         .build();
   }

   public static Wallet toEntity(WalletDto walletDto) {
      return Wallet.builder()
         .name(walletDto.getName())
         .balance(walletDto.getBalance())
         .build();
   }

   public static SimpleWalletQueryDto toQueryDto(Wallet wallet) {
      return SimpleWalletQueryDto
         .builder()
         .id(wallet.getId())
         .build();
   }
}
