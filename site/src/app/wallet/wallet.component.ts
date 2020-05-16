import {Component, OnInit} from '@angular/core';
import {Wallet} from '../models/wallet.model';
import {WalletHttpService} from './wallet.http.service';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-wallet-creator',
  templateUrl: './wallet.component.html',
  styleUrls: ['./wallet.component.css']
})
export class WalletComponent implements OnInit {
  wallets: Array<Wallet>;


  constructor(private http: HttpClient, private walletHttpService: WalletHttpService) {
    this.wallets = new Array<Wallet>();
    this.getWallets();
  }


  getWallets(): void {
    this.walletHttpService.getWallets().subscribe(success => {
      console.log(success);
      this.wallets = success;
    }, error => console.log(error));
    console.log(this.wallets.length);
  }

  ngOnInit(): void {
  }

}
