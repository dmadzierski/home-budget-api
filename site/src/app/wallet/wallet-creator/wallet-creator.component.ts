import {Component, OnInit} from '@angular/core';
import {Wallet} from '../../models/wallet.model';
import {WalletHttpService} from '../wallet.http.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-wallet-creator',
  templateUrl: './wallet-creator.component.html',
  styleUrls: ['./wallet-creator.component.css']
})
export class WalletCreatorComponent implements OnInit {

  wallet: Wallet = new Wallet();
  error: any;

  constructor(private router: Router, private walletHttpService: WalletHttpService) {
  }

  ngOnInit(): void {
  }

  addWallet() {
    this.walletHttpService.addWallet(this.wallet).subscribe(success => {
        this.router.navigateByUrl('/wallet/details?id=' + success.id);
      },
      error => {
        this.error = error.error.errors;
      }
    );
  }
}
