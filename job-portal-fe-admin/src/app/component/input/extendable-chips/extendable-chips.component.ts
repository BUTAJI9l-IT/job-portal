import {Component, Input, OnInit} from '@angular/core';
import {FormArray, FormGroup} from "@angular/forms";

@Component({
  selector: 'extendable-chips',
  templateUrl: './extendable-chips.component.html',
  styleUrls: ['./extendable-chips.component.css']
})
export class ExtendableChipsComponent implements OnInit {
  @Input()
  categories: FormArray<FormGroup> = new FormArray<FormGroup>([]);
  @Input()
  maxVisible = 2;
  showAllCategories = false
  remainingCategories = 0;

  toggleCategories() {
    this.showAllCategories = !this.showAllCategories;
    if (this.showAllCategories) {
      this.remainingCategories = 0;
    } else {
      this.remainingCategories = Math.max(this.categories.length - this.maxVisible, 0);
    }
  }

  ngOnInit(): void {
    this.remainingCategories = Math.max(this.categories.length - this.maxVisible, 0);
  }
}
