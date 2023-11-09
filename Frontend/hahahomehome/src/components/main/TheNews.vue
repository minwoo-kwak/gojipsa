<script setup></script>

<template>
  <div class="news">
    <h2>부동산 뉴스</h2>
    <table class="table table-hover">
      <thead>
        <tr>
          <th scope="col">#</th>
          <th scope="col">제목</th>
          <th scope="col">언론사</th>
          <th scope="col">날짜</th>
          <th scope="col">조회수</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <th scope="row">1</th>
          <td>
            <a
              href="https://www.mk.co.kr/news/realestate/10870756"
              target="_blank"
              rel="noopener noreferrer"
              >뉴스 제목입니다.</a
            >
          </td>
          <td>매일경제</td>
          <td>2023-11-09 17:02:41</td>
          <td>@mdo</td>
        </tr>
        <tr>
          <th scope="row">2</th>
          <td>Jacob</td>
          <td>Thornton</td>
          <td>2023-11-09 17:02:41</td>
          <td>@fat</td>
        </tr>
        <tr>
          <th scope="row">3</th>
          <td colspan="2">Larry the Bird</td>
          <td>2023-11-09 17:02:41</td>
          <td>@twitter</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped>
.news {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 40%;
}
</style>
