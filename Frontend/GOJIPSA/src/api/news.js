import { localAxios } from "../util/http-common";

const local = localAxios();

const url = "/news"

function getNews(start, success, fail) {
    local.get(`${url}/${start}`).then(success).catch(fail);
}


export {
    getNews
}