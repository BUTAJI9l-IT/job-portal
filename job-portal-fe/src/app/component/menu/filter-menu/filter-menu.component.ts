import {AfterViewInit, ChangeDetectorRef, Component, Input, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {MainSelectable} from "../../input/intermediate-check/intermediate-check.component";
import {JobPositionCategoryService} from "../../../../api/jobPositionCategory.service";
import {AlertService} from "../../../service/alert.service";
import {CompanyDto} from "../../../../model/companyDto";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'filter-menu',
  templateUrl: './filter-menu.component.html',
  styleUrls: ['./filter-menu.component.css']
})
export class FilterMenuComponent implements OnInit, AfterViewInit {
  @Input()
  applicants: boolean = false;
  @Input()
  jobs: boolean = false;
  @Input()
  companies: boolean = false;
  @Input()
  companiesSelected = new FormControl<CompanyDto[]>([])
  @Input()
  dates: boolean = false;
  @Input()
  dateRange = new FormGroup({
    from: new FormControl<Date | null>(null),
    to: new FormControl<Date | null>(null),
  })
  @Input()
  companySize: boolean = false;
  @Input()
  companySizeValue = new FormControl();
  @Input()
  categories: boolean = false;
  @Input()
  categoriesSelected = new FormControl<string[]>([])
  categoriesSelectedCount = new FormControl(0);
  allCategories: MainSelectable = {selected: false, sub: []}

  showTab: string = '';

  show(tab: string) {
    this.showTab = tab;
  }

  badgeCount() {
    let result = 0;
    result += (this.categories ? this.categoriesSelectedCount.value! : 0);
    result += (this.companySize && !!this.companySizeValue.value ? 1 : 0);
    result += (this.companies ? this.companiesSelected.value?.length! : 0);
    if (this.dates) {
      result += (!!this.dateRange.controls.from.value ? 1 : 0)
      result += (!!this.dateRange.controls.to.value ? 1 : 0)
    }
    return result;
  }

  constructor(private categoryService: JobPositionCategoryService,
              private cdr: ChangeDetectorRef,
              private alertService: AlertService,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.categoryService.getCategories().subscribe({
      next: (response) => {
        this.allCategories = {
          selected: false,
          sub: response.jobCategories!.map(jc => {
            return {id: jc.id!, name: jc.name!, selected: false}
          })
        };
      },
      error: err => {
        this.alertService.commonErrorHandle(err.error)
      }
    })
  }

  clear() {
    if (this.allCategories.sub == null) {
      return;
    }
    if (this.categories) {
      this.allCategories.sub.forEach(t => (t.selected = false));
      this.categoriesSelected.setValue([])
      this.categoriesSelectedCount.setValue(0);
    }
    if (this.companySize) {
      this.companySizeValue.reset();
    }
    if (this.dates) {
      this.dateRange.controls.from.reset();
      this.dateRange.controls.to.reset();
    }
    if (this.companies) {
      this.companiesSelected.setValue([]);
    }
  }

  ngAfterViewInit(): void {
    this.route.queryParams
      .subscribe(params => {
          if (this.companies && params['companyId'] && params['companyName']) {
            this.companiesSelected.setValue([{
              id: params['companyId'], companyName: params['companyName']
            }]);
          }
        }
      );
    this.cdr.detectChanges();
  }
}
