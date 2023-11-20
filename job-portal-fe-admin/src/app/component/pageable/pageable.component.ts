import {AfterViewInit, Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {MatTableDataSource} from "@angular/material/table";
import {FormControl} from "@angular/forms";

export interface Column {
  label: string;
  sortable: boolean;
  ref: string;
}

export interface PageableEvent {
  paginator: MatPaginator;
  sort: MatSort;
  filterQ: string | null
}

@Component({
  selector: 'app-pageable',
  templateUrl: './pageable.component.html',
  styleUrls: ['./pageable.component.css']
})
export class PageableComponent implements AfterViewInit {
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  @Input()
  columns: Column[] = [];
  @Input()
  addNewButton = true
  @Output()
  onChange: EventEmitter<PageableEvent> = new EventEmitter<PageableEvent>()
  @Output()
  addNew: EventEmitter<any> = new EventEmitter<any>()
  @Input()
  dataSource: MatTableDataSource<any> = new MatTableDataSource<any>([]);
  filterQ = new FormControl<string>("")

  getDisplayedColumns() {
    return this.columns.map((c: Column) => c.ref);
  }

  onChangeEmit(resetPaginator: boolean) {
    if (resetPaginator) {
      this.paginator.firstPage()
    }
    this.onChange.emit({paginator: this.paginator, sort: this.sort, filterQ: this.filterQ.value })
  }

  ngAfterViewInit(): void {
    this.onChangeEmit(true)
  }

  applyFilter() {
    this.onChangeEmit(true)
  }

  addNewEvent() {
    this.addNew.emit();
  }
}
