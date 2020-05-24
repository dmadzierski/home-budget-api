import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ApiUri} from '../api.uri';

@Injectable({
  providedIn: 'root'
})
export class TransactionHttpService {

  constructor(private http: HttpClient) {
  }

  public getTypes() {
    return this.http.get(ApiUri.transactionTypes);
  }
}
