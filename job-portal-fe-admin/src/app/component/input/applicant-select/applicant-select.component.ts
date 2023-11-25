import {Component, EventEmitter, Input, Output} from '@angular/core';
import {JobPositionDto} from "../../../../model/jobPositionDto";
import {FormControl} from "@angular/forms";
import {CompanyDto} from "../../../../model/companyDto";
import {JobPositionService} from "../../../../api/jobPosition.service";
import {ApplicantService} from "../../../../api/applicant.service";
import {ApplicantDto} from "../../../../model/applicantDto";

@Component({
  selector: 'app-applicant-select',
  templateUrl: './applicant-select.component.html',
  styleUrls: ['./applicant-select.component.css']
})
export class ApplicantSelectComponent {
  applicantDisplay = (value: any) => {
    return value.name + ' ' + value.lastName + ' (' + value.email + ')';
  };
  applicants: ApplicantDto[] = []
  applicantControl = new FormControl<ApplicantDto | string>('');
  selectedApplicant = new FormControl<ApplicantDto>({})
  @Input()
  selectedJobId = new FormControl<string | undefined>(undefined)
  @Output()
  onChange = new EventEmitter<string>()

  constructor(
      private applicantService: ApplicantService
  ) {
  }

  ngOnInit(): void {
    this.applicantControl.valueChanges.subscribe((value) => {
      if (typeof value === 'string') {
        this.applicantService.getApplicants(value ? [value] : [], !!this.selectedJobId.value ? this.selectedJobId.value : undefined).subscribe((resp) => {
          this.applicants = resp.content ? resp.content : [];
        });
      } else if (value !== null) {
        this.selectedApplicant.setValue({})
        this.selectedApplicant.setValue(value)
      }
      this.onChange.emit(this.selectedApplicant.value?.id)
    })
    this.applicantControl.setValue('');
  }

  remove() {
    this.selectedApplicant.setValue({})
  }
}
