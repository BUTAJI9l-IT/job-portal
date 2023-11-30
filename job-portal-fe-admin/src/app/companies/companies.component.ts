import {Component} from '@angular/core';
import {FiltersFor} from "../component/pageable/filters/filters.component";
import {MatTableDataSource} from "@angular/material/table";
import {PageableEvent} from "../component/pageable/pageable.component";
import {CompanyDto} from "../../model/companyDto";
import {CompanyService} from "../../api/company.service";
import {UserService} from "../../api/user.service";

@Component({
    selector: 'app-companies',
    templateUrl: './companies.component.html',
    styleUrls: ['./companies.component.css']
})
export class CompaniesComponent {
    dataSource: MatTableDataSource<CompanyDto> = new MatTableDataSource<CompanyDto>();
    columns = [
        {ref: "id", label: "ID", sortable: false},
        {ref: "userId", label: "User ID", sortable: false},
        {ref: "companyName", label: "Company name", sortable: true},
        {ref: "companySize", label: "Size", sortable: true},
    ];
    sortProperties: Map<string, string> = new Map([
        ["companyName", "name"],
        ["companySize", "companySize"],
    ])
    lastEvent?: PageableEvent;

    constructor(private companyService: CompanyService, private userService: UserService) {
    }

    updateDataSource(event: PageableEvent) {
        this.lastEvent = event
        let sorts: string[] = [];
        if (!!event.sort && !!event.sort.active && !!event.sort.direction) {
            let active = this.sortProperties.get(event.sort.active)
            if (!active) {
                active = event.sort.active
            }
            sorts = [active + "," + event.sort.direction]
        }
        this.companyService.getCompanies(!!event.filterQ ? [event.filterQ] : [],
            event.companySize, event.paginator.pageIndex,
            event.paginator.pageSize, sorts)
            .subscribe(
                r => {
                    this.dataSource.data = r.content!
                    event.paginator.length = r.totalElements
                }
            )
    }


    delete($event: any) {
        this.userService.deleteUser($event.userId).subscribe(() => {
            if (this.lastEvent) {
                this.updateDataSource(this.lastEvent)
            }
        })
    }

    protected readonly FiltersFor = FiltersFor;
}
