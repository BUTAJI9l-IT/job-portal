import {SpinnerActionTypes} from "./spinner.actions";
import {Action} from "@ngrx/store";

export interface State {
  isOn: boolean;
}

export const initialState: State = {
  isOn: false
};

export function reducer(state: State = initialState, action: Action): State {
  switch (action.type) {
    case SpinnerActionTypes.StartSpinner: {
      return {
        isOn: true
      };
    }

    case SpinnerActionTypes.StopSpinner: {
      return {
        isOn: false
      };
    }

    default:
      return state;
  }
}
