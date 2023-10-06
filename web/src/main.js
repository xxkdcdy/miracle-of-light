import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

createApp(App).use(store).use(router).mount('#app')
document.title='林业昆虫智能识别系统 @xxkdcdy';