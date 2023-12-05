import {Component, Input} from '@angular/core';
import {FiltersFor} from "../../../component/pageable/filters/filters.component";
import {MatTableDataSource} from "@angular/material/table";
import {ExperienceDto} from "../../../../model/experienceDto";
import {FormControl} from "@angular/forms";

@Component({
    selector: 'app-experiences-list',
    templateUrl: './experiences-list.component.html',
    styleUrls: ['./experiences-list.component.css']
})
export class ExperiencesListComponent {

    protected readonly FiltersFor = FiltersFor;
    @Input()
    experiences = new FormControl<ExperienceDto[]>([])
    dataSource: MatTableDataSource<ExperienceDto> = new MatTableDataSource<ExperienceDto>();
    columns = [
        {ref: "id", label: "ID", sortable: false},
        {ref: "occupation", label: "Occupation", sortable: false},
        {ref: "dateRange.fromDate", label: "From", sortable: false},
        {ref: "dateRange.toDate", label: "To", sortable: false},
        {ref: "company.name", label: "Company name", sortable: false},
        {ref: "jobCategories", label: "Categories", sortable: false, multiple: true, attribute: "name"},
    ];

    constructor() {
        this.dataSource.data = !!this.experiences.value ? this.experiences.value : []
        this.experiences.valueChanges.subscribe(() => {
            this.dataSource.data =!!this.experiences.value ? this.experiences.value : []
        })
    }

}
