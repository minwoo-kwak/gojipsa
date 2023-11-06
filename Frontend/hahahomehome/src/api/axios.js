import { ref } from "vue";
import axios from "axios"

const SERVER_URL = ref("http://localhost")
const PORT = ref(":8080")
const VERSION = ref("/api/v1")

export const instance = axios.create({
    baseURL: SERVER_URL.value + PORT.value + VERSION.value
})

instance.defaults.headers.common['Authorization']
    = sessionStorage.getItem('userStore') == null
        ? '' : JSON.parse(sessionStorage.getItem('userStore'))['Authorization']

instance.defaults.headers.post['Content-Type'] = 'application/json';

instance.interceptors.request.use((config) => {
    return config
}), (error) => {
    return Promise.reject(error)
    };

instance.interceptors.response.use((config) => {
    return config
}), (error) => {
    return Promise.reject(error)
};