<template>
  <MyMenuBar />
  <router-view></router-view>
</template>

<script setup lang="ts">
import MyMenuBar from "./components/MyMenuBar.vue";
import * as services from "./utils/services";
import { useRouter } from "vue-router";

const router = useRouter();
main();
async function main() {
  let doesTokenExist = false;
  if (localStorage.getItem("token") != null) {
    doesTokenExist = true;
  }
  let tokenObject = JSON.parse(localStorage.getItem("token") || "{}");
  let date = new Date();
  if (
    doesTokenExist &&
    date.getTime() / 1000 < tokenObject.timeGenerated + tokenObject.expires_in
  ) {
    router.push("/playlist");
  } else {
    let urlParams = new URLSearchParams(window.location.search);
    const code = urlParams.get("code") || "";
    if (code == "") {
      router.push("/login");
    } else {
      await services.getToken(code);
      //TODO: add logic here when getToken fails (due to error or server being down)
      router.push("/playlist");
    }
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  /* -webkit-font-smoothing: antialiased; */
  /* -moz-osx-font-smoothing: grayscale; */
  text-align: center;
  /* margin-top: 60px; */
}

.mainmenu {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  background: rgba(248, 249, 250, 255);
  border-color: rgba(223, 225, 230, 255);
  border-style: solid;
  margin-top: 20px;
  margin-right: 25%;
  margin-left: 25%;
  padding-bottom: 30px;
  justify-content: center;
}
</style>
