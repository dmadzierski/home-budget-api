import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ApiUri} from './api.uri';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private authenticated = false;

  constructor(private http: HttpClient) {
  }

  public isAuthenticated(): boolean {
    return this.authenticated;
  }

  authenticate(user, callback): void {
    let authString: string;
    if (sessionStorage.getItem('email') !== undefined && sessionStorage.getItem('basicauth') !== undefined && user === undefined) {
      // authString = sessionStorage.getItem('basicauth');
      this.authenticated = true;
      return;
    } else if (user !== undefined) {
      authString = 'Basic ' + btoa(user.email + ':' + user.password);
    }
    this.http.get(ApiUri.user, {
      headers: {Authorization: authString},
    }).subscribe(response => {
      if (user !== undefined) {
        sessionStorage.setItem('email', user.email);
        sessionStorage.setItem('basicauth', authString);
      }
      this.authenticated = true;
      return callback && callback();
    }, error => {
      this.authenticated = false;
    });
  }
}
