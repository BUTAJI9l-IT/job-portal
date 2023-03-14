import {Component} from '@angular/core';
import {AbstractAccountComponent} from "../abstract/abstract-account/abstract-account.component";
import {FormArray, FormControl, FormGroup, Validators} from "@angular/forms";
import {ApplicantDetailDto} from "../../../../model/applicantDetailDto";
import {ExperienceDto} from "../../../../model/experienceDto";
import {ApplicantUpdateDto} from "../../../../model/applicantUpdateDto";
import {phoneValidators} from "../../../component/input/phone/phone.component";

@Component({
  selector: 'user-account-applicant',
  templateUrl: './user-account-applicant.component.html',
  styleUrls: ['./user-account-applicant.component.css', '../abstract/abstract-account/abstract-account.component.css']
})
export class UserAccountApplicantComponent extends AbstractAccountComponent {

  applicantInfo: FormGroup = new FormGroup({
    id: new FormControl(''),
    profile: new FormControl(''),
    phone: new FormControl(''),
    country: new FormControl(''),
    state: new FormControl(''),
    city: new FormControl(''),
    experiences: new FormArray<FormGroup>([]),
  });

  override update(): void {
    this.applicantService.updateApplicant(this.mapUpdate(), this.applicantInfo.value.id).subscribe({
      next: response => {
        this.mapForm(response);
        this.userInfo.controls["name"].setValue(response.name);
        this.userInfo.controls["lastName"].setValue(response.lastName);

        this.alertService.showMessage("alerts.changed.success");
      },
      error: err => {
        this.alertService.commonErrorHandle(err.error);
      }
    });
  }

  override getNonUserForm(): FormGroup {
    return this.applicantInfo;
  }

  override buildNonUserForm(): void {
    this.applicantService.getApplicant(this.nui).subscribe(applicantDetailDto => {
      this.mapForm(applicantDetailDto);
    })
  }

  private mapForm(applicantDetailDto: ApplicantDetailDto) {
    this.applicantInfo = this.fb.group({
      id: new FormControl(applicantDetailDto.id, [Validators.required]),
      profile: new FormControl(applicantDetailDto.profile),
      phone: new FormControl(applicantDetailDto.phone, phoneValidators),
      country: new FormControl(applicantDetailDto.country),
      state: new FormControl(applicantDetailDto.state),
      city: new FormControl(applicantDetailDto.city),
      experiences: this.fb.array(this.buildExperiences(applicantDetailDto)),
    });
    this.initValues();
  }

  private buildExperiences(applicantDetailDto: ApplicantDetailDto) {
    if (!applicantDetailDto.experiences) {
      return [];
    }
    return applicantDetailDto.experiences.map(exp => {
      return this.fb.group({
        id: new FormControl(exp.id, Validators.required),
        occupation: new FormControl(exp.occupation),
        company: this.fb.group({
          id: new FormControl(exp.company?.id),
          companyName: new FormControl(exp.company?.companyName)
        }),
        dateRange: this.fb.group({
          from: new FormControl(exp.dateRange?.fromDate),
          to: new FormControl(exp.dateRange?.toDate),
        }),
        jobCategories: this.fb.array(this.buildCategories(exp))
      });
    });
  }

  private buildCategories(exp: ExperienceDto): Array<FormGroup> {
    if (!exp.jobCategories) {
      return [];
    }
    return exp.jobCategories.map(cat => {
      return this.fb.group({
        id: new FormControl(cat.id),
        name: new FormControl(cat.name)
      })
    });
  }

  private mapUpdate(): ApplicantUpdateDto {
    return {
      city: this.applicantInfo.controls["city"].value,
      country: this.applicantInfo.controls["country"].value,
      phone: this.applicantInfo.controls["phone"].value,
      profile: this.applicantInfo.controls["profile"].value,
      state: this.applicantInfo.controls["state"].value,
      lastName: this.userInfo.controls["lastName"].value,
      name: this.userInfo.controls["name"].value
    };
  }

  generateCv() {
    this.applicantService.generateCV(this.applicantInfo.controls['id'].value, "body").subscribe({
      next: (response) => {
        let url = window.URL.createObjectURL(response);
        window.open(url, '_blank');
      },
      error: err => {
        this.alertService.commonErrorHandle(err.error);
      }
    });
  }
}
