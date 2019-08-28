import axios from 'axios';

axios.interceptors.response.use(resp => {
    const {data} = resp
    return data;
}, error => {
    return Promise.reject(error);
});

export default {
    get(url, options) {
        return axios.get(url, options)
            .then(resp => Promise.resolve(resp.data))
            .catch(error => Promise.reject(error));
    },
    post(url, options) {
        return axios.post(url, options)
            .then(resp => Promise.resolve(resp.data))
            .catch(error => Promise.reject(error));
    }
}