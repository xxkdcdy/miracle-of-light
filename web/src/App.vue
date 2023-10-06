<template>
  <NavBar />
  <router-view/>
</template>

<script>
import NavBar from './components/NavBar.vue'
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap/dist/js/bootstrap"
import { useStore } from 'vuex'

export default {
  components: {
    NavBar
  },
  setup() {
    const store = useStore();
        const jwt_token = localStorage.getItem("jwt_token");
        if(jwt_token) {
            store.commit("updateToken", jwt_token);
            store.dispatch("getinfo", {
                success() {
                    store.commit("updatePullingInfo", false);
                },
                error() {
                    store.commit("updatePullingInfo", false);
                }
            })
        } else {
            store.commit("updatePullingInfo", false);
        }
  }
}
</script>


<style>
body {
  background-image: url("./assets/images/background.png");
  background-size: cover;
}
</style>
