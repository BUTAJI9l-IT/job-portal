import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {CompanyDto} from "../../../../model/companyDto";
import {FormControl} from "@angular/forms";
import {CompanyService} from "../../../../api/company.service";

@Component({
    selector: 'app-company-select',
    templateUrl: './company-select.component.html',
    styleUrls: ['./company-select.component.css']
})
export class CompanySelectComponent implements OnInit {
    companyDisplay = (value: any) => {
        return value.companyName;
    };
    companies: CompanyDto[] = []
    companyControl = new FormControl<CompanyDto | string>('');
    selectedCompanies = new FormControl<CompanyDto[]>([])
    @Input()
    multiple = false
    @Output()
    onChange = new EventEmitter<string[]>()

    constructor(
        private companyService: CompanyService
    ) {
    }

    ngOnInit(): void {
        this.companyControl.valueChanges.subscribe((value) => {
            if (typeof value === 'string') {
                this.companyService.getCompanies(value ? [value] : []).subscribe((resp) => {
                    this.companies = resp.content ? resp.content : [];
                });
            } else if (value !== null) {
                if (!this.multiple) {
                    this.selectedCompanies.setValue([])
                }
                if (!this.selectedCompanies.value?.some(c => c.id === value.id)) {
                    this.selectedCompanies.setValue(this.selectedCompanies.value?.concat(value!)!)
                }
                this.companyControl.setValue('');
            }
            this.onChange.emit(this.selectedCompanies.value?.filter(c => !!c.id).map(c => c.id!))
        })
        this.companyControl.setValue('');
    }

    remove(company: CompanyDto) {
        this.selectedCompanies.setValue(this.selectedCompanies.value?.filter(c => c.id != company.id)!)
        this.onChange.emit(this.selectedCompanies.value?.filter(c => !!c.id).map(c => c.id!))
    }
}