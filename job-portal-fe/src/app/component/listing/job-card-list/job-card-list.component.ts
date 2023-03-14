import {AfterViewInit, Component, Input, OnInit} from '@angular/core';
import {FormArray, FormControl, FormGroup, Validators} from "@angular/forms";
import {MatDialog} from "@angular/material/dialog";
import {CompanyService} from "../../../../api/company.service";
import {JobPositionService} from "../../../../api/jobPosition.service";
import {AlertService} from "../../../service/alert.service";
import {PageJobPositionDto} from "../../../../model/pageJobPositionDto";
import {AddJobComponent} from "../../dialog/add-job/add-job.component";
import {CompanyDto} from "../../../../model/companyDto";
import {ActivatedRoute} from "@angular/router";
import {StorageService} from "../../../service/storage.service";

@Component({
  selector: 'job-card-list',
  templateUrl: './job-card-list.component.html',
  styleUrls: ['./job-card-list.component.css']
})
export class JobCardListComponent implements AfterViewInit {
  @Input()
  width: string = "100%";
  @Input()
  height: string = "100%";
  @Input()
  nui: string = ''
  @Input()
  isGlobal = true;
  @Input()
  filters = new FormGroup({
    q: new FormControl<string[]>([]),
    inputQ: new FormControl<string>(''),
    status: new FormControl<string>('ACTIVE'),
    categories: new FormControl<string[]>([]),
    companies: new FormControl<CompanyDto[]>([]),
    size: new FormControl(20),
    sort: new FormControl<string[]>([])
  });
  @Input()
  page = new FormControl(0);
  count = new FormControl(0);
  nextPageCount = new FormControl(0);
  totalPages = new FormControl(0);
  jobPositions = new FormArray<FormGroup>([]);

  constructor(public dialog: MatDialog, private companyService: CompanyService, private jobService: JobPositionService, private alertService: AlertService) {
  }

  addJob() {
    this.dialog.open(AddJobComponent, {data: {userId: this.nui}}).afterClosed().subscribe((res: boolean) => {
      if (res) {
        this.updateJobs(0);
      }
    })
  }

  ngAfterViewInit(): void {
    this.updateJobs(0);
    this.page.valueChanges.subscribe({
      next: value => {
        if (value! <= 0) {
          this.updateJobs(0);
          return;
        } else if (value! >= this.totalPages.value!) {
          this.page.setValue(this.totalPages.value! - 1)
          return;
        }
        this.updateJobs(value!);
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

  updateJobs(page: number) {
    this.jobService.getJobPositions(
      this.filters.value.q!.concat(this.filters.value.inputQ!),
      this.filters.value.status!,
      this.filters.value.categories!,
      this.filters.value.companies!.map(company => company.id!),
      page,
      this.filters.value.size!,
      this.filters.value.sort!
    ).subscribe({
      next: (response) => {
        this.getJobs(response);
      },
      error: err => {
        this.alertService.commonErrorHandle(err.error);
      }
    })
  }

  private getJobs(response: PageJobPositionDto) {
    this.count.setValue(response.totalElements!)
    this.totalPages.setValue(response.totalPages!)
    this.nextPageCount.setValue(response.totalElements! - (response.pageable?.offset! + response.numberOfElements!))
    this.jobPositions = new FormArray(response.content?.map(jp => {
      return new FormGroup({
        id: new FormControl(jp.id, Validators.required),
        company: new FormGroup({
          id: new FormControl(jp.company?.id),
          companyName: new FormControl(jp.company?.name),
          userId: new FormControl(jp.company?.userId)
        }),
        applied: new FormControl(jp.applied),
        favourite: new FormControl(jp.favourite),
        positionName: new FormControl(jp.positionName),
        status: new FormControl(jp.status),
        jobCategories: new FormArray<FormGroup>(jp.jobCategories?.map(cat => {
          return new FormGroup({
            id: new FormControl(cat.id),
            name: new FormControl(cat.name)
          });
        })!)
      });
    })!)
  }

  deleteItem() {
    this.page.setValue(((this.filters.controls.status.value !== undefined && this.jobPositions.controls.length === 1 && this.page.value !== 0) ? this.page.value! - 1 : this.page.value!));
  }
}

