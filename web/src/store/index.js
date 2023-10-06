import { createStore } from 'vuex'
import ModuleUser from './user'
import ModulePredictor from './predictor'
import ModuleNotification from './notification'

export default createStore({
  state: {
  },
  mutations: {
  },
  actions: {
  },
  modules: {
    user: ModuleUser,
    predictor: ModulePredictor,
    notification: ModuleNotification,
  }
})
