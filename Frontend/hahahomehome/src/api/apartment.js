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
// dongcode를 기준으로 아파트 리스트 가져오기
function getApartListAPI(param,success,fail){
    //console.log("param : ",param)
    let dongcode=param.dongcode;
    let page=param.page;
    local.get(`${url}/${dongcode}`,{params:{page:page}}).then(success).catch(fail);
}

function getApartDetailFromLocalAPI(aptCode, success, fail) {
    local.get(`${url}/detail/${aptCode}`).then(success).catch(fail)
}
function getApartDealFromLocalAPI(aptCode, success, fail) {
    local.get(`${url}/deal/${aptCode}`).then(success).catch(fail)
}

export {
    getSido,
    getGugun,
    getDongNameListFromLocalAPI,
    getDongCodeFromLocalAPI,
    getApartListAPI,
    getApartDetailFromLocalAPI,
    getApartDealFromLocalAPI
}