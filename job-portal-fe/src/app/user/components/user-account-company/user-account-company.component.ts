import {Component} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {CompanyDetailDto} from "../../../../model/companyDetailDto";
import {AbstractAccountComponent} from "../abstract/abstract-account/abstract-account.component";
import {CompanyUpdateDto} from "../../../../model/companyUpdateDto";
import {RegistrationRequest} from "../../../../model/registrationRequest";

@Component({
  selector: 'user-account-company',
  templateUrl: './user-account-company.component.html',
  styleUrls: ['./user-account-company.component.css', '../abstract/abstract-account/abstract-account.component.css']
})
export class UserAccountCompanyComponent extends AbstractAccountComponent {
  cSizes = [
    {value: RegistrationRequest.CompanySizeEnum.MICRO},
    {value: RegistrationRequest.CompanySizeEnum.SMALL},
    {value: RegistrationRequest.CompanySizeEnum.MEDIUM},
    {value: RegistrationRequest.CompanySizeEnum.LARGE},
    {value: RegistrationRequest.CompanySizeEnum.CORPORATION}
  ];
  companyInfo = new FormGroup({
    id: new FormControl(),
    companyName: new FormControl(),
    companyLink: new FormControl(),
    companySize: new FormControl(),
    description: new FormControl()
  });

  override update(): void {
    this.companyService.updateCompany(this.mapUpdate(), this.companyInfo.value.id).subscribe({
      next: response => {
        this.mapForm(response);
        this.patchUserName()

        this.alertService.showMessage("alerts.changed.success");
      },
      error: err => {
        this.alertService.commonErrorHandle(err.error);
      }
    });
  }

  override getNonUserForm(): FormGroup {
    return this.companyInfo;
  }

  override buildNonUserForm(): void {
    this.companyService.getCompany(this.nui).subscribe(companyDetail => {
      this.mapForm(companyDetail);
    })
  }

  private mapForm(info: CompanyDetailDto) {
    this.companyInfo = this.fb.group({
        id: new FormControl(info.id, [Validators.required]),
        companyLink: new FormControl(info.companyLink),
        companyName: new FormControl(info.companyName),
        companySize: new FormControl(info.companySize),
        description: new FormControl(info.description)
      }
    );
    this.initValues();
  }

  private mapUpdate(): CompanyUpdateDto {
    return {
      companyLink: this.companyInfo.controls.companyLink.value,
      companyName: this.companyInfo.controls.companyName.value,
      companySize: this.companyInfo.controls.companySize.value,
      description: this.companyInfo.controls.description.value,
      lastName: this.userInfo.controls["lastName"].value,
      name: this.userInfo.controls["name"].value,
    };
  }
}
