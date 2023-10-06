import { createRouter, createWebHistory } from 'vue-router'
import NotFound from '../views/error/NotFound.vue'
import UserAccountLoginView from '../views/user/account/UserAccountLoginView.vue'
import UserAccountRegisterView from '../views/user/account/UserAccountRegisterView.vue'
import UserProfileIndexView from '../views/user/profile/UserProfileIndexView.vue'
import UserProfileNotificationView from '../views/user/profile/UserProfileNotificationView.vue'
import UserScoreIndexView from '../views/user/score/UserScoreIndexView.vue'
import UserScoreRanklistView from '../views/user/score/UserScoreRanklistView.vue'
import UserSecurityEmailView from '../views/user/security/UserSecurityEmailView.vue'
import UserSecurityIdentificationView from '../views/user/security/UserSecurityIdentificationView.vue'
import UserSecurityChangepwdView from '../views/user/security/UserSecurityChangePasswordView.vue'
import UserSecurityFindpwdView from '../views/user/security/UserSecurityFindPasswordView.vue'
import PredictShowIndexView from '../views/predict/show/PredictIndexView.vue'
import PredictShowMarkView from '../views/predict/show/PredictMarkView.vue'
import PredictShowRecordView from '../views/predict/show/PredictRecordView.vue'
import PredictDataView from '../views/predict/data/PredictDataView.vue'
import HomeIndexView from '../views/home/HomeIndexView.vue'
import ManageIndexView from '../views/manage/ManageIndexView.vue'
import ManageUserView from '../views/manage/ManageUserView.vue'
import ManageMarkView from '../views/manage/ManageMarkView.vue'
import ManageVersionView from '../views/manage/ManageVersionView.vue'
import ManageInsectView from '../views/manage/ManageInsectView.vue'
import ManageExchangeView from '../views/manage/ManageExchangeView.vue'
import ManageExchangeLogView from '../views/manage/ManageExchangeLogView.vue'
import store from '../store/index'

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeIndexView,
    meta: {
      requestAuth: false,
    }
  },
  { 
    path: '/user/account/login/',
    name: 'user_account_login',
    component: UserAccountLoginView,
    meta: {
      requestAuth: false,
    }
  },
  { 
    path: '/user/account/register/',
    name: 'user_account_register',
    component: UserAccountRegisterView,
    meta: {
      requestAuth: false,
    }
  },
  { 
    path: '/user/profile/index/',
    name: 'user_profile_index',
    component: UserProfileIndexView,
    meta: {
      requestAuth: true,
    }
  },
  { 
    path: '/user/profile/notification/',
    name: 'user_profile_notification',
    component: UserProfileNotificationView,
    meta: {
      requestAuth: true,
    }
  },
  { 
    path: '/user/score/index/',
    name: 'user_score_index',
    component: UserScoreIndexView,
    meta: {
      requestAuth: true,
    }
  },
  { 
    path: '/user/score/ranklist/',
    name: 'user_score_ranklist',
    component: UserScoreRanklistView,
    meta: {
      requestAuth: false,
    }
  },
  { 
    path: '/user/security/email/',
    name: 'user_security_email',
    component: UserSecurityEmailView,
    meta: {
      requestAuth: true,
    }
  },
  { 
    path: '/user/security/identification/',
    name: 'user_security_identification',
    component: UserSecurityIdentificationView,
    meta: {
      requestAuth: true,
    }
  },
  { 
    path: '/user/security/changepwd/',
    name: 'user_security_changepwd',
    component: UserSecurityChangepwdView,
    meta: {
      requestAuth: true,
    }
  },
  { 
    path: '/user/security/findpwd/',
    name: 'user_security_findpwd',
    component: UserSecurityFindpwdView,
    meta: {
      requestAuth: false,
    }
  },
  { 
    path: '/predict/show/index/',
    name: 'predict_show_index',
    component: PredictShowIndexView,
    meta: {
      requestAuth: true,
    }
  },
  { 
    path: '/predict/show/mark/',
    name: 'predict_show_mark',
    component: PredictShowMarkView,
    meta: {
      requestAuth: true,
    }
  },
  { 
    path: '/predict/show/record/',
    name: 'predict_show_record',
    component: PredictShowRecordView,
    meta: {
      requestAuth: true,
    }
  },
  { 
    path: '/predict/data/index/',
    name: 'predict_data_index',
    component: PredictDataView,
    meta: {
      requestAuth: true,
    }
  },
  { 
    path: '/manage/index/',
    name: 'manage_index',
    component: ManageIndexView,
    meta: {
      requestAuth: true,
    }
  },
  { 
    path: '/manage/user/',
    name: 'manage_user',
    component: ManageUserView,
    meta: {
      requestAuth: true,
    }
  },
  { 
    path: '/manage/mark/',
    name: 'manage_mark',
    component: ManageMarkView,
    meta: {
      requestAuth: true,
    }
  },
  { 
    path: '/manage/version/',
    name: 'manage_version',
    component: ManageVersionView,
    meta: {
      requestAuth: true,
    }
  },
  { 
    path: '/manage/insect/',
    name: 'manage_insect',
    component: ManageInsectView,
    meta: {
      requestAuth: true,
    }
  },
  { 
    path: '/manage/exchange/',
    name: 'manage_exchange',
    component: ManageExchangeView,
    meta: {
      requestAuth: true,
    }
  },
  { 
    path: '/manage/exchange/log/',
    name: 'manage_exchange_log',
    component: ManageExchangeLogView,
    meta: {
      requestAuth: true,
    }
  },
  { 
    path: '/404/',
    name: '404',
    component: NotFound,
    meta: {
      requestAuth: false,
    }
  },
  {
    path: '/:catchAll(.*)',
    name: 'default',
    redirect: '/404/',
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  if (to.meta.requestAuth && (!store.state.user.is_login || store.state.user.flag === "3")) {
    next({name: "home"});
  } else {
    next();
  }
})

export default router
