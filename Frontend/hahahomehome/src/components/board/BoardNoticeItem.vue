<script setup>

</script>

<template>
        <tr>
          <th scope="row">{{ notice.board_no }}</th>
          <td>{{ notice.title }}</td>
          <td>{{ notice.user_id }}</td>
          <td>{{ notice.register_time }}</td>
          <td>{{ notice.hit }}</td>
          <v-dialog v-model="dialogStates[`dialog${notice.board_no}`]" width="auto">
            <v-card>
              <v-card-text>
                <h2>{{ notice.board_no }}. {{ notice.title }}</h2>
                <hr />
                <div>
                  {{ notice.content }}
                </div>
                <hr />
                <div>작성일: {{ notice.register_time }}</div>
              </v-card-text>
              <v-card-actions>
                <v-btn
                  color="primary"
                  block
                  @click="dialogStates[`dialog${notice.board_no}`] = false"
                  >Close Dialog</v-btn
                >
              </v-card-actions>
            </v-card>
          </v-dialog>
        </tr>
</template>

<style scoped>

</style>