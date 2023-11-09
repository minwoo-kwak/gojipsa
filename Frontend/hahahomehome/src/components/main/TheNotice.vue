<script setup></script>

<template>
  <div class="notice">
    <h2>공지사항</h2>
    <table class="table table-hover">
      <thead>
        <tr>
          <th scope="col">#</th>
          <th scope="col">제목</th>
          <th scope="col">작성자</th>
          <th scope="col">작성일</th>
          <th scope="col">조회수</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <th scope="row">1</th>
          <td>Mark</td>
          <td>Otto</td>
          <td>2023-00-00</td>
          <td>@mdo</td>
        </tr>
        <tr @click="dialog = true">
          <th scope="row">2</th>
          <td>
            <div class="text-center">
              공지사항 제목
              <v-dialog v-model="dialog" width="auto">
                <v-card>
                  <v-card-text>
                    공지사항 내용내용내용 공지사항 내용내용내용 <br />
                    공지사항 내용내용내용 공지사항<br />
                    내용내용내용
                  </v-card-text>
                  <v-card-actions>
                    <v-btn color="primary" block @click="dialog = false">Close Dialog</v-btn>
                  </v-card-actions>
                </v-card>
              </v-dialog>
            </div>
          </td>
          <td>Thornton</td>
          <td>2023-00-00</td>
          <td>@fat</td>
        </tr>
        <tr>
          <th scope="row">3</th>
          <td colspan="2">Larry the Bird</td>
          <td>2023-00-00</td>
          <td>@twitter</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>
<script>
export default {
  data() {
    return {
      dialog: false
    }
  }
}
</script>

<style scoped lang="scss">
.notice {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 40%;
}
</style>
