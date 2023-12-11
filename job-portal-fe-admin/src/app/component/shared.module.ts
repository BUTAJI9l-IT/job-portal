import {NgModule} from '@angular/core';
import {NotFoundComponent} from "../home/components/not-found/not-found.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatSelectModule} from "@angular/material/select";
import {MatButtonModule} from "@angular/material/button";
import {NgSelectModule} from "@ng-select/ng-select";
import {RouterLink} from "@angular/router";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatIconModule} from "@angular/material/icon";
import {MatChipsModule} from "@angular/material/chips";
import {MatDialogModule} from "@angular/material/dialog";
import {MatInputModule} from "@angular/material/input";
import {MatTabsModule} from "@angular/material/tabs";
import {MatGridListModule} from "@angular/material/grid-list";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {MatSnackBarModule} from "@angular/material/snack-bar";
import {MatTableModule} from "@angular/material/table";
import {MatMenuModule} from "@angular/material/menu";
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatListModule} from "@angular/material/list";
import {MatNativeDateModule, MatRippleModule} from "@angular/material/core";
import {CommonModule, NgForOf} from "@angular/common";
import {MatCardModule} from "@angular/material/card";
import {FlexLayoutModule} from "@angular/flex-layout";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatButtonToggleModule} from "@angular/material/button-toggle";
import {MatBadgeModule} from "@angular/material/badge";
import {MatRadioModule} from "@angular/material/radio";
import {ForbiddenComponent} from "../home/components/forbidden/forbidden.component";
import {PageableComponent} from './pageable/pageable.component';
import {MatSortModule} from "@angular/material/sort";
import {FiltersComponent} from './pageable/filters/filters.component';
import {CompanySelectComponent} from './input/company-select/company-select.component';
import {MatAutocompleteModule} from "@angular/material/autocomplete";
import {JobSelectComponent} from './input/job-select/job-select.component';
import {ApplicantSelectComponent} from './input/applicant-select/applicant-select.component';
import {TextComponent} from "./input/text/text.component";
import {TextAreaComponent} from "./input/text-area/text-area.component";
import {EmailComponent} from "./input/email/email.component";
import {PhoneComponent} from "./input/phone/phone.component";
import {AddressInputComponent} from "./input/address-input/address-input.component";
import {ActionsComponent} from "./dialog/actions/actions.component";
import {EditorComponent} from "./input/editor/editor.component";
import {QuillEditorComponent, QuillViewComponent} from "ngx-quill";
import {ConfirmationComponent} from "./dialog/confirmation/confirmation.component";
import {AddExperienceComponent} from "./dialog/add-experience/add-experience.component";
import {DateRangeComponent} from "./input/date-range/date-range.component";
import {EditableInputComponent} from "./input/editable-input/editable-input.component";
import {ExtendableChipsComponent} from "./input/extendable-chips/extendable-chips.component";
import {AddJobComponent} from "./dialog/add-job/add-job.component";
import { AddUserComponent } from './dialog/add-user/add-user.component';
import {PasswordComponent} from "./input/password/password.component";
import {RepeatComponent} from "./input/password/repeat/repeat.component";
import { AddApplicationComponent } from './dialog/add-application/add-application.component';
import { AddCategoryComponent } from './dialog/add-category/add-category.component';

@NgModule({
    declarations: [NotFoundComponent, ForbiddenComponent, PageableComponent, FiltersComponent, CompanySelectComponent, JobSelectComponent, ApplicantSelectComponent, TextComponent, TextAreaComponent, EmailComponent, PhoneComponent, AddressInputComponent, ActionsComponent, EditorComponent, ConfirmationComponent, AddExperienceComponent, DateRangeComponent, EditableInputComponent, ExtendableChipsComponent,AddJobComponent, AddUserComponent,PasswordComponent,RepeatComponent, AddApplicationComponent, AddCategoryComponent],
    exports: [NotFoundComponent, ForbiddenComponent, PageableComponent, FiltersComponent, CompanySelectComponent, JobSelectComponent, ApplicantSelectComponent, TextComponent, TextAreaComponent, EmailComponent, PhoneComponent, AddressInputComponent, ActionsComponent, EditorComponent, ConfirmationComponent, AddExperienceComponent, DateRangeComponent, EditableInputComponent, ExtendableChipsComponent,AddJobComponent,PasswordComponent,RepeatComponent],
    imports: [
        MatNativeDateModule,
        CommonModule,
        ReactiveFormsModule,
        MatFormFieldModule,
        MatSelectModule,
        MatButtonModule,
        NgSelectModule,
        FormsModule,
        MatToolbarModule,
        MatIconModule,
        MatChipsModule,
        MatDialogModule,
        MatInputModule,
        MatTabsModule,
        MatGridListModule,
        MatCheckboxModule,
        MatSnackBarModule,
        MatTableModule,
        MatMenuModule,
        MatSidenavModule,
        MatListModule,
        MatRippleModule,
        RouterLink,
        NgForOf,
        MatCardModule,
        FlexLayoutModule,
        MatChipsModule,
        MatDatepickerModule,
        MatPaginatorModule,
        MatButtonToggleModule,
        MatBadgeModule,
        MatRadioModule,
        MatSortModule,
        MatAutocompleteModule,
        QuillViewComponent,
        QuillEditorComponent
    ],
})
export class SharedModule {
}

