import {Component} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {CompanyDto} from "../../../../model/companyDto";
import {StorageService} from "../../../service/storage.service";

@Component({
  selector: 'app-jobs',
  templateUrl: './jobs.component.html',
  styleUrls: ['./jobs.component.css']
})
export class JobsComponent {
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
  isGlobal = true;

  constructor(private storage: StorageService) {
    if (this.storage.getTokenInfo()?.scope === 'ADMIN') {
      this.isGlobal = false;
    }
  }

}
