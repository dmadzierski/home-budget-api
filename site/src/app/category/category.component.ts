import {Component, OnInit} from '@angular/core';
import {Category} from '../models/category.model';
import {CategoryHttpService} from './category.http.service';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css']
})
export class CategoryComponent implements OnInit {
  categories: Array<Category>;


  constructor(private categoryHttpService: CategoryHttpService) {
    this.getCategories();
  }

  ngOnInit(): void {
  }

  private getCategories() {
    this.categoryHttpService.getCategories().subscribe(success => {
      console.log(success);
      this.categories = success;
    }, error => {
    });
  }
}
