import {AfterViewInit, Component, Input} from '@angular/core';
import {FormArray, FormControl, FormGroup, Validators} from "@angular/forms";
import {CompanyService} from "../../../../api/company.service";
import {AlertService} from "../../../service/alert.service";
import {PageApplicationDto} from "../../../../model/pageApplicationDto";
import {ApplicationService} from "../../../../api/application.service";

@Component({
  selector: 'application-card-list',
  templateUrl: './application-card-list.component.html',
  styleUrls: ['./application-card-list.component.css']
})
export class ApplicationCardListComponent implements AfterViewInit {
  @Input()
  width: string = "100%";
  @Input()
  height: string = "100%";
  @Input()
  nui: string = '';
  @Input()
  isGlobal = true;
  @Input()
  filters = new FormGroup({
    q: new FormControl<string[]>([]),
    inputQ: new FormControl<string>(''),
    status: new FormControl<string>('OPEN'),
    applicant: new FormControl<string>(''),
    dateRange: new FormGroup({
      from: new FormControl<Date | null>(null),
      to: new FormControl<Date | null>(null),
    }),
    size: new FormControl(10),
    sort: new FormControl<string[]>([])
  });
  @Input()
  page = new FormControl(0);
  count = new FormControl(0);
  nextPageCount = new FormControl(0);
  totalPages = new FormControl(0);
  applications = new FormArray<FormGroup>([]);

  constructor(
    private companyService: CompanyService,
    private applicationService: ApplicationService,
    private alertService: AlertService) {
  }

  ngAfterViewInit(): void {
    this.updateApplications(0);
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
  }

  updateApplications(page: number) {
    this.applicationService.getApplications(
      this.filters.value.q!.concat(this.filters.value.inputQ!),
      this.filters.value.applicant!,
      undefined,
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

  private getApplications(response: PageApplicationDto) {
    this.count.setValue(response.totalElements!)
    this.totalPages.setValue(response.totalPages!)
    this.nextPageCount.setValue(response.totalElements! - (response.pageable?.offset! + response.numberOfElements!))
    this.applications = new FormArray(response.content?.map(application => {
      return new FormGroup({
        id: new FormControl(application.id, Validators.required),
        date: new FormControl(new Date(application.date!)),
        status: new FormControl(application.state),
        name: new FormControl(application.jobPosition?.name!),
        job: new FormControl(application.jobPosition?.id!),
      });
    })!)
  }

  deleteItem() {
    this.page.setValue(((this.filters.controls.status.value !== undefined && this.applications.controls.length === 1 && this.page.value !== 0) ? this.page.value! - 1 : this.page.value!));
  }
}
