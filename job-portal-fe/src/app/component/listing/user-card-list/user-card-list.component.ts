import {Component, Input} from '@angular/core';
import {FormArray, FormControl, FormGroup} from "@angular/forms";
import {AlertService} from "../../../service/alert.service";
import {UserDto} from "../../../../model/userDto";
import {UserService} from "../../../../api/user.service";
import {PageUserDto} from "../../../../model/pageUserDto";
import ScopeEnum = UserDto.ScopeEnum;

@Component({
  selector: 'user-card-list',
  templateUrl: './user-card-list.component.html',
  styleUrls: ['./user-card-list.component.css']
})
export class UserCardListComponent {
  @Input()
  width: string = "100%";
  @Input()
  height: string = "100%";
  @Input()
  filters = new FormGroup({
    q: new FormControl<string[]>([]),
    inputQ: new FormControl<string>(''),
    scope: new FormControl<ScopeEnum | undefined>(undefined),
    size: new FormControl(10),
    sort: new FormControl<string[]>([])
  });
  @Input()
  page = new FormControl(0);
  count = new FormControl(0);
  nextPageCount = new FormControl(0);
  totalPages = new FormControl(0);
  users = new FormArray<FormGroup>([]);

  constructor(
    private userService: UserService,
    private alertService: AlertService
  ) {
  }

  ngOnInit(): void {
    this.updateUsers(0);
    this.page.valueChanges.subscribe({
      next: value => {
        if (value! <= 0) {
          this.updateUsers(0);
          return;
        } else if (value! >= this.totalPages.value!) {
          this.page.setValue(this.totalPages.value! - 1)
          return;
        }
        this.updateUsers(value!);
      },
      error: err => {
        this.alertService.commonErrorHandle(err.error)
      }
    })
    this.filters.valueChanges.subscribe({
      next: () => {
        this.page.setValue(0);
      },
      error: err => {
        this.alertService.commonErrorHandle(err.error)
      }
    })
  }

  updateUsers(page: number) {
    this.userService.getUsers(
      this.filters.value.q!.concat(this.filters.value.inputQ!),
      this.filters.value.scope!,
      page,
      this.filters.value.size!,
      this.filters.value.sort!
    ).subscribe({
      next: (response) => {
        this.getUsers(response);
      },
      error: err => {
        this.alertService.commonErrorHandle(err.error);
      }
    })
  }

  deleteItem() {
    this.page.setValue(((this.users.controls.length === 1 && this.page.value !== 0) ? this.page.value! - 1 : this.page.value!));
  }

  private getUsers(response: PageUserDto) {
    this.count.setValue(response.totalElements!)
    this.totalPages.setValue(response.totalPages!)
    this.nextPageCount.setValue(response.totalElements! - (response.pageable?.offset! + response.numberOfElements!))
    this.users = new FormArray(response.content?.map(u => {
      return new FormGroup({
        id: new FormControl(u.id),
        email: new FormControl(u.email),
        lastName: new FormControl(u.lastName),
        name: new FormControl(u.name),
        scope: new FormControl(u.scope)
      });
    })!)
  }
}
