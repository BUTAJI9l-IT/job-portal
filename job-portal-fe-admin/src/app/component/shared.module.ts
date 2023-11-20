import {NgModule} from '@angular/core';
import {NotFoundComponent} from "../home/components/not-found/not-found.component";
import {TranslateModule} from "@ngx-translate/core";
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
import { PageableComponent } from './pageable/pageable.component';
import {MatSortModule} from "@angular/material/sort";

@NgModule({
  declarations: [NotFoundComponent, ForbiddenComponent, PageableComponent],
  exports: [NotFoundComponent, ForbiddenComponent, PageableComponent],
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
        MatCardModule,
        FlexLayoutModule,
        MatChipsModule,
        MatDatepickerModule,
        MatPaginatorModule,
        MatButtonToggleModule,
        MatBadgeModule,
        MatRadioModule,
        MatSortModule
    ],
})
export class SharedModule {
}

