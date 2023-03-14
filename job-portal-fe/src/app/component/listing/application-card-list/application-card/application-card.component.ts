import {Component, EventEmitter, Input, Output} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {TranslateService} from "@ngx-translate/core";
import {AlertService} from "../../../../service/alert.service";
import {ConfirmationService} from "../../../../service/confirmation.service";
import {ApplicationService} from "../../../../../api/application.service";

@Component({
  selector: 'application-card',
  templateUrl: './application-card.component.html',
  styleUrls: ['./application-card.component.css']
})
export class ApplicationCardComponent {
  @Output()
  onUpdate = new EventEmitter();
  confirmWithdraw = 'job-position.withdraw.confirm';
  @Input()
  nui: string = ''
  @Input()
  application: FormGroup = new FormGroup({
    id: new FormControl('', Validators.required),
    date: new FormControl(new Date()),
    status: new FormControl('OPEN'),
    name: new FormControl(''),
    job: new FormControl(''),
  });

  constructor(private translate: TranslateService,
              private alertService: AlertService,
              private confirmationService: ConfirmationService,
              private applicationService: ApplicationService) {
  }

  getApplicationDate() {
    return this.application.controls['date'].value!.toLocaleDateString(this.translate.currentLang, {
      weekday: "long",
      year: "numeric",
      month: "short",
      day: "numeric"
    })
  }

  deleteApplication() {
    if (this.application.controls['status'].value === 'CLOSED') {
      this.applicationService.deleteApplication(this.application.controls['id'].value!).subscribe({
        next: () => {
          this.alertService.showMessage('alerts.job-position.delete-closed');
          this.onUpdate.emit();
        },
        error: err => {
          this.alertService.commonErrorHandle(err.error);
        }
      })
      return;
    }

    this.confirmationService.openConfirm(this.confirmWithdraw, () => {
      this.applicationService.getApplications([], this.nui, this.application.controls['job'].value!).subscribe({
        next: (applications) => {
          const active = applications.content?.filter(a => a.state !== 'CLOSED');
          if (active && active[0]) {
            this.applicationService.deleteApplication(active[0].id!).subscribe({
              next: () => {
                this.alertService.showMessage('alerts.job-position.withdraw');
                this.onUpdate.emit();
              },
              error: err => {
                this.alertService.commonErrorHandle(err.error);
              }
            })
          } else {
            this.alertService.showMessage('alerts.job-position.no-active-apply');
          }
        },
        error: err => {
          this.alertService.commonErrorHandle(err.error);
        }
      })
    })
  }
}
