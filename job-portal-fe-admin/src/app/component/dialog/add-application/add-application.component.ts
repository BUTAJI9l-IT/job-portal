import { Component } from '@angular/core';
import {AbstractSafeClosableComponent} from "../abstract-safe-closable/abstract-safe-closable.component";
import {AlertService} from "../../../service/alert.service";
import {ConfirmationService} from "../../../service/confirmation.service";
import {MatDialogRef} from "@angular/material/dialog";
import {AuthorizationService} from "../../../../api/authorization.service";
import {ApplicationService} from "../../../../api/application.service";
import {JobPositionService} from "../../../../api/jobPosition.service";

@Component({
  selector: 'app-add-application',
  templateUrl: './add-application.component.html',
  styleUrls: ['./add-application.component.css']
})
export class AddApplicationComponent extends AbstractSafeClosableComponent<AddApplicationComponent>{
  applicant = ""
  jobPosition = ""

  constructor(
      confirmationService: ConfirmationService,
      dialogRef: MatDialogRef<AddApplicationComponent>,
      private alertService: AlertService,
      private jobPositionService: JobPositionService) {
    super(confirmationService, dialogRef);
  }

  saveApplication() {
    if (!this.applicant || !this.jobPosition) {
      return
    }
    this.jobPositionService.apply(this.applicant, this.jobPosition).subscribe({
          next: () => {
            window.location.reload()
            this.alertService.showMessage("An application has been created");
          },
          error: err => {
            this.alertService.handleErrorCode(err.error)
          }
        }
    );
  }

  setJobPosition($event: string) {
    this.jobPosition = $event
  }

  setApplicant($event: string) {
    this.applicant = $event
  }
}
