import { combineReducers } from 'redux';
import WeatherReducer from './reducerWeather';

const rootReducer = combineReducers({
  weather: WeatherReducer
});

export default rootReducer;
