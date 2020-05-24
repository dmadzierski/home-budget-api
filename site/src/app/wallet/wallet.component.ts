import {Component, OnInit} from '@angular/core';
import {Wallet} from '../models/wallet.model';
import {WalletHttpService} from './wallet.http.service';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';

@Component({
  selector: 'app-wallet-creator',
  templateUrl: './wallet.component.html',
  styleUrls: ['./wallet.component.css']
})
export class WalletComponent implements OnInit {
  wallets: Array<Wallet>;

  constructor(private http: HttpClient, private walletHttpService: WalletHttpService, private router: Router) {
    this.getWallets();
  }


  getWallets(): void {
    this.walletHttpService.getWallets().subscribe(success => {
      this.wallets = success;
    }, error => {
    });
  }

  ngOnInit(): void {
  }

  showWalletsDetails(id: number) {
    this.router.navigateByUrl('/wallet/details?id=' + id);
  }
}
