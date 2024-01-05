<template>
  <Menubar :model="items" class="menubar">
    <template #start>
      <img alt="logo" src="../assets/spotify_logo_alt.png" height="40" class="mr-2" />
    </template>
    <template #end>
      <Button label="Logout" severity="danger" @click="logout()" />
    </template>
  </Menubar>
</template>
<script setup lang="ts">
import { reactive } from "vue";

const items = reactive([
  {
    label: "Home" as string,
    url: "http://localhost:5173" as string,
  },
]);

function logout() {
  localStorage.removeItem("token");
  localStorage.removeItem("user");
  window.location.href = "http://localhost:5173";
}
</script>
<style scoped>
.menubar {
  background: white;
  border: none;
  border-bottom: solid;
  border-color: rgba(223, 225, 230, 255);
}
</style>