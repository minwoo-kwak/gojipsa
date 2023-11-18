import { localAxios } from "../util/http-common";

const local = localAxios();

const url = "/checklist"

function writeChecklist(checklist,success,fail){
    local.post(`${url}/`,checklist).then(success).catch(fail)
}

function loadSavedChecklist(aptCode,success,fail){
    local.get(`${url}/load/${aptCode}`).then(success).catch(fail)
}

export{
    writeChecklist,
    loadSavedChecklist
}