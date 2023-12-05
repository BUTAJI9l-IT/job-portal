import {Component, Input, OnInit} from '@angular/core';
import {FormControl} from "@angular/forms";

export type Selectable = {
  id: string,
  name: string,
  selected: boolean
}

export type MainSelectable = {
  selected: boolean
  sub: Array<Selectable>
}

@Component({
  selector: 'intermediate-checkbox',
  templateUrl: './intermediate-check.component.html',
  styleUrls: ['./intermediate-check.component.css']
})
export class IntermediateCheckComponent {
  @Input()
  categories: MainSelectable = {
    selected: false,
    sub: [],
  };

  allComplete: boolean = false;
  @Input()
  categoriesSelected = new FormControl<string[]>([])
  @Input()
  categoriesSelectedCount = new FormControl(0);

  updateAllComplete() {
    this.allComplete = this.categories.sub != null && this.categories.sub.every(t => t.selected);
    this.setForm();
  }

  someComplete(): boolean {
    if (this.categories.sub == null) {
      return false;
    }
    return this.categories.sub.filter(t => t.selected).length > 0 && !this.allComplete;
  }

  setAll(completed: boolean) {
    this.allComplete = completed;
    if (this.categories.sub == null) {
      return;
    }
    this.categories.sub.forEach(t => (t.selected = completed));
    this.setForm();
  }

  setForm() {
    let cats = this.categories.sub.filter(cat => cat.selected);
    if (cats.length === this.categories.sub.length) {
      this.categoriesSelected.setValue([]);
    } else {
      this.categoriesSelected.setValue(cats.map(cat => cat.id))
    }
    this.categoriesSelectedCount.setValue(cats.length);
  }
}
