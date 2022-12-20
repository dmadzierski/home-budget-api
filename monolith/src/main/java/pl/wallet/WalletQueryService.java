package pl.wallet;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WalletQueryService {

   private final WalletQueryRepository walletQueryRepository;

   public WalletDto findByIdAndUser_Email(Long walletId, String name) {
      return walletQueryRepository.findByIdAndUserEmail(walletId, name).orElseThrow(() -> new WalletException(WalletError.NOT_FOUND));
   }
}
