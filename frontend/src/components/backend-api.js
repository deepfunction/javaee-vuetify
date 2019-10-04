import axios from 'axios'
import i18n from '@/plugins/i18n';

const AXIOS = axios.create({
    baseURL: `http://localhost:8080/rest`,
    params: {
        locale: i18n.locale
    },
    timeout: 10000
});

export default {

    register(email, password) {
        return AXIOS.get(`/registration/` + email + '/' + password);
    },

    registrationConfirm(code) {
        return AXIOS.post(`/registrationConfirm/` + code);
    },

    login(email, password, deviceId) {
        return AXIOS.get(`/login/` + email + '/' + password + '/' + deviceId);
    },

    sendPasswordUpdateEmail(email) {
        return AXIOS.get(`/sendPasswordUpdateEmail/` + email);
    },

    updatePassword(code, password) {
        return AXIOS.put(`/updatePassword/` + code + '/' + password);
    },

    checkTokenExist(token) {
        return AXIOS.get(`/checkTokenExist/` + token);
    },

    loginWithToken(token) {
        return AXIOS.get(`/loginWithToken/ + token`);
    },

    deleteAccessTokenByDeviceId(deviceId) {
        AXIOS.post(`/deleteAccessTokenByDeviceId/` + deviceId);
    },

    getItems(token) {
        return AXIOS.get(`/getCourses`, {
            headers: {'Authorization': ' Bearer ' + token}
        })
    },

    completeCheckout(items, token) {
        return AXIOS.put('/completeCheckout/', {
            courses: items
        }, {
            headers: {'Authorization': ' Bearer ' + token}
        });
    }
}


