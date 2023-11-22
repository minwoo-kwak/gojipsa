import { localAxios } from "../util/http-common";

const local = localAxios();

const url = "/info"

function getPsyIndexList(success, fail) {
    local.get(`${url}/list`).then(success).catch(fail);
}


export {
    getPsyIndexList
}