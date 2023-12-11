import {Component} from '@angular/core';
import {AbstractAccountComponent} from "../abstract-account/abstract-account.component";
import {FormArray, FormControl, FormGroup, Validators} from "@angular/forms";
import {ApplicantDetailDto} from "../../../../model/applicantDetailDto";
import {ExperienceDto} from "../../../../model/experienceDto";
import {ApplicantUpdateDto} from "../../../../model/applicantUpdateDto";
import {phoneValidators} from "../../../component/input/phone/phone.component";
import {AddExperienceComponent} from "../../../component/dialog/add-experience/add-experience.component";

@Component({
  selector: 'user-account-applicant',
  templateUrl: './user-account-applicant.component.html',
  styleUrls: ['./user-account-applicant.component.css', '../abstract-account/abstract-account.component.css']
})
export class UserAccountApplicantComponent extends AbstractAccountComponent {

  applicantInfo: FormGroup = new FormGroup({
    id: new FormControl(''),
    profile: new FormControl(''),
    phone: new FormControl(''),
    country: new FormControl(''),
    state: new FormControl(''),
    city: new FormControl(''),
    experiences: new FormControl([]),
  });

  override update(): void {
    this.applicantService.updateApplicant(this.mapUpdate(), this.applicantInfo.value.id).subscribe({
      next: response => {
        this.mapForm(response);
        this.userInfo.controls["name"].setValue(response.name);
        this.userInfo.controls["lastName"].setValue(response.lastName);

        this.alertService.showMessage("Changes were saved");
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
      experiences: new FormControl(applicantDetailDto.experiences),
    });
    this.initValues();
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

  deleteExperience($event: string) {
    this.applicantService.removeExperience(this.applicantInfo.controls["id"].value, $event).subscribe(
        () => {
          this.buildNonUserForm();
        }
    )
  }

  addExperience() {
    this.dialog.open(AddExperienceComponent, {
      data: {
        userId: this.applicantInfo.controls["id"].value
      }
    }).afterClosed().subscribe((res: boolean) => {
      if (res) {
        this.buildNonUserForm();
      }
    })
  }
}
