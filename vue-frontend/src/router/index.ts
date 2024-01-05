import { createRouter, createWebHistory } from "vue-router";
import PlaylistUI from "../components/PlaylistUI.vue";
import LoginPage from "../components/LoginPage.vue";
import ResultPage from "../components/ResultPage.vue";

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: "/login",
      name: "login",
      component: LoginPage,
    },
    {
      path: "/playlist",
      name: "playlist",
      component: PlaylistUI,
    },
    {
      path: "/resultPage/:newPlaylistId",
      name: "results-page",
      component: ResultPage,
    },
  ],
});

export default router;
