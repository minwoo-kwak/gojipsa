import { localAxios } from "../util/http-common";

const local = localAxios();

const url = "/checklist"

/**
 * 체크리스트 작성
 * @param {Object} checklist 
 * @param {Function} success 
 * @param {Function} fail 
 */
function writeChecklist(checklist,success,fail){
    local.post(`${url}/`,checklist).then(success).catch(fail)
}

/**
 * 체크리스트 목록 보여주기
 * @param {Function} pageNo 페이지
 * @param {Function} success 
 * @param {Function} fail 
 */
function showAllChecklist(pageNo, success, fail) {
    local.get(`${url}/`,{params: {page: pageNo}}).then(success).catch(fail)
}

/**
 * 체크리스트 상세보기
 * @param {string} chlistId 체크리스트 아이디
 * @param {Function} success 
 * @param {Function} fail 
 */
function showDetailChecklist(chlistId, success, fail) {
    local.get(`${url}/${chlistId}`).then(success).catch(fail)
} 
/**
 * 저장된 체크리스트를 불러옵니다.
 * @param {string} aptCode 
 * @param {Function} success 
 * @param {Function} fail 
 */
function loadSavedChecklist(aptCode,success,fail){
    local.get(`${url}/load/${aptCode}`).then(success).catch(fail)
}

/**
 * 체크리스트를 삭제합니다.
 * @param {string} chlistId 
 * @param {Function} success 
 * @param {Function} fail 
 */
function deleteChecklist(chlistId, success, fail) {
    local.delete(`${url}/${chlistId}`).then(success).catch(fail)
}

export{
    writeChecklist,
    showAllChecklist,
    showDetailChecklist,
    loadSavedChecklist,
    deleteChecklist
}
