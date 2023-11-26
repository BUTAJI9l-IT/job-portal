import {NgModule} from '@angular/core';
import {SearchbarComponent} from "../home/components/searchbar/searchbar.component";
import {LoginComponent} from "../home/components/login/login.component";
import {PasswordComponent} from "./input/password/password.component";
import {EmailComponent} from "./input/email/email.component";
import {ActionsComponent} from "./dialog/actions/actions.component";
import {RepeatComponent} from "./input/password/repeat/repeat.component";
import {ConfirmationComponent} from "./dialog/confirmation/confirmation.component";
import {LanguageMenuComponent} from "../home/components/language-menu/language-menu.component";
import {AccountMenuComponent} from "../home/components/account-menu/account-menu.component";
import {SavedJobsMenuComponent} from "../home/components/saved-jobs-menu/saved-jobs-menu.component";
import {NotFoundComponent} from "../home/components/not-found/not-found.component";
import {TranslateModule, TranslateService} from "@ngx-translate/core";
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
import {PhoneComponent} from './input/phone/phone.component';
import {TextAreaComponent} from './input/text-area/text-area.component';
import {TextComponent} from './input/text/text.component';
import {EditableInputComponent} from './input/editable-input/editable-input.component';
import {AddressInputComponent} from './input/address-input/address-input.component';
import {MatAutocompleteModule} from "@angular/material/autocomplete";
import {EditorComponent} from './input/editor/editor.component';
import {QuillModule} from "ngx-quill";
import {ExperienceCardListComponent} from './listing/experience-card-list/experience-card-list.component';
import {MatCardModule} from "@angular/material/card";
import {ExperienceCardComponent} from './listing/experience-card-list/experience-card/experience-card.component';
import {FlexLayoutModule} from "@angular/flex-layout";
import {ExtendableChipsComponent} from './listing/extendable-chips/extendable-chips.component';
import {AddExperienceComponent} from './dialog/add-experience/add-experience.component';
import {MatDatepickerModule} from "@angular/material/datepicker";
import {DateRangeComponent} from './input/date-range/date-range.component';
import {ChangePasswordComponent} from './dialog/change-password/change-password.component';
import {AddJobComponent} from './dialog/add-job/add-job.component';
import {JobCardListComponent} from './listing/job-card-list/job-card-list.component';
import {JobCardComponent} from './listing/job-card-list/job-card/job-card.component';
import {PaginatorComponent} from './listing/paginator/paginator.component';
import {LocalizedPaginator} from "../helpers/localized.paginator";
import {MatPaginatorIntl, MatPaginatorModule} from "@angular/material/paginator";
import {MatButtonToggleModule} from "@angular/material/button-toggle";
import {MatBadgeModule} from "@angular/material/badge";
import {FilterMenuComponent} from './menu/filter-menu/filter-menu.component';
import {IntermediateCheckComponent} from './input/intermediate-check/intermediate-check.component';
import {RadioCompanySizeComponent} from './input/radio-company-size/radio-company-size.component';
import {MatRadioModule} from "@angular/material/radio";
import {CompanyMultiselectComponent} from './input/company-multiselect/company-multiselect.component';
import {SortMenuComponent} from './menu/sort-menu/sort-menu.component';
import {SortItemComponent} from './menu/sort-menu/sort-item/sort-item.component';
import {CompanyCardListComponent} from './listing/company-card-list/company-card-list.component';
import {CompanyCardComponent} from './listing/company-card-list/company-card/company-card.component';
import {ApplicationCardListComponent} from './listing/application-card-list/application-card-list.component';
import {ApplicationCardComponent} from './listing/application-card-list/application-card/application-card.component';
import {UserCardListComponent} from './listing/user-card-list/user-card-list.component';
import {UserCardComponent} from './listing/user-card-list/user-card/user-card.component';
import {MatTooltipModule} from "@angular/material/tooltip";

@NgModule({
  declarations: [SearchbarComponent, LoginComponent, PasswordComponent, EmailComponent, ActionsComponent, RepeatComponent, ConfirmationComponent, LanguageMenuComponent, AccountMenuComponent, SavedJobsMenuComponent, NotFoundComponent, PhoneComponent, TextAreaComponent, TextComponent, EditableInputComponent, AddressInputComponent, EditorComponent, ExperienceCardListComponent, ExperienceCardComponent, ExtendableChipsComponent, AddExperienceComponent, DateRangeComponent, ChangePasswordComponent, AddJobComponent, JobCardListComponent, JobCardComponent, PaginatorComponent, FilterMenuComponent, IntermediateCheckComponent, RadioCompanySizeComponent, CompanyMultiselectComponent, SortMenuComponent, SortItemComponent, CompanyCardListComponent, CompanyCardComponent, ApplicationCardListComponent, ApplicationCardComponent, UserCardListComponent, UserCardComponent],
  exports: [SearchbarComponent, LoginComponent, PasswordComponent, EmailComponent, ActionsComponent, RepeatComponent, ConfirmationComponent, LanguageMenuComponent, AccountMenuComponent, SavedJobsMenuComponent, NotFoundComponent, PhoneComponent, TextAreaComponent, TextComponent, PhoneComponent, AddressInputComponent, EditorComponent, ExperienceCardListComponent, AddExperienceComponent, JobCardListComponent, PaginatorComponent, JobCardComponent, ExtendableChipsComponent, FilterMenuComponent, SortMenuComponent, CompanyCardListComponent, ApplicationCardListComponent, UserCardListComponent],
    imports: [
        MatNativeDateModule,
        CommonModule,
        TranslateModule,
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
        MatAutocompleteModule,
        QuillModule,
        MatCardModule,
        FlexLayoutModule,
        MatChipsModule,
        MatDatepickerModule,
        MatPaginatorModule,
        MatButtonToggleModule,
        MatBadgeModule,
        MatRadioModule,
        MatTooltipModule
    ],
  providers: [
    {
      provide: MatPaginatorIntl,
      deps: [TranslateService],
      useFactory: (translateService: TranslateService) => new LocalizedPaginator(translateService).getPaginatorIntl()
    }]
})
export class SharedModule {
}

