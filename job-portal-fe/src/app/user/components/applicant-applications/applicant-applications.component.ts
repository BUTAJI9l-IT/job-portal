import {Component} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {StorageService} from "../../../service/storage.service";
import {UserService} from "../../../../api/user.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-applicant-applications',
  templateUrl: './applicant-applications.component.html',
  styleUrls: ['./applicant-applications.component.css']
})
export class ApplicantApplicationsComponent {
  filters = new FormGroup({
    q: new FormControl<string[]>([]),
    inputQ: new FormControl<string>(''),
    status: new FormControl<string>('OPEN'),
    applicant: new FormControl<string>(''),
    dateRange: new FormGroup({
      from: new FormControl<Date | null>(null),
      to: new FormControl<Date | null>(null),
    }),
    size: new FormControl(10),
    sort: new FormControl<string[]>([])
  });
  page = new FormControl(0)
  nui = '';

  constructor(private userService: UserService, private route: ActivatedRoute) {
    this.route.params.subscribe(params => {
      let userId = params['id'];
      this.userService.getUserInfo(userId).subscribe(info => {
        this.nui = info.nui!;
        this.filters.controls.applicant.setValue(info.nui!);
      })
    });
  }
}
