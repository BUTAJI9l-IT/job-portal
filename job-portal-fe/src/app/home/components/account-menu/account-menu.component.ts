import {Component, Input} from '@angular/core';
import {EventData} from "../../../../model/event.class";
import {EventBusService} from "../../../service/event-bus.service";

@Component({
  selector: 'account-menu',
  templateUrl: './account-menu.component.html',
  styleUrls: ['./account-menu.component.css']
})
export class AccountMenuComponent {
  @Input()
  username?: string;
  @Input()
  userId!: string;
  open: boolean = false;

  constructor(private eventBusService: EventBusService) {
  }

  logout() {
    this.eventBusService.emit(new EventData('logout', null));
  }
}
