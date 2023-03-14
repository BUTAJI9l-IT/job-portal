import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {FormArray, FormControl, FormGroup, Validators} from "@angular/forms";
import {AlertService} from "../../service/alert.service";
import {ActivatedRoute} from "@angular/router";
import {StorageService} from "../../service/storage.service";
import {UserService} from "../../../api/user.service";
import {DomSanitizer} from "@angular/platform-browser";
import {JobPositionService} from "../../../api/jobPosition.service";
import {JobPositionDetailDto} from "../../../model/jobPositionDetailDto";
import {EventData} from "../../../model/event.class";
import {EventBusService} from "../../service/event-bus.service";
import {Subscription} from "rxjs";
import {ConfirmationService} from "../../service/confirmation.service";
import {ApplicationService} from "../../../api/application.service";
import {AddJobComponent} from "../../component/dialog/add-job/add-job.component";
import {MatDialog} from "@angular/material/dialog";
import {JobPositionUpdateDto} from "../../../model/jobPositionUpdateDto";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-job-detail',
  templateUrl: './job-detail.component.html',
  styleUrls: ['./job-detail.component.css']
})
export class JobDetailComponent implements OnInit, OnDestroy {
  @Input()
  fromApplications = false;
  avatar: FormControl = new FormControl('');
  jobPosition: FormGroup = new FormGroup({
    id: new FormControl('', Validators.required),
    company: new FormGroup({
      id: new FormControl('', Validators.required),
      companyName: new FormControl(''),
      userId: new FormControl('')
    }),
    applied: new FormControl(false),
    created: new FormControl(),
    lastUpdated: new FormControl(),
    country: new FormControl(),
    state: new FormControl(),
    city: new FormControl(),
    contactEmail: new FormControl('', Validators.required),
    appliedCount: new FormControl(0),
    detail: new FormControl(''),
    favourite: new FormControl(false),
    positionName: new FormControl('', Validators.required),
    status: new FormControl(null, Validators.required),
    jobCategories: new FormArray<FormGroup>([])
  });
  canEdit = false;
  applicant = false;
  confirmApply = 'job-position.apply.confirm';
  confirmWithdraw = 'job-position.withdraw.confirm';
  eventBusSub?: Subscription;

  constructor(private alertService: AlertService,
              private route: ActivatedRoute,
              private storage: StorageService,
              private jobService: JobPositionService,
              private applicationService: ApplicationService,
              private userService: UserService,
              private sanitizer: DomSanitizer,
              private dialog: MatDialog,
              private translate: TranslateService,
              private eventBusService: EventBusService,
              private confirmationService: ConfirmationService,
  ) {
    this.eventBusSub = this.eventBusService.on('unsaveJobPosition', (value: { id: string, changedTo: boolean }) => {
      if (value.id! === this.jobPosition.controls['id'].value!) {
        this.jobPosition.controls['favourite'].setValue(value.changedTo!)
      }
    });
  }

  ngOnDestroy(): void {
    if (this.eventBusSub)
      this.eventBusSub.unsubscribe();
  }

  private getDate(a: Date) {
    return a.toLocaleDateString(this.translate.currentLang, {year: "numeric", month: "short", day: "numeric"})
  }

  getCreated() {
    const created = this.jobPosition.controls['created'].value;
    return this.getDate(new Date(created!));
  }

  getUpdated() {
    const created = this.jobPosition.controls['lastUpdated'].value;
    return this.getDate(new Date(created!));
  }

  private getAvatar(userId: string) {
    this.userService.getAvatar(userId).subscribe(response => {
      const blob = new Blob([response], {type: response.type});
      this.avatar = new FormControl(this.sanitizer.bypassSecurityTrustUrl(URL.createObjectURL(blob)));
    })
  }

