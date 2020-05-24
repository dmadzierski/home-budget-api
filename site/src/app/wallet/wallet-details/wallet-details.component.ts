import {Component, OnInit} from '@angular/core';
import {WalletHttpService} from '../wallet.http.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Wallet} from '../../models/wallet.model';

@Component({
  selector: 'app-wallet-details',
  templateUrl: './wallet-details.component.html',
  styleUrls: ['./wallet-details.component.css']
})
export class WalletDetailsComponent implements OnInit {

  public wallet: Wallet;
  private id: number;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private walletHttpService: WalletHttpService) {
  }

  ngOnInit(): void {
    this.route
      .queryParams
      .subscribe(params => {
        this.id = params['id'];
      });
    this.getWallet();
  }

  addTransaction() {

  }

  private getWallet() {
    this.walletHttpService.getWalletWithDetails(this.id).subscribe(k => {
      this.wallet = k;
    });
  }
}
