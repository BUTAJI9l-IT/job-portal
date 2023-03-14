import { Component } from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {UserDto} from "../../../../model/userDto";
import ScopeEnum = UserDto.ScopeEnum;

@Component({
  selector: 'app-users-list',
  templateUrl: './users-list.component.html',
  styleUrls: ['./users-list.component.css']
})
export class UsersListComponent {
  filters = new FormGroup({
    q: new FormControl<string[]>([]),
    inputQ: new FormControl<string>(''),
    scope: new FormControl<ScopeEnum | undefined>(undefined),
    size: new FormControl(10),
    sort: new FormControl<string[]>([])
  });
  page = new FormControl(0)
}
