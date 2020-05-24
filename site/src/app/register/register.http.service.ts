import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ApiUri} from '../api.uri';
import {Observable} from 'rxjs';
import {Router} from '@angular/router';
import {User} from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class RegisterHttpService {

  constructor(private http: HttpClient, private router: Router) {
  }

  public registerUser(user: User): Observable<any> {
    return this.http.post(ApiUri.register, user);
  }

}
