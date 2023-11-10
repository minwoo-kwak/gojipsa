import { localAxios } from "../util/http-common";

const local = localAxios();

const url = "/board"

function listNotice(param, success, fail) {
    console.log(local.getBaseURL);
    local.get(`${url}/list`, { params: param }).then(success).catch(fail);
}

export {
    listNotice,
}