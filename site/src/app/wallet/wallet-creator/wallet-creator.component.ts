import {Component, OnInit} from '@angular/core';
import {Wallet} from '../../models/wallet.model';
import {WalletHttpService} from '../wallet.http.service';

@Component({
  selector: 'app-wallet-creator',
  templateUrl: './wallet-creator.component.html',
  styleUrls: ['./wallet-creator.component.css']
})
export class WalletCreatorComponent implements OnInit {

  constructor(private walletHttpService: WalletHttpService) {
  }

  wallet: Wallet = new Wallet();

  ngOnInit(): void {
  }

  addWallet() {
    this.walletHttpService.addWallet(this.wallet).subscribe(success => {
        console.log(success);
      },
      error => {
        console.log(error);
      }
    );
  }
}
