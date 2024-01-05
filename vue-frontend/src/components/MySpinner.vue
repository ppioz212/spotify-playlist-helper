<template>
  <div class="container">
    <div class="row justify-content-md-center" style="margin-top: 5px">
      <p>{{ props.loadingMessage }}</p>
      <ProgressSpinner strokeWidth="4" fill="var(--surface-ground)" animationDuration="1.5s"
        aria-label="Custom ProgressSpinner" />
    </div>
  </div>
</template>
<script setup lang="ts">
import { defineProps } from "vue";

const props = defineProps({
  loadingMessage: {
    type: String,
    required: false,
  },
});
</script>
<style scoped></style>