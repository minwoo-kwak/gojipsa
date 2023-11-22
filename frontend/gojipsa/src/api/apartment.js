import { localAxios } from "../util/http-common";

const local = localAxios();

const url = "/apartment"

/**
 * 시도리스트 받아오기
 * @param {*} success 
 * @param {*} fail 
 */
function getSido(success, fail) {
    local.get(`${url}/sido`).then(success).catch(fail);
}
/**
 * 선택된 시도에 해당하는 구군 리스트 가져오기
 * @param {string} sidoName 
 * @param {*} success 
 * @param {*} fail 
 */
function getGugun(sidoName ,success, fail) {
    local.get(`${url}/gugun/${sidoName}`).then(success).catch(fail);
}

/**
 * 선택된 시도, 구군에 해당하는 동이름 리스트 가져오기
 * @param {Object} param 
 * @param {*} success 
 * @param {*} fail 
 */
function getDongNameListFromLocalAPI(param ,success, fail) {
    local.get(`${url}/dongname`, {params:param}).then(success).catch(fail);
}

/**
 * 선택된 시도, 구군, 동이름에 해당하는 동코드 가져오기
 * @param {Object} param 
 * @param {*} success 
 * @param {*} fail 
 */
function getDongCodeFromLocalAPI(param, success, fail) {
    console.log("param in apartment.js == ", param);
    local.get(`${url}/dongcode`, { params: param }).then(success).catch(fail);
}
/**
 * dongcode를 기준으로 아파트 리스트 가져오기
 * @param {Object} param 
 * @param {*} success 
 * @param {*} fail 
 */
function getApartListAPI(param,success,fail){
    //console.log("param : ",param)
    let dongcode=param.dongcode;
    let page=param.page;
    local.get(`${url}/${dongcode}`,{params:{page:page}}).then(success).catch(fail);
}

/**
 * 아파트 코드에 해당하는 아파트 정보 가져오기
 * @param {string} aptCode 
 * @param {*} success 
 * @param {*} fail 
 */
function getApartDetailFromLocalAPI(aptCode, success, fail) {
    local.get(`${url}/detail/${aptCode}`).then(success).catch(fail)
}

/**
 * 아파트 거래정보 가져오기
 * @param {string} aptCode 
 * @param {*} success 
 * @param {*} fail 
 */
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