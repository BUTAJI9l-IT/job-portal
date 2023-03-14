import { Injectable } from '@angular/core';
import {Country, State, City, IState} from 'country-state-city';

@Injectable({
  providedIn: 'root'
})
export class LocationService {
  getCountries() {
    return Country.getAllCountries();
  }

  getStatesByCountry(countryCode: string) {
    return State.getStatesOfCountry(countryCode);
  }

  getStatesByCountryName(name: string) {
    const countryCode: string[] = Country.getAllCountries().filter(c => c.name === name).map(c => c.isoCode);
    return (countryCode && countryCode[0]) ? this.getStatesByCountry(countryCode[0]) : [];
  }

  getCitiesByStateName(name: string) {
    const state: IState[] = State.getAllStates().filter(s => s.name === name);
    return (state && state[0]) ? this.getCitiesByState(state[0].countryCode, state[0].isoCode) : [];
  }

  getCitiesByState(countryCode: string, stateCode: string) {
    return City.getCitiesOfState(countryCode, stateCode);
  }
}
