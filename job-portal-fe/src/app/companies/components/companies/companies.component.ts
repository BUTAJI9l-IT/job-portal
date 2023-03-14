import {Component} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {CompanyDto} from "../../../../model/companyDto";
import CompanySizeEnum = CompanyDto.CompanySizeEnum;

@Component({
  selector: 'app-companies',
  templateUrl: './companies.component.html',
  styleUrls: ['./companies.component.css']
})
export class CompaniesComponent {
  filters = new FormGroup({
    q: new FormControl<string[]>([]),
    inputQ: new FormControl<string>(''),
    companySize: new FormControl<CompanySizeEnum | undefined>(undefined),
    size: new FormControl(10),
    sort: new FormControl<string[]>([])
  });
  page = new FormControl(0)
}
