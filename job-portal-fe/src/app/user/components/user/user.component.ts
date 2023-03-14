import {AfterViewInit, ChangeDetectorRef, Component, OnDestroy, ViewChild} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {MatSidenav} from "@angular/material/sidenav";
import {StorageService} from "../../../service/storage.service";
import {UserService} from "../../../../api/user.service";

@Component({
  selector: 'user-account',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements AfterViewInit {
  @ViewChild('sidenav') sidenav!: MatSidenav;
  userId!: string;
  scope = '';

  constructor(private route: ActivatedRoute, private cdr: ChangeDetectorRef, private storage: StorageService, private userService: UserService) {
    route.firstChild?.params.subscribe(params => {
      this.userId = params['id'];
      userService.getUserInfo(this.userId).subscribe(response => {
        this.scope = response.scope!;
      })
    })
  }

  ngAfterViewInit(): void {
    this.sidenav.open().then(() => {
    });
    this.cdr.detectChanges();
  }

}