  private buildJobPosition(job: JobPositionDetailDto): void {
    this.jobPosition = new FormGroup({
      id: new FormControl(job.id, Validators.required),
      company: new FormGroup({
        id: new FormControl(job.company?.id, Validators.required),
        companyName: new FormControl(job.company?.name),
        userId: new FormControl(job.company?.userId)
      }),
      applied: new FormControl(job.applied),
      created: new FormControl(job.created),
      lastUpdated: new FormControl(job.lastUpdated),
      country: new FormControl(job.country),
      state: new FormControl(job.state),
      city: new FormControl(job.city),
      contactEmail: new FormControl(job.contactEmail, Validators.required),
      appliedCount: new FormControl(job.appliedCount),
      detail: new FormControl(job.detail),
      favourite: new FormControl(job.favourite),
      positionName: new FormControl(job.positionName, Validators.required),
      status: new FormControl(job.status, Validators.required),
      jobCategories: new FormArray<FormGroup>(job.jobCategories?.map(cat => {
        return new FormGroup({
          id: new FormControl(cat.id),
          name: new FormControl(cat.name)
        });
      })!)
    });
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      let jobId = params['id'];
      this.jobService.getJobPosition(jobId).subscribe(job => {
        this.buildJobPosition(job);
        this.getAvatar(job.company?.userId!);
        let scope = this.storage.getTokenInfo()?.scope!;
        let nui = this.storage.getTokenInfo()?.nui!;
        this.canEdit = !this.fromApplications && ((scope === "ADMIN") || (scope === "COMPANY" && nui === this.jobPosition.get("company")?.get("id")!.value))
        this.applicant = (scope === "REGULAR_USER");
      })
    });
  }

  deleteFromFavorite() {
    this.jobService.removeFromFavorites(this.storage.getTokenInfo()?.nui!, this.jobPosition.get("id")?.value!).subscribe({
      next: () => {
        this.jobPosition.get("favourite")?.setValue(false);
        this.eventBusService.emit(new EventData("unsaveJobPosition", {
          id: this.jobPosition.get('id')?.value!,
          changedTo: false
        }))
      },
      error: err => {
        this.alertService.commonErrorHandle(err.error);
      }
    })
  }


  addToFavorite() {
    this.jobService.addToFavorites(this.storage.getTokenInfo()?.nui!, this.jobPosition.get("id")?.value!).subscribe({
      next: () => {
        this.eventBusService.emit(new EventData("unsaveJobPosition", {
          id: this.jobPosition.get('id')?.value!,
          changedTo: true
        }))
        this.jobPosition.get("favourite")?.setValue(true);
      },
      error: err => {
        this.alertService.commonErrorHandle(err.error);
      }
    })
  }

  editJob() {
    this.dialog.open(AddJobComponent, {
      data: {
        userId: this.jobPosition.get('company')?.get('id')!.value,
        job: this.jobPosition
      }
    })
  }

  deleteApplication() {
    this.confirmationService.openConfirm(this.confirmWithdraw, () => {
      this.applicationService.getApplications([], this.storage.getTokenInfo()?.nui!, this.jobPosition.controls['id'].value!).subscribe({
        next: (applications) => {
          const active = applications.content?.filter(a => a.state !== 'CLOSED');
          if (active && active[0]) {
            this.applicationService.deleteApplication(active[0].id!).subscribe({
              next: () => {
                this.alertService.showMessage('alerts.job-position.withdraw');
                this.jobPosition.controls['applied'].setValue(false);
                this.jobPosition.controls['appliedCount'].setValue(this.jobPosition.controls['appliedCount'].value - 1);
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

  apply() {
    this.confirmationService.openConfirm(this.confirmApply, () => {
      this.jobService.apply(this.storage.getTokenInfo()?.nui!, this.jobPosition.controls['id'].value!).subscribe({
        next: () => {
          this.alertService.showMessage('alerts.job-position.apply');
          this.jobPosition.controls['applied'].setValue(true);
          this.jobPosition.controls['appliedCount'].setValue(this.jobPosition.controls['appliedCount'].value + 1);
        },
        error: err => {
          this.alertService.commonErrorHandle(err.error);
        }
      })
    })
  }

  deleteJobPosition() {
    this.updateJobPositionWithStatus(JobPositionUpdateDto.StatusEnum.INACTIVE, 'dialog.confirmation.job.delete');
  }

  restoreJobPosition() {
    this.updateJobPositionWithStatus(JobPositionUpdateDto.StatusEnum.ACTIVE, 'dialog.confirmation.job.restore');
  }

  private updateJobPositionWithStatus(status: JobPositionUpdateDto.StatusEnum, confirmText: string) {
    this.confirmationService.openConfirm(confirmText, () => {
      let id = this.jobPosition.controls['id'].value;
      this.jobService.getJobPosition(id).subscribe({
        next: (response) => {
          this.jobService.updateJobPosition({
            city: response.city,
            contactEmail: response.contactEmail,
            country: response.country,
            detail: response.detail,
            jobCategories: response.jobCategories?.map(cat => cat.id!),
            positionName: response.positionName,
            state: response.state,
            status: status
          }, id).subscribe({
            next: () => {
              this.jobPosition.controls['status'].setValue(status);
            },
            error: err => {
              this.alertService.commonErrorHandle(err.error);
            }
          });
        },
        error: err => {
          this.alertService.commonErrorHandle(err.error);
        }
      })
    });
  }
}
