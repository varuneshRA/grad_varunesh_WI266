import { createStore } from 'redux';
import authReducer from './AuthReducer';


const store = createStore(authReducer);

export default store;