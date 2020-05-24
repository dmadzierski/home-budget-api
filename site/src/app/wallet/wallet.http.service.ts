import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {ApiUri} from '../api.uri';
import {Wallet} from '../models/wallet.model';
import {AuthService} from '../auth.service';

@Injectable({
  providedIn: 'root'
})
export class WalletHttpService {

  constructor(private http: HttpClient, private auth: AuthService) {
  }

  addWallet(wallet: Wallet): Observable<any> {
    return this.http.post(ApiUri.addWallet, wallet);
  }

  getWallets(): Observable<any> {
    return this.http.get(ApiUri.userWallets);
  }

  getWalletWithDetails(id: number): Observable<any> {
    return this.http.get(ApiUri.wallet + '/' + id);
  }
}
