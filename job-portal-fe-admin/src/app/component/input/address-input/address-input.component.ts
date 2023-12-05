import {Component, Input, OnInit} from '@angular/core';
import {EditableInputComponent} from "../editable-input/editable-input.component";
import {FormControl} from "@angular/forms";
import {LocationService} from "../../../service/location.service";
import {slideInOut} from "../../../animations";

@Component({
  selector: 'address-input',
  templateUrl: './address-input.component.html',
  styleUrls: ['./address-input.component.css'],
  animations: [slideInOut]
})
export class AddressInputComponent extends EditableInputComponent implements OnInit {
  _formControlCountryIn: FormControl<string> = new FormControl();
  @Input() set formControlCountryIn(value: FormControl<string>) {
    this._formControlCountryIn = value;
    this.formControlCountry.setValue(this._formControlCountryIn.value);
  }

  _formControlStateIn: FormControl<string> = new FormControl();
  @Input() set formControlStateIn(value: FormControl<string>) {
    this._formControlStateIn = value;
    this.formControlState.setValue(this._formControlStateIn.value);
  }

  _formControlCityIn: FormControl<string> = new FormControl();
  @Input() set formControlCityIn(value: FormControl<string>) {
    this._formControlCityIn = value;
    this.formControlCity.setValue(this._formControlCityIn.value);
  }

  formControlCountry: FormControl<string> = new FormControl();
  formControlState: FormControl<string> = new FormControl();

  formControlCity: FormControl<string> = new FormControl();
  countries: string[] = [];
  filteredCountries: string[] = [];
  states: string[] = [];
  filteredStates: string[] = [];
  cities: string[] = [];
  filteredCities: string[] = [];

  enableState: boolean = false;
  enableCity: boolean = false;

  constructor(private locationService: LocationService) {
    super();
  }

  ngOnInit(): void {
    this.formControlCountry.valueChanges.subscribe((value) => {
      this.enableState = !!value;
      if (this.enableState) {
        this.formControlState.enable();
      } else {
        this.formControlState.disable();
        this.formControlState.reset();
      }
      this._formControlCountryIn.setValue(value);
      this.filteredCountries = this.filterCountries(value);
      this.states = this.locationService.getStatesByCountryName(value).map(state => state.name)
    })
    this.formControlState.valueChanges.subscribe((value) => {
      this.enableCity = !!value && !!this.formControlCountry.value;
      if (this.enableCity) {
        this.formControlCity.enable();
      } else {
        this.formControlCity.disable();
        this.formControlCity.reset();
      }
      this._formControlStateIn.setValue(value);
      this.filteredStates = this.filterStates(value);
      this.cities = this.locationService.getCitiesByStateName(value).map(city => city.name)
    })
    this.formControlCity.valueChanges.subscribe((value) => {
      this._formControlCityIn.setValue(value);
      this.filteredCities = this.filterCities(value);
    })
    this.countries = this.locationService.getCountries().map(country => country.name);
  }

  filterCountries(value: string): string[] {
    if (!value) {
      return this.countries
    }
    const filterValue = value.toLowerCase();
    return this.countries.filter(option => option.toLowerCase().includes(filterValue));
  }

  filterStates(value: string): string[] {
    if (!value) {
      return this.states
    }
    const filterValue = value.toLowerCase();
    return this.states.filter(option => option.toLowerCase().includes(filterValue));
  }

  filterCities(value: string): string[] {
    if (!value) {
      return this.cities
    }
    const filterValue = value.toLowerCase();
    return this.cities.filter(option => option.toLowerCase().includes(filterValue));
  }

}
