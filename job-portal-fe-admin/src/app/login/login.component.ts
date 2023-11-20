import {Component, OnInit} from '@angular/core';
import {FormControl} from "@angular/forms";
import {AuthorizationService} from "../../api/authorization.service";
import {StorageService} from "../service/storage.service";
import jwt_decode from "jwt-decode";
import {JwtClaims} from "../../model/jwtClaims";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  hide: boolean = true;
  email = new FormControl()
  password = new FormControl()

  constructor(private authService: AuthorizationService, private storageService: StorageService) {
  }

  public onButtonPush() {
    this.authService.login({"email": this.email.value, "password": this.password.value}).subscribe(
        response => {
          if (jwt_decode<JwtClaims>(response.accessToken!).scope === "ADMIN") {
            this.storageService.saveTokens(response);
            window.location.replace('users');
          } else {
            window.location.reload();
          }
        }
    )
  }

    ngOnInit(): void {
        if (this.storageService.getTokenInfo()?.scope === "ADMIN") {
            window.location.replace("users")
        }
    }
}
