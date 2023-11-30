import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {CompanyDto} from "../../../../model/companyDto";
import {UserDto} from "../../../../model/userDto";
import {FormControl, FormGroup} from "@angular/forms";
import {JobPositionDto} from "../../../../model/jobPositionDto";
import {ApplicationDto} from "../../../../model/applicationDto";
import CompanySizeEnum = CompanyDto.CompanySizeEnum;
import ScopeEnum = UserDto.ScopeEnum;
import StatusEnum = JobPositionDto.StatusEnum;
import StateEnum = ApplicationDto.StateEnum;
import {CategoryDto} from "../../../../model/categoryDto";
import {JobPositionCategoryService} from "../../../../api/jobPositionCategory.service";

export interface Filters {
    jobPosition: FormControl<undefined | string>,
    applicant: FormControl<undefined | string>,
    company: FormControl<undefined | string>,
    categories: FormControl<string[] | undefined>,
    companies: FormControl<string[] | undefined>,
    status: FormControl<undefined | StatusEnum | StateEnum>,
    dateFrom: FormControl<undefined | Date>,
    dateTo: FormControl<undefined | Date>,
    companySize: FormControl<undefined | CompanySizeEnum>,
    scope: FormControl<undefined | ScopeEnum>
}

export enum FiltersFor {
    USER, APPLICANT, COMPANY, JOB, APPLICATION, NONE,
}

@Component({
    selector: 'app-filters',
    templateUrl: './filters.component.html',
    styleUrls: ['./filters.component.css']
})
export class FiltersComponent implements OnInit{
    protected readonly FiltersFor = FiltersFor;
    categoriesList: CategoryDto[] = [];
    @Output()
    onChange = new EventEmitter<Filters>()
    @Input()
    filtersForm: FormGroup<Filters> = new FormGroup<Filters>({
        applicant: new FormControl(),
        categories: new FormControl(),
        companies: new FormControl(),
        company: new FormControl(),
        companySize: new FormControl(),
        dateFrom: new FormControl(),
        dateTo: new FormControl(),
        jobPosition: new FormControl(),
        scope: new FormControl(),
        status: new FormControl()
    })
    @Input()
    filtersFor: FiltersFor = FiltersFor.NONE
    filtersEnabledByType: Map<FiltersFor, string[]> = new Map<FiltersFor, string[]>([
        [FiltersFor.USER, ["scope"]],
        [FiltersFor.JOB, ["status", "categories", "companies"]],
        [FiltersFor.COMPANY, ["companySize"]],
        [FiltersFor.APPLICATION, ["applicant", "jobPosition", "date", "status"]],
        [FiltersFor.APPLICANT, ["jobPosition"]],
        [FiltersFor.NONE, []]
    ])

    constructor(private categoryService: JobPositionCategoryService) {
    }

    isEnabled(filterId: string) {
        return this.getEnabled()?.includes(filterId)
    }

    getEnabled() {
        return this.filtersEnabledByType.get(this.filtersFor)
    }

    setCompanies(ids: string[]) {
        this.filtersForm.controls.companies.setValue(ids)
    }

    setCompany(ids: string[]) {
        this.filtersForm.controls.company.setValue(ids[0])
    }

    setJobPosition(id: string) {
        this.filtersForm.controls.jobPosition.setValue(id)
    }

    setApplicant(id: string) {
        this.filtersForm.controls.applicant.setValue(id)
    }

    ngOnInit(): void {
        if (this.isEnabled("categories")) {
            this.categoryService.getCategories().subscribe(val => {
                this.categoriesList = !!val.jobCategories ? val.jobCategories : []
            })
        }
    }
}
