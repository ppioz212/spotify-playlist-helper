import { createApp } from "vue";
import { createPinia } from "pinia";
import App from "./App.vue";
import PrimeVue from "primevue/config";
import Button from "primevue/button";
import ProgressSpinner from "primevue/progressspinner";
import Menubar from "primevue/menubar";
import InputText from "primevue/inputtext";
import router from "./router";
import Slider from 'primevue/slider'

import "primevue/resources/themes/saga-blue/theme.css";
import "primevue/resources/primevue.min.css";
import "primeicons/primeicons.css";

const app = createApp(App);
const pinia = createPinia();
app.use(router);
app.use(PrimeVue);
app
  .use(pinia)
  .component("Menubar", Menubar)
  .component("Button", Button)
  .component("ProgressSpinner", ProgressSpinner)
  .component("InputText", InputText)
  .component("Slider", Slider)
  .mount("#app");
