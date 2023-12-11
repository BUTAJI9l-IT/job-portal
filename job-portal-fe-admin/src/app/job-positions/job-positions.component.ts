import {Component} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {PageableEvent} from "../component/pageable/pageable.component";
import {JobPositionDto} from "../../model/jobPositionDto";
import {JobPositionService} from "../../api/jobPosition.service";
import {FiltersFor} from "../component/pageable/filters/filters.component";
import {MatDialog} from "@angular/material/dialog";
import {AddJobComponent} from "../component/dialog/add-job/add-job.component";

@Component({
    selector: 'app-job-positions',
    templateUrl: './job-positions.component.html',
    styleUrls: ['./job-positions.component.css']
})
export class JobPositionsComponent {
    dataSource: MatTableDataSource<JobPositionDto> = new MatTableDataSource<JobPositionDto>();
    columns = [
        {ref: "id", label: "ID", sortable: false},
        {ref: "company.id", label: "Company ID", sortable: false},
        {ref: "company.name", label: "Company name", sortable: true},
        {ref: "positionName", label: "Name", sortable: true},
        {ref: "jobCategories", label: "Categories", sortable: true, multiple: true, attribute: "name"},
        {ref: "status", label: "Status", sortable: true},
    ];
    sortProperties: Map<string, string> = new Map([
        ["company.name", "company"],
        ["jobCategories", "category"],
        ["positionName", "name"]
    ])

    constructor(private jobService: JobPositionService, private dialog: MatDialog) {
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
        this.jobService.getJobPositions(!!event.filterQ ? [event.filterQ] : [],
            event.status, event.categories, event.companies, event.paginator.pageIndex,
            event.paginator.pageSize, sorts)
            .subscribe(
                r => {
                    this.dataSource.data = r.content!
                    event.paginator.length = r.totalElements
                }
            )
    }

    protected readonly FiltersFor = FiltersFor;

    addJob() {
        this.dialog.open(AddJobComponent).afterClosed().subscribe((res: boolean) => {
            if (res) {
                window.location.reload();
            }
        })
    }
}
