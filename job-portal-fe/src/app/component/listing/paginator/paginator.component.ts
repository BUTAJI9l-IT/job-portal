import {Component, Input} from '@angular/core';
import {FormControl} from "@angular/forms";
import {PageEvent} from "@angular/material/paginator";

@Component({
  selector: 'app-paginator',
  templateUrl: './paginator.component.html',
  styleUrls: ['./paginator.component.css']
})
export class PaginatorComponent {
  @Input()
  length = new FormControl(50);
  @Input()
  pageSize = new FormControl(10);
  @Input()
  pageIndex = new FormControl(0);

  handlePageEvent(e: PageEvent) {
    if (this.length.value !== e.length) {
      this.length.setValue(e.length);
    }
    if (this.pageSize.value !== e.pageSize) {
      this.pageSize.setValue(e.pageSize);
    }
    if (this.pageIndex.value !== e.pageIndex) {
      this.pageIndex.setValue(e.pageIndex);
    }
  }
}
