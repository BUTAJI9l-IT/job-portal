import {Component, OnInit} from '@angular/core';
import {FormArray, FormControl, FormGroup, Validators} from "@angular/forms";
import {AlertService} from "../../service/alert.service";
import {ActivatedRoute} from "@angular/router";
import {JobPositionService} from "../../../api/jobPosition.service";
import {MatDialog} from "@angular/material/dialog";
import {ConfirmationService} from "../../service/confirmation.service";
import {JobPositionDetailDto} from "../../../model/jobPositionDetailDto";
import {AddJobComponent} from "../../component/dialog/add-job/add-job.component";
import {JobPositionUpdateDto} from "../../../model/jobPositionUpdateDto";

@Component({
  selector: 'app-job-detail',
  templateUrl: './job-position.component.html',
  styleUrls: ['./job-position.component.css']
})
export class JobPositionComponent implements OnInit {
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

  constructor(private alertService: AlertService,
              private route: ActivatedRoute,
              private jobService: JobPositionService,
              private dialog: MatDialog,
              private confirmationService: ConfirmationService,
  ) {
  }

  private getDate(a: Date) {
    return a.toLocaleDateString("en", {year: "numeric", month: "short", day: "numeric"})
  }

  getCreated() {
    const created = this.jobPosition.controls['created'].value;
    return this.getDate(new Date(created!));
  }

  getUpdated() {
    const created = this.jobPosition.controls['lastUpdated'].value;
    return this.getDate(new Date(created!));
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
      })
    });
  }

  editJob() {
    this.dialog.open(AddJobComponent, {
      data: {
        userId: this.jobPosition.get('company')?.get('id')!.value,
        job: this.jobPosition
      }
    })
  }

  deleteJobPosition() {
    this.updateJobPositionWithStatus(JobPositionUpdateDto.StatusEnum.INACTIVE, 'Inactivate job position?');
  }

  restoreJobPosition() {
    this.updateJobPositionWithStatus(JobPositionUpdateDto.StatusEnum.ACTIVE, 'Activate job position?');
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

