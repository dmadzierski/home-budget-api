import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ApiUri} from './api.uri';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private authenticated = false;

  constructor(private http: HttpClient, private router: Router) {
  }

  public isAuthenticated(): boolean {
    return this.authenticated;
  }

  authenticate(user, callback): void {
    if (sessionStorage.getItem('email') !== undefined && sessionStorage.getItem('email') !== null
      && sessionStorage.getItem('basicauth') !== undefined && sessionStorage.getItem('basiauth') !== null
      && user === undefined) {
      this.authenticated = true;
      return;
    }
    const authString = user ? 'Basic ' + btoa(user.email + ':' + user.password) : '';
    this.http.get(ApiUri.user, {
      headers: {Authorization: authString}
    }).subscribe(response => {
      try {
        response['name'];
      } catch (e) {
        return undefined;
      }
      if (response['name'] !== null) {
        if (user !== undefined) {
          sessionStorage.setItem('email', user.email);
          sessionStorage.setItem('basicauth', authString);
        }
        this.authenticated = true;
        return callback && callback();
      }
      return undefined;
    }, error => {
      this.authenticated = false;
    });
  }

  logout(): void {
    sessionStorage.removeItem('email');
    sessionStorage.removeItem('basicauth');
    this.authenticated = false;
  }

  getUserName(): string {
    return sessionStorage.getItem('email');
  }
}
