import { localAxios } from "../util/http-common";

const local = localAxios();

const url = "/board"

function listNotice(page, success, fail) {
    local.get(`${url}/list?page=${page.value}`).then(success).catch(fail);
}

export {
    listNotice,
}