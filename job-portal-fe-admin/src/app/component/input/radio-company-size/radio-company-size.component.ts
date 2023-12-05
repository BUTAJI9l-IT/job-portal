import {Component, Input} from '@angular/core';
import {FormControl} from "@angular/forms";
import {RegistrationRequest} from "../../../../model/registrationRequest";

@Component({
  selector: 'radio-company-size',
  templateUrl: './radio-company-size.component.html',
  styleUrls: ['./radio-company-size.component.css']
})
export class RadioCompanySizeComponent {
  @Input()
  size = new FormControl();
  cSizes = [
    {value: null},
    {value: RegistrationRequest.CompanySizeEnum.MICRO},
    {value: RegistrationRequest.CompanySizeEnum.SMALL},
    {value: RegistrationRequest.CompanySizeEnum.MEDIUM},
    {value: RegistrationRequest.CompanySizeEnum.LARGE},
    {value: RegistrationRequest.CompanySizeEnum.CORPORATION}
  ];

  clear() {
    this.size.reset();
  }
}
