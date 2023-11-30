import {Component} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {PageableEvent} from "../component/pageable/pageable.component";
import {ApplicantDto} from "../../model/applicantDto";
import {ApplicantService} from "../../api/applicant.service";
import {FiltersFor} from "../component/pageable/filters/filters.component";

@Component({
    selector: 'app-applicants',
    templateUrl: './applicants.component.html',
    styleUrls: ['./applicants.component.css']
})
export class ApplicantsComponent {
    dataSource: MatTableDataSource<ApplicantDto> = new MatTableDataSource<ApplicantDto>();
    columns = [
        {ref: "id", label: "ID", sortable: false},
        {ref: "userId", label: "User ID", sortable: false},
        {ref: "name", label: "Name", sortable: true},
        {ref: "lastName", label: "Last Name", sortable: false},
        {ref: "email", label: "Email", sortable: false},
    ];
    sortProperties: Map<string, string> = new Map([])

    constructor(private applicantService: ApplicantService) {
    }

    updateDataSource(event: PageableEvent) {
        let sorts: string[] = [];
        if (!!event.sort && !!event.sort.active && !!event.sort.direction) {
            let active = this.sortProperties.get(event.sort.active)
            if (!active) {
                active = event.sort.active
            }
            sorts = [active + "," + event.sort.direction]
        }
        this.applicantService.getApplicants(!!event.filterQ ? [event.filterQ] : [], event.jobPosition, event.paginator.pageIndex,
            event.paginator.pageSize, sorts)
            .subscribe(
                r => {
                    this.dataSource.data = r.content!
                    event.paginator.length = r.totalElements
                }
            )
    }

    protected readonly FiltersFor = FiltersFor;
}
