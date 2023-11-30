import {Component, OnInit} from '@angular/core';
import {FiltersFor} from "../component/pageable/filters/filters.component";
import {MatTableDataSource} from "@angular/material/table";
import {JobPositionCategoryService} from "../../api/jobPositionCategory.service";
import {ReferenceDto} from "../../model/referenceDto";

@Component({
    selector: 'app-categories',
    templateUrl: './categories.component.html',
    styleUrls: ['./categories.component.css']
})
export class CategoriesComponent implements OnInit {
    dataSource: MatTableDataSource<ReferenceDto> = new MatTableDataSource<ReferenceDto>();
    columns = [
        {ref: "id", label: "ID", sortable: false},
        {ref: "name", label: "Category name", sortable: false}
    ];

    constructor(private categoryService: JobPositionCategoryService) {
    }

    protected readonly FiltersFor = FiltersFor;

    ngOnInit(): void {
        this.categoryService.getCategories().subscribe(cats => {
            this.dataSource = new MatTableDataSource<ReferenceDto>(cats.jobCategories)
        })
    }
}
