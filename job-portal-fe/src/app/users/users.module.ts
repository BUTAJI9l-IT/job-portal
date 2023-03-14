import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {UsersListComponent} from './components/users-list/users-list.component';
import {SharedModule} from "../component/shared.module";
import {RouterModule, Routes} from "@angular/router";


const routes: Routes = [{path: '', component: UsersListComponent}]

@NgModule({
  declarations: [
    UsersListComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    SharedModule
  ]
})

export class UsersModule {
}
