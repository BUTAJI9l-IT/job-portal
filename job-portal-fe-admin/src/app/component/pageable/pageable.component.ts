import {AfterViewInit, Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {MatTableDataSource} from "@angular/material/table";
import {FormControl, FormGroup} from "@angular/forms";
import {Filters, FiltersFor} from "./filters/filters.component";
import {UserDto} from "../../../model/userDto";
import {CompanyDto} from "../../../model/companyDto";
import {ApplicationDto} from "../../../model/applicationDto";
import {JobPositionDto} from "../../../model/jobPositionDto";
import ScopeEnum = UserDto.ScopeEnum;
import CompanySizeEnum = CompanyDto.CompanySizeEnum;
import StateEnum = ApplicationDto.StateEnum;
import StatusEnum = JobPositionDto.StatusEnum;

export interface Column {
    attribute?: string;
    multiple?: boolean;
    label: string;
    sortable: boolean;
    ref: string;
}

export interface PageableEvent {
    applicant: undefined | string;
    categories: string[] | undefined;
    companies: string[] | undefined
    company: undefined | string,
    companySize: undefined | CompanySizeEnum,
    dateFrom: undefined | Date,
    dateTo: undefined | Date,
    filterQ: string | null,
    jobPosition: undefined | string,
    paginator: MatPaginator,
    scope: undefined | ScopeEnum,
    sort: MatSort,
    status: undefined | StatusEnum | StateEnum
}

@Component({
    selector: 'app-pageable',
    templateUrl: './pageable.component.html',
    styleUrls: ['./pageable.component.css']
})
export class PageableComponent implements AfterViewInit, OnInit {
    @ViewChild(MatPaginator) paginator!: MatPaginator;
    @ViewChild(MatSort) sort!: MatSort;
    @Input()
    columns: Column[] = [];
    @Input()
    addNewButton = true
    @Input()
    deleteButton = true
    @Output()
    onChange: EventEmitter<PageableEvent> = new EventEmitter<PageableEvent>()
    @Output()
    addNew: EventEmitter<any> = new EventEmitter<any>()
    @Output()
    onDelete: EventEmitter<string> = new EventEmitter<any>()
    @Input()
    dataSource: MatTableDataSource<any> = new MatTableDataSource<any>([]);
    filterQ = new FormControl<string>("")
    @Input()
    filterType: FiltersFor = FiltersFor.NONE;
    filters: FormGroup<Filters> = new FormGroup<Filters>({
        applicant: new FormControl(),
        categories: new FormControl(),
        companies: new FormControl(),
        company: new FormControl(),
        companySize: new FormControl(),
        dateFrom: new FormControl(),
        dateTo: new FormControl(),
        jobPosition: new FormControl(),
        scope: new FormControl(),
        status: new FormControl()
    });

    getDisplayedColumns() {
        if (this.deleteButton) {
            return this.columns.map((c: Column) => c.ref).concat("actions");
        }
        return this.columns.map((c: Column) => c.ref);
    }

    onChangeEmit(resetPaginator: boolean) {
        if (resetPaginator && !!this.paginator) {
            this.paginator.firstPage()
        }
        this.onChange.emit({
            applicant: this.filters.controls.applicant.value,
            categories: this.filters.controls.categories.value,
            companies: this.filters.controls.companies.value,
            company: this.filters.controls.company.value,
            companySize: this.filters.controls.companySize.value,
            dateFrom: this.filters.controls.dateFrom.value,
            dateTo: this.filters.controls.dateTo.value,
            jobPosition: this.filters.controls.jobPosition.value,
            scope: this.filters.controls.scope.value,
            status: this.filters.controls.status.value,
            paginator: this.paginator, sort: this.sort, filterQ: this.filterQ.value
        })
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

    deleteRow(row: any) {
        this.onDelete.emit(row)
    }

    ngOnInit(): void {
        this.filters.valueChanges.subscribe(() => {
            this.onChangeEmit(true)
        })
    }

    getMultiple(row: any, col: Column) {
        return this.getObjectProperty(row, col.ref).map((o: any) => o[col.attribute!]).join(", ")
    }

    getObjectProperty(row: any, path: string) {
        if (row == null) { // null or undefined
            return row;
        }
        const parts = path.split('.');
        for (let i = 0; i < parts.length; ++i) {
            if (row == null) { // null or undefined
                return undefined;
            }
            const key = parts[i];
            row = row[key];
        }
        if (this.isISOString(row)) {
            return new Date(row).toDateString()
        }
        return row;
    };

    isISOString(val: string) {
        const d = new Date(val);
        return !Number.isNaN(d.valueOf());
    };

    protected readonly FiltersFor = FiltersFor;
}
