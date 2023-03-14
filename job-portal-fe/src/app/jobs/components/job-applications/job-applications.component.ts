import {AfterViewInit, ChangeDetectorRef, Component, ViewChild} from '@angular/core';
import {MatSidenav, MatSidenavContent} from "@angular/material/sidenav";
import {ActivatedRoute} from "@angular/router";
import {StorageService} from "../../../service/storage.service";
import {FormArray, FormControl, FormGroup, Validators} from "@angular/forms";
import {JobPositionDetailDto} from "../../../../model/jobPositionDetailDto";
import {JobPositionService} from "../../../../api/jobPosition.service";
import {ApplicationService} from "../../../../api/application.service";
import {AlertService} from "../../../service/alert.service";
import {PageApplicationDto} from "../../../../model/pageApplicationDto";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-job-applications',
  templateUrl: './job-applications.component.html',
  styleUrls: ['./job-applications.component.css']
})
export class JobApplicationsComponent implements AfterViewInit {
  @ViewChild('sidenav') sidenav!: MatSidenav;
  @ViewChild('contentElement') contentElement!: MatSidenavContent;
  applications = new FormArray<FormGroup>([])
  selected = new FormControl(false);
  jobPosition = new FormGroup<any>({id: new FormControl(undefined)});
  filters = new FormGroup({
    q: new FormControl<string[]>([]),
    inputQ: new FormControl<string>(''),
    status: new FormControl<string>('OPEN'),
    dateRange: new FormGroup({
      from: new FormControl<Date | null>(null),
      to: new FormControl<Date | null>(null),
    }),
    size: new FormControl(10),
    sort: new FormControl<string[]>([])
  });
  page = new FormControl(0)
  count = new FormControl(0);
  nextPageCount = new FormControl(0);
  totalPages = new FormControl(0);


  constructor(private route: ActivatedRoute, private cdr: ChangeDetectorRef, private storage: StorageService,
              private jobService: JobPositionService, private applicationService: ApplicationService,
              private alertService: AlertService, protected translate: TranslateService) {
    this.route.params.subscribe(params => {
      let jobId = params['id'];
      this.jobService.getJobPosition(jobId).subscribe(job => {
        this.buildJobPosition(job);
        this.updateApplications(0);
      })
    });
  }

  updateApplications(page: number) {
    this.applicationService.getApplications(
      this.filters.value.q!.concat(this.filters.value.inputQ!),
      undefined,
      this.jobPosition.controls['id'].value!,
      undefined,
      this.filters.value.status!,
      this.filters.controls.dateRange.controls.from.value ? this.filters.controls.dateRange.controls.from.value : undefined,
      this.filters.controls.dateRange.controls.to.value ? this.filters.controls.dateRange.controls.to.value : undefined,
      page,
      this.filters.value.size!,
      this.filters.value.sort!
    ).subscribe({
      next: (response) => {
        this.getApplications(response);
      },
      error: err => {
        this.alertService.commonErrorHandle(err.error);
      }
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

  private getApplications(response: PageApplicationDto) {
    this.count.setValue(response.totalElements!)
    this.totalPages.setValue(response.totalPages!)
    this.nextPageCount.setValue(response.totalElements! - (response.pageable?.offset! + response.numberOfElements!))
    this.applications = new FormArray(response.content?.map(application => {
      return new FormGroup({
        id: new FormControl(application.id, Validators.required),
        date: new FormControl(new Date(application.date!)),
        status: new FormControl(application.state),
        name: new FormControl(application.applicant?.name!),
      });
    })!)
  }

  ngAfterViewInit(): void {
    if (this.route.firstChild) {
      this.route.firstChild.params.subscribe(params => {
        this.selected.setValue(!!params['applicantId']);
        this.contentElement.scrollTo({bottom: 0, behavior: 'smooth'});
      })
    }
    this.page.valueChanges.subscribe({
      next: value => {
        if (value! <= 0) {
          this.updateApplications(0);
          return;
        } else if (value! >= this.totalPages.value!) {
          this.page.setValue(this.totalPages.value! - 1)
          return;
        }
        this.updateApplications(value!);
      },
      error: err => {
        this.alertService.commonErrorHandle(err.error)
      }
    })
    this.filters.valueChanges.subscribe({
      next: () => {
        this.page.setValue(0);
      },
      error: err => {
        this.alertService.commonErrorHandle(err.error)
      }
    })
    this.sidenav.open().then(() => {
    });
    this.cdr.detectChanges();
  }

  getApplicationDate(a: FormGroup) {
    return a.controls['date'].value!.toLocaleDateString(this.translate.currentLang, {
      weekday: "long",
      year: "numeric",
      month: "short",
      day: "numeric"
    })
  }
}
