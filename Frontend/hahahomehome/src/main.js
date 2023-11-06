import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'

import App from './App.vue'
import router from './router'

// Vuetify 기본 세팅
// https://vuetifyjs.com/en/getting-started/installation/#existing-projects

// Vuetify
import 'vuetify/styles'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'

const vuetify = createVuetify({
  components,
  directives,
})
// ###########  Vuetify 기본 세팅

// axios 객체 사용
import { instance } from "./api/axios"

const app = createApp(App)

// axios라 쓰면 instance 객체가 제공이 된다
app.provide('axios', instance)

app.use(createPinia().use(piniaPluginPersistedstate))
app.use(router)
app.use(vuetify)

app.mount('#app')
