import { localAxios } from "../util/http-common";

const local = localAxios();

const url = "/wordcloud"

async function getCloudData(success, fail) {
    await local.get(`${url}/words`).then(success).catch(fail);
}


export {
    getCloudData
}