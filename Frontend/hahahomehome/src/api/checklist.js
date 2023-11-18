import { localAxios } from "../util/http-common";

const local = localAxios();

const url = "/checklist"

function writeChecklist(checklist,success,fail){
    local.post(`${url}/`,checklist).then(success).catch(fail)
}

function showAllChecklist(pageNo, success, fail) {
    local.get(`${url}/`,{params: {page: pageNo}}).then(success).catch(fail)
}


export{
    writeChecklist,
    showAllChecklist
}