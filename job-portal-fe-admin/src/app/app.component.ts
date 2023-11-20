import {Component, OnDestroy, OnInit} from '@angular/core';
import {StorageService} from "./service/storage.service";
import {EventBusService} from "./service/event-bus.service";
import {Subscription} from "rxjs";
import {Router} from "@angular/router";

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, OnDestroy {
    eventBusSub?: Subscription;

    constructor(private storage: StorageService,
                private storageService: StorageService,
                private eventBusService: EventBusService,
                private router: Router) {
    }

    ngOnInit(): void {
        console.log(this.router.url)
        if (this.router.url !== "/login" && this.router.url !== "/" && this.storage.getTokenInfo()?.scope !== "ADMIN") {
            window.location.replace("login")
        }
        this.eventBusSub = this.eventBusService.on('logout', () => {
            this.logOut();
        });
    }

    logOut(): void {
        this.storageService.clean();
        window.location.replace('login');
    }

    ngOnDestroy(): void {
        if (this.eventBusSub)
            this.eventBusSub.unsubscribe();
    }
}
