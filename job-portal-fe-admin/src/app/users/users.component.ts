import { Component } from '@angular/core';
import {UserService} from "../../api/user.service";
import {MatTableDataSource} from "@angular/material/table";
import {UserDto} from "../../model/userDto";
import {MatPaginator} from "@angular/material/paginator";
import {PageableEvent} from "../component/pageable/pageable.component";
import {FiltersFor} from "../component/pageable/filters/filters.component";

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent {
  dataSource: MatTableDataSource<UserDto> = new MatTableDataSource<UserDto>();
  columns = [
    {ref: "id", label: "ID", sortable: false},
    {ref: "email", label: "Email", sortable: true},
    {ref: "name", label: "Name", sortable: true},
    {ref: "lastName", label: "Last Name", sortable: false},
    {ref: "scope", label: "Scope", sortable: true},
  ];

  constructor(private userService: UserService) {
  }

  updateDataSource(event: PageableEvent) {
    let sorts: string[] = [];
    if (!!event.sort.active && !!event.sort.direction) {
      sorts = [event.sort.active + "," + event.sort.direction]
    }
    this.userService.getUsers(!!event.filterQ ? [event.filterQ] : [], event.scope, event.paginator.pageIndex, event.paginator.pageSize, sorts).subscribe(
        r => {
          this.dataSource.data = r.content!
          event.paginator.length = r.totalElements
        }
    )
  }

  protected readonly FiltersFor = FiltersFor;
}
