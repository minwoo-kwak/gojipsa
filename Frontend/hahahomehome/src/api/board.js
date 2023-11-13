import { localAxios } from "../util/http-common";

const local = localAxios();

const url = "/board"

function listNotice(page, success, fail) {
    local.get(`${url}/list?page=${page.value}`).then(success).catch(fail);
}

function modifyNotice(article, success, fail) {
    local.put(`${url}/modify`, JSON.stringify(article)).then(success).catch(fail);
}

function deleteNotice(board_no, success, fail) {
    local.delete(`${url}/delete/${board_no}`,).then(success).catch(fail);
}

export {
    listNotice,
    modifyNotice,
    deleteNotice,
}