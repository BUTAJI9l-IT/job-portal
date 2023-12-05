import {Component} from '@angular/core';
import {AbstractAccountComponent} from "../abstract-account/abstract-account.component";

@Component({
  selector: 'user-account-admin',
  templateUrl: './user-account-admin.component.html',
  styleUrls: ['./user-account-admin.component.css', '../abstract-account/abstract-account.component.css']
})
export class UserAccountAdminComponent extends AbstractAccountComponent {
  override update(): void {
    this.patchUserName()
  }
}
