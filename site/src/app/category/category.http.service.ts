import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ApiUri} from '../api.uri';
import {Category} from '../models/category.model';

@Injectable({
  providedIn: 'root'
})
export class CategoryHttpService {

  constructor(private http: HttpClient) {
  }

  addCategory(category: Category): Observable<any> {
    return this.http.put(ApiUri.addCategory, category);
  }

  getCategories(): Observable<any> {
    return this.http.get(ApiUri.userCategories);
  }
}
