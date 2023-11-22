import { localAxios } from "../util/http-common";

const local = localAxios();

const url = "/info"

/**
 * 부동산 심리지수 리스트 가져오기
 * @param {*} success 
 * @param {*} fail 
 */
function getPsyIndexList(success, fail) {
    local.get(`${url}/list`).then(success).catch(fail);
}


export {
    getPsyIndexList
}