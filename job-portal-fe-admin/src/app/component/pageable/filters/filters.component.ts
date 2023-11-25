import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {CompanyDto} from "../../../../model/companyDto";
import CompanySizeEnum = CompanyDto.CompanySizeEnum;
import {UserDto} from "../../../../model/userDto";
import ScopeEnum = UserDto.ScopeEnum;
import {FormArray, FormControl, FormGroup} from "@angular/forms";
import {JobPositionDto} from "../../../../model/jobPositionDto";
import StatusEnum = JobPositionDto.StatusEnum;
import {ApplicationDto} from "../../../../model/applicationDto";
import StateEnum = ApplicationDto.StateEnum;
export interface Filters {
  jobPosition: FormControl<undefined | string>,
  applicant: FormControl<undefined | string>,
  company: FormControl<undefined | string>,
  categories: FormControl<string[] | undefined>,
  companies: FormControl<string[] | undefined>,
  status: FormControl<undefined | StatusEnum | StateEnum>,
  dateFrom: FormControl<undefined | string> ,
  dateTo: FormControl<undefined | string> ,
  companySize: FormControl<undefined | CompanySizeEnum>,
  scope: FormControl<undefined | ScopeEnum>
}
export enum FiltersFor {
  USER, APPLICANT, COMPANY, JOB, APPLICATION, NONE,
}
@Component({
  selector: 'app-filters',
  templateUrl: './filters.component.html',
  styleUrls: ['./filters.component.css']
})
export class FiltersComponent {
  @Output()
  onChange = new EventEmitter<Filters>()
  @Input()
  filtersForm: FormGroup<Filters> = new FormGroup<Filters>({
    applicant: new FormControl(),
    categories: new FormControl(),
    companies: new FormControl(),
    company: new FormControl(),
    companySize: new FormControl(),
    dateFrom: new FormControl(),
    dateTo: new FormControl(),
    jobPosition: new FormControl(),
    scope: new FormControl(),
    status: new FormControl()
  })
  @Input()
  filtersFor: FiltersFor =  FiltersFor.NONE
  filtersEnabledByType: Map<FiltersFor, string[]> = new Map<FiltersFor, string[]>([
    [FiltersFor.USER, ["scope"]],
    [FiltersFor.JOB, ["status", "categories", "companies"]],
    [FiltersFor.COMPANY, ["companySize"]],
    [FiltersFor.APPLICATION, ["applicant", "jobPosition", "applicant", "date"]],
    [FiltersFor.APPLICANT, ["jobPosition"]],
    [FiltersFor.NONE, []]
  ])

  isEnabled(filterId: string) {
    return this.getEnabled()?.includes(filterId)
  }

  getEnabled() {
    return this.filtersEnabledByType.get(this.filtersFor)
  }

  setCompanies(ids: string[]){
    this.filtersForm.controls.companies.setValue(ids)
  }

  setCompany(ids: string[]){
    this.filtersForm.controls.company.setValue(ids[0])
  }

  setJobPosition(id: string) {
    this.filtersForm.controls.jobPosition.setValue(id)
  }

  setApplicant(id: string) {
    this.filtersForm.controls.applicant.setValue(id)
  }
}
