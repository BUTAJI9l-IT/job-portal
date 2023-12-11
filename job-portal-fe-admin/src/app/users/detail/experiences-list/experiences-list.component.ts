import {Component, EventEmitter, Input, Output} from '@angular/core';
import {FiltersFor} from "../../../component/pageable/filters/filters.component";
import {MatTableDataSource} from "@angular/material/table";
import {ExperienceDto} from "../../../../model/experienceDto";

@Component({
    selector: 'app-experiences-list',
    templateUrl: './experiences-list.component.html',
    styleUrls: ['./experiences-list.component.css']
})
export class ExperiencesListComponent {

    protected readonly FiltersFor = FiltersFor;
    @Output()
    onDelete: EventEmitter<string> = new EventEmitter<string>()
    @Output()
    addNew: EventEmitter<void> = new EventEmitter<void>()
    @Input()
    dataSource: MatTableDataSource<ExperienceDto> = new MatTableDataSource<ExperienceDto>();
    columns = [
        {ref: "id", label: "ID", sortable: false},
        {ref: "occupation", label: "Occupation", sortable: false},
        {ref: "dateRange.fromDate", label: "From", sortable: false},
        {ref: "dateRange.toDate", label: "To", sortable: false},
        {ref: "company.companyName", label: "Company name", sortable: false},
        {ref: "jobCategories", label: "Categories", sortable: false, multiple: true, attribute: "name"},
    ];

    delete($event: ExperienceDto) {
        this.onDelete.emit($event.id)
    }

    addNewEvent() {
        this.addNew.emit()
    }
}
