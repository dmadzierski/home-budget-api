import {Component, Input, OnChanges, OnInit} from '@angular/core';

@Component({
  selector: 'error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.css'],
})
export class ErrorComponent implements OnInit, OnChanges {

  @Input() errorsList: any;

  constructor() {

  }

  ngOnInit(): void {
  }

  ngOnChanges(): void {

  }

}
