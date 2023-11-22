import { localAxios } from "../util/http-common";

// 로컬 Axios 인스턴스 생성
const local = localAxios();

// 게시판 API 엔드포인트 URL
const url = "/board";

/**
 * 공지사항 목록 조회
 * @param {Object} page - 페이지 정보 객체
 * @param {Function} success - 성공 시 호출되는 콜백 함수
 * @param {Function} fail - 실패 시 호출되는 콜백 함수
 */
function listNotice(page, success, fail) {
    local.get(`${url}/list?page=${page.value}`).then(success).catch(fail);
}

/**
 * 조회수 증가 처리
 * @param {number} board_id - 게시글 ID
 */
function updateHit(board_id) {
    local.put(`${url}/hit/${board_id}`);
}

/**
 * 공지사항 작성
 * @param {Object} article - 작성할 게시글 정보 객체
 * @param {Function} success - 성공 시 호출되는 콜백 함수
 * @param {Function} fail - 실패 시 호출되는 콜백 함수
 */
function writeNotice(article, success, fail) {
    local.post(`${url}/write`, article).then(success).catch(fail);
}

/**
 * 공지사항 수정
 * @param {Object} article - 수정할 게시글 정보 객체
 * @param {Function} success - 성공 시 호출되는 콜백 함수
 * @param {Function} fail - 실패 시 호출되는 콜백 함수
 */
function modifyNotice(article, success, fail) {
    local.put(`${url}/modify`, JSON.stringify(article)).then(success).catch(fail);
}

/**
 * 공지사항 삭제
 * @param {number} board_no - 삭제할 게시글의 번호
 * @param {Function} success - 성공 시 호출되는 콜백 함수
 * @param {Function} fail - 실패 시 호출되는 콜백 함수
 */
function deleteNotice(board_no, success, fail) {
    local.delete(`${url}/delete/${board_no}`).then(success).catch(fail);
}

// 함수들을 외부에서 사용할 수 있도록 export
export {
    listNotice,
    modifyNotice,
    deleteNotice,
    writeNotice,
    updateHit
};
