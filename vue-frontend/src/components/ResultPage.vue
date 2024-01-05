
<template>
  <div>
    <iframe title="Spotify Embed: Recommendation Playlist " :src="iframeUrl" width="50%" height="500px" frameBorder="0"
      allow="autoplay; clipboard-write; encrypted-media; fullscreen; picture-in-picture" loading="lazy" />
  </div>
</template>
<script setup lang="ts">
import { useRoute } from "vue-router";
import { ref, onMounted } from "vue";

const route = useRoute();
const iframeUrl = ref<string>();
onMounted(() => {
  iframeUrl.value =
    "https://open.spotify.com/embed/playlist/" +
    route.params.newPlaylistId +
    "?utm_source=generator&theme=0";
});
</script>
<style scoped></style>