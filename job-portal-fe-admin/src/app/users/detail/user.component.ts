import { Component } from '@angular/core';
import {JwtClaims} from "../../../model/jwtClaims";
import {ActivatedRoute} from "@angular/router";
import {UserService} from "../../../api/user.service";

@Component({
  selector: 'app-user-detail',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent {
  scope: JwtClaims.ScopeEnum | undefined;

  constructor(private route: ActivatedRoute, private userService: UserService) {
    route.params.subscribe(params => {
      userService.getUserInfo(params['id']).subscribe(response => {
        this.scope = response.scope!;
      })
    })
  }
}
