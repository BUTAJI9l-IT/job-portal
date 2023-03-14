import {Component, Input} from '@angular/core';
import {FormArray, FormControl, FormGroup, Validators} from "@angular/forms";
import {CompanyDto} from "../../../../model/companyDto";
import {CompanyService} from "../../../../api/company.service";
import {AlertService} from "../../../service/alert.service";
import {PageCompanyDto} from "../../../../model/pageCompanyDto";
import CompanySizeEnum = CompanyDto.CompanySizeEnum;

@Component({
  selector: 'app-company-card-list',
  templateUrl: './company-card-list.component.html',
  styleUrls: ['./company-card-list.component.css']
})
export class CompanyCardListComponent {
  @Input()
  width: string = "100%";
  @Input()
  height: string = "100%";
  @Input()
  filters = new FormGroup({
    q: new FormControl<string[]>([]),
    inputQ: new FormControl<string>(''),
    companySize: new FormControl<CompanySizeEnum | undefined>(undefined),
    size: new FormControl(10),
    sort: new FormControl<string[]>([])
  });
  @Input()
  page = new FormControl(0);
  count = new FormControl(0);
  nextPageCount = new FormControl(0);
  totalPages = new FormControl(0);
  companies = new FormArray<FormGroup>([]);

  constructor(
    private companyService: CompanyService,
    private alertService: AlertService
  ) {
  }

  ngOnInit(): void {
    this.updateCompanies(0);
    this.page.valueChanges.subscribe({
      next: value => {
        if (value! <= 0) {
          this.updateCompanies(0);
          return;
        } else if (value! >= this.totalPages.value!) {
          this.page.setValue(this.totalPages.value! - 1)
          return;
        }
        this.updateCompanies(value!);
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

  updateCompanies(page: number) {
    this.companyService.getCompanies(
      this.filters.value.q!.concat(this.filters.value.inputQ!),
      this.filters.value.companySize!,
      page,
      this.filters.value.size!,
      this.filters.value.sort!
    ).subscribe({
      next: (response) => {
        this.getCompanies(response);
      },
      error: err => {
        this.alertService.commonErrorHandle(err.error);
      }
    })
  }

  private getCompanies(response: PageCompanyDto) {
    this.count.setValue(response.totalElements!)
    this.totalPages.setValue(response.totalPages!)
    this.nextPageCount.setValue(response.totalElements! - (response.pageable?.offset! + response.numberOfElements!))
    this.companies = new FormArray(response.content?.map(comp => {
      return new FormGroup({
        id: new FormControl(comp.id, Validators.required),
        companyName: new FormControl(comp.companyName, Validators.required),
        userId: new FormControl(comp.userId, Validators.required)
      });
    })!)
  }
}
