import {Component, EventEmitter, Input, Output} from '@angular/core';
import {FormControl} from "@angular/forms";
import {JobPositionDto} from "../../../../model/jobPositionDto";
import {JobPositionService} from "../../../../api/jobPosition.service";

@Component({
    selector: 'app-job-select',
    templateUrl: './job-select.component.html',
    styleUrls: ['./job-select.component.css']
})
export class JobSelectComponent {
    jobDisplay = (value: any) => {
        return value.positionName;
    };
    jobs: JobPositionDto[] = []
    jobControl = new FormControl<JobPositionDto | string>('');
    selectedJob = new FormControl<JobPositionDto>({})
    @Input()
    selectedCompaniesId = new FormControl<string[] | undefined>([])
    @Output()
    onChange = new EventEmitter<string>()

    constructor(
        private jobService: JobPositionService
    ) {
    }

    ngOnInit(): void {
        this.selectedCompaniesId.valueChanges.subscribe((value) => {
            this.jobService.getJobPositions(!!this.jobControl.value && typeof this.jobControl.value === 'string' ? [this.jobControl.value] : [], undefined, undefined, !!value ? value : undefined).subscribe((resp) => {
                this.jobs = resp.content ? resp.content : [];
            });
            this.onChange.emit(this.selectedJob.value?.id)
        })
        this.jobControl.valueChanges.subscribe((value) => {
            if (typeof value === 'string') {
                this.jobService.getJobPositions(value ? [value] : [], undefined, undefined, !!this.selectedCompaniesId.value ? this.selectedCompaniesId.value : undefined).subscribe((resp) => {
                    this.jobs = resp.content ? resp.content : [];
                });
            } else if (value !== null) {
                this.selectedJob.setValue({})
                this.selectedJob.setValue(value)
            }
            this.onChange.emit(this.selectedJob.value?.id)
        })
        this.jobControl.setValue('');
    }

    remove() {
        this.selectedJob.setValue({})
        this.jobControl.setValue('');
        this.onChange.emit(this.selectedJob.value?.id)
    }
}
