import {Component, Input} from '@angular/core';
import {FormControl} from "@angular/forms";

export type SortDirection = 'asc' | 'desc'

@Component({
  selector: 'sort-item',
  templateUrl: './sort-item.component.html',
  styleUrls: ['./sort-item.component.css']
})
export class SortItemComponent {
  @Input()
  enabled = false;
  @Input()
  name = '';
  @Input()
  value = '';
  @Input()
  array = new FormControl<string[]>([]);
  sorted?: SortDirection;

  changeSort() {
    if (this.sorted === 'asc') {
      this.sorted = 'desc'
    } else if (this.sorted === 'desc') {
      this.sorted = undefined
    } else {
      this.sorted = 'asc'
    }
    this.addToArray()
  }

  addToArray() {
    if (this.sorted === 'asc') {
      this.array.setValue(this.array.value!.concat(this.value + ',asc'));
    } else if (this.sorted === 'desc') {
      let index = this.array.value!.indexOf(this.value + ',asc');
      if (index !== -1) {
        this.array.value!.splice(index,1)
        this.array.setValue(this.array.value!.concat(this.value + ',desc'));
      }
    } else {
      this.array.setValue(this.array.value!.filter(prop => prop !== this.value + ',desc'))
    }
  }
}
