import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ApiUri} from './api.uri';
import {User} from './models/user.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  authenticated = false;
  private user: User;

  constructor(private http: HttpClient) {
  }

  public header(): any {
    return this.user ? {
      authorization: 'Basic ' + btoa(this.user.email + ':' + this.user.password)
    } : {};
  }

  authenticate(user, callback): void {
    this.user = user;
    this.http.get(ApiUri.user, {
      headers: this.header()
    }).subscribe(response => {
      console.log(response);
      if (response['name']) {
        this.authenticated = true;
      } else {
        this.authenticated = false;
      }
      return callback && callback();
    });

  }
}
