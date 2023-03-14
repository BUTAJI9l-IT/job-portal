import {Component, EventEmitter, Input, Output} from '@angular/core';
import {AbstractControl, FormArray, FormControl, FormGroup, Validators} from "@angular/forms";
import {TranslateService} from "@ngx-translate/core";
import {ApplicantService} from "../../../../../api/applicant.service";
import {AlertService} from "../../../../service/alert.service";
import {StorageService} from "../../../../service/storage.service";
import {ConfirmationService} from "../../../../service/confirmation.service";

@Component({
  selector: 'experience-card',
  templateUrl: './experience-card.component.html',
  styleUrls: ['./experience-card.component.css']
})
export class ExperienceCardComponent {
  @Output()
  onDelete = new EventEmitter();
  @Input()
  userId = ''
  @Input()
  readOnly = false;
  @Input()
  experience: FormGroup = new FormGroup({
    id: new FormControl('', Validators.required),
    occupation: new FormControl(''),
    company: new FormGroup({
      id: new FormControl(''),
      companyName: new FormControl('')
    }),
    dateRange: new FormGroup({
      from: new FormControl(''),
      to: new FormControl(''),
    }),
    jobCategories: new FormArray<FormGroup>([])
  });

  constructor(private translate: TranslateService,
              private alertService: AlertService,
              private storage: StorageService,
              private confirmationService: ConfirmationService,
              private applicantService: ApplicantService) {
  }

  getDateRange() {
    const range: AbstractControl<any> = this.experience.controls['dateRange'];
    const from = range.get('from')?.value;
    const to = range.get('to')?.value;
    const fromString = from ? this.getDate(new Date(from)) : '';
    const toString = to ? this.getDate(new Date(to)) : this.translate.instant('inputs.date-range.present');
    return fromString + ' - ' + toString;
  }

  private getDate(a: Date) {
    return a.toLocaleDateString(this.translate.currentLang, {year: "numeric", month: "short", day: "numeric"})
  }

  deleteExperience() {
    this.confirmationService.openConfirm('dialog.confirmation.experience.delete', () => {
      this.applicantService.removeExperience(this.userId, this.experience.controls['id'].value).subscribe({
        next: () => {
          this.onDelete.emit();
        },
        error: err => {
          this.alertService.commonErrorHandle(err.error);
        }
      })
    });
  }
}
