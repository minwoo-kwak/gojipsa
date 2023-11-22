import axios from "axios";

const { VITE_SERVER_URL, VITE_PORT, VITE_VERSION } = import.meta.env;

// local api axios instances
function localAxios() {
    console.log("localAxios() 함수 안의 axios 생성!!")
    const instance = axios.create({
        baseURL: VITE_SERVER_URL + VITE_PORT + VITE_VERSION,
        headers: {
            "Content-Type": "application/json;charset=utf-8",
        },
    });

    // 세션스토리지에 로그인 한 유저정보가 있으면 헤더에 설정해줍니다.
    instance.defaults.headers.common['Authorization']
    = sessionStorage.getItem('userStore') == null
        ? '' : JSON.parse(sessionStorage.getItem('userStore'))['Authorization']

    return instance;
}

export {localAxios}