import {Component, OnInit} from '@angular/core';
import {Category} from '../models/category.model';

@Component({
  selector: 'app-transaction',
  templateUrl: './transaction.component.html',
  styleUrls: ['./transaction.component.css']
})
export class TransactionComponent implements OnInit {
  categories: Array<Category>;

  constructor() {
  }

  ngOnInit(): void {
  }

  showDetails(id: any) {
  }
}
