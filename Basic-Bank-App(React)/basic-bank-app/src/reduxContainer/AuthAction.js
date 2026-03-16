import { LOGIN_SUCCESS, LOGOUT } from './AuthTypes';

export const loginAction = (userData) => {
    return {
        type: LOGIN_SUCCESS,
        payload: userData
    };
};

export const logoutAction = () => {
    return {
        type: LOGOUT
    };
};