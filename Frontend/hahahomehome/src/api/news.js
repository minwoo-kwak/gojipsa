import { localAxios } from "../util/http-common";

const local = localAxios();

const url = "/news"

function getNews(success, fail) {
    local.get(`${url}/`).then(success).catch(fail);
}


export {
    getNews
}