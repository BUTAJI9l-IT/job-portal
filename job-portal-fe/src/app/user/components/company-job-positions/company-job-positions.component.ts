import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {CompanyDto} from "../../../../model/companyDto";
import {ActivatedRoute} from "@angular/router";
import {UserService} from "../../../../api/user.service";

@Component({
  selector: 'app-company-job-positions',
  templateUrl: './company-job-positions.component.html',
  styleUrls: ['./company-job-positions.component.css']
})
export class CompanyJobPositionsComponent {
  filters = new FormGroup({
    q: new FormControl<string[]>([]),
    inputQ: new FormControl<string>(''),
    status: new FormControl<string>('ACTIVE'),
    categories: new FormControl<string[]>([]),
    companies: new FormControl<CompanyDto[]>([]),
    size: new FormControl(10),
    sort: new FormControl<string[]>([])
  });
  page = new FormControl(0)
  companyId: string = ''

  constructor(private userService: UserService, private route: ActivatedRoute) {
    this.route.params.subscribe(params => {
      let userId = params['id'];
      this.userService.getUserInfo(userId).subscribe(info => {
        this.companyId = info.nui!;
        this.filters.controls.companies.setValue([{id: this.companyId}]);
      })
    });
  }
}
