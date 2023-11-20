import {Injectable} from '@angular/core';
import {BehaviorSubject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class LoadingService {
  private isLoading$$ = new BehaviorSubject<boolean>(false);

  setLoading(isLoading: boolean) {
    this.isLoading$$.next(isLoading);
  }

  isLoading() {
    return this.isLoading$$.asObservable();
  }

  loadingOn() {
    this.setLoading(true);
  }

  loadingOff() {
    setTimeout(() => {
      this.setLoading(false);
    }, 2000);
  }
}
