import {Component, Input, OnInit} from '@angular/core';
import {CompanyDto} from "../../../../model/companyDto";
import {FormControl} from "@angular/forms";
import {CompanyService} from "../../../../api/company.service";

@Component({
  selector: 'company-multiselect',
  templateUrl: './company-multiselect.component.html',
  styleUrls: ['./company-multiselect.component.css']
})
export class CompanyMultiselectComponent implements OnInit {
  companyDisplay = (value: any) => {
    return value.companyName;
  };
  companies: CompanyDto[] = []
  companyControl = new FormControl<CompanyDto | string>('');
  @Input()
  selectedCompanies = new FormControl<CompanyDto[]>([])

  constructor(
    private companyService: CompanyService
  ) {
  }

  ngOnInit(): void {
    this.companyControl.valueChanges.subscribe((value) => {
      if (typeof value === 'string') {
        this.companyService.getCompanies(value ? [value] : []).subscribe((resp) => {
          this.companies = resp.content ? resp.content : [];
        });
      } else if (value !== null) {
        if (!this.selectedCompanies.value?.some(c => c.id === value.id)) {
          this.selectedCompanies.setValue(this.selectedCompanies.value?.concat(value!)!)
        }
        this.companyControl.setValue('');
      }
    })
    this.companyControl.setValue('');
  }

  remove(company: CompanyDto) {
    this.selectedCompanies.setValue(this.selectedCompanies.value?.filter(c => c.id != company.id)!)
  }
}
