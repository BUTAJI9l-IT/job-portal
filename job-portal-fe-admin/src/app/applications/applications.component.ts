import {Component} from '@angular/core';
import {FiltersFor} from "../component/pageable/filters/filters.component";
import {MatTableDataSource} from "@angular/material/table";
import {PageableEvent} from "../component/pageable/pageable.component";
import {ApplicationService} from "../../api/application.service";
import {ApplicationDto} from "../../model/applicationDto";

@Component({
    selector: 'app-applications',
    templateUrl: './applications.component.html',
    styleUrls: ['./applications.component.css']
})
export class ApplicationsComponent {
    dataSource: MatTableDataSource<ApplicationDto> = new MatTableDataSource<ApplicationDto>();
    columns = [
        {ref: "id", label: "ID", sortable: false},
        {ref: "applicant.name", label: "Applicant", sortable: true},
        {ref: "jobPosition.name", label: "Name", sortable: true},
        {ref: "date", label: "Date", sortable: true},
        {ref: "state", label: "State", sortable: true},
    ];
    sortProperties: Map<string, string> = new Map([
        ["jobPosition.name", "jobPosition"],
        ["applicant.name", "applicant"],
        ["positionName", "name"],
        ["state", "status"]
    ])

    constructor(private applicationService: ApplicationService) {
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
        this.applicationService.getApplications(!!event.filterQ ? [event.filterQ] : [],
            event.applicant, event.jobPosition, event.company, event.status, event.dateFrom, event.dateTo,
            event.paginator.pageIndex,
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
