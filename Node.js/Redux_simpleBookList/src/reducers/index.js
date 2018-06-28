import { combineReducers } from 'redux';
import BooksReducer from './reducerBooks';
import ActiveReducer from './reducerActiveBook';

const rootReducer = combineReducers({
  books: BooksReducer,
  activeBook: ActiveReducer
});

export default rootReducer;
