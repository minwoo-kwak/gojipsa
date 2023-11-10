import { localAxios } from "../util/http-common";

const local = localAxios();

const url = "/apartment"

function getSido(success, fail) {
    local.get(`${url}/sido`).then(success).catch(fail);
}
function getGugun(sidoName ,success, fail) {
    local.get(`${url}/gugun/${sidoName}`).then(success).catch(fail);
}

function getDongNameListFromLocalAPI(gugunName ,success, fail) {
    local.get(`${url}/dongname/${gugunName}`).then(success).catch(fail);
}

function getDongCodeFromLocalAPI(param, success, fail) {
    console.log("param in apartment.js == ", param);
    local.get(`${url}/dongcode`, { params: param }).then(success).catch(fail);
}

export {
    getSido,
    getGugun,
    getDongNameListFromLocalAPI,
    getDongCodeFromLocalAPI
}