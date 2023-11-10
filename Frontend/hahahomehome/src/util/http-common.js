import axios from "axios";

const { VITE_SERVER_URL, VITE_PORT, VITE_VERSION } = import.meta.env;

// local api axios instances
function localAxios() {
    const instance = axios.create({
        baseURL: VITE_SERVER_URL + VITE_PORT + VITE_VERSION,
        headers: {
            "Content-Type": "application/json;charset=utf-8",
        },
    });
    return instance;
}

export {localAxios}