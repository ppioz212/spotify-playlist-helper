<template>
  <main class="mainmenu" v-if="playlistUI">
    <p style="margin-bottom: 20px; align-items: center">Enter the name of your playlist:</p>
    <div class="card flex justify-content-center">
      <span class="p-float-label">
        <InputText id="playlistname" v-model="inputValue" />
        <label class="centerInputText" for="playlistname">Name of Playlist</label>
      </span>
    </div>
    <p style="margin-bottom: 0px">
      Select what Playlists/Albums you'd like to include:
    </p>
    <p style="margin-top: 0px">(You must select at least one item)</p>
    <Button class="button" label="Generate Playlist" @click="generatePlaylist()" />
    <Button class="button" label="Reset Data" severity="warning" @click="resetDatabase()" />
    <section class="slider-section">
      <div class="slider-text">
        Min Tempo:
        <InputText v-model.number="tempoRange[0]" />
        Max Tempo:
        <InputText v-model.number="tempoRange[1]" />
      </div>
      <div class="slider-container">
        <Slider class="slider" :min="minTempo" :max="maxTempo" v-model="tempoRange" range />
      </div>
    </section>
  </main>

  <MySpinner :loading-message="loadingMessage" v-show="loadingScreen" />

  <body v-if="playlistUI" class="playlist-ui">
    <section class="checkboxes">
      <Checkbox v-model="likedSongsBox" :binary="true" />
      <label> Liked Songs</label>
    </section>
    <div>
      Selected Playlists: {{ checkedPlaylists.length }} Selected Albums:
      {{ checkedAlbums.length }}
    </div>
    <section class="playlist-album-selection" v-if="playlistUI">
      <div class="selectionElement">
        <h3>Playlists</h3>
        <section class="checkboxes">
          <Checkbox v-model="userCreatedBox" :binary="true"
            @change="playlistCheckBoxEvent('UserCreated', userCreatedBox)" />
          <label> User Created Playlists </label>
          <Checkbox v-model="followedPlaylistsBox" :binary="true"
            @change="playlistCheckBoxEvent('Followed', followedPlaylistsBox)" />
          <label> Followed Playlists</label>
        </section>
        <div v-for="playlist in allPlaylistObjs" :key="playlist['id']">
          <Checkbox v-model="checkedPlaylists" :value="playlist['id']" />
          <label>
            {{ playlist["name"] }} by {{ playlist["owner"]["displayName"] }}
          </label>
        </div>
      </div>
      <div class="selectionElement">
        <h3>Albums</h3>
        <section class="checkboxes">
          <Checkbox v-model="allAlbumsCheck" :binary="true" @change="allAlbumsCheckBoxEvent()" />
          <label> Select All Albums </label>
        </section>
        <div v-for="album in allAlbumObjs" :key="album['id']">
          <Checkbox v-model="checkedAlbums" :value="album['id']" />
          <label>
            {{ album["name"] }} by {{ album["artists"] }}
          </label>
        </div>
      </div>
    </section>
  </body>
</template>

<script setup lang="ts">
import MySpinner from "./MySpinner.vue";
import Checkbox from "primevue/checkbox";
import { ref, onMounted, reactive } from "vue";
import * as services from "../utils/services";
import { useRouter } from "vue-router";

const router = useRouter()

const loadingMessage = ref<string>("Loading Playlists and Albums...");
const loadingScreen = ref<boolean>(true);
const playlistUI = ref<boolean>(false);
const playlistSelection = ref<boolean>(false);

const userCreatedBox = ref<boolean>(false);
const followedPlaylistsBox = ref<boolean>(false);
function playlistCheckBoxEvent(aggregateType: string, checkbox: boolean) {
  const filteredPlaylistIds: string[] = [];
  let filteredPlaylistObjs = [];
  if (aggregateType == "UserCreated") {
    filteredPlaylistObjs = allPlaylistObjs.value.filter(
      (item) => item["owner"]["id"] == user.id
    );
  } else {
    filteredPlaylistObjs = allPlaylistObjs.value.filter(
      (item) => item["owner"]["id"] != user.id
    );
  }

  for (let playlistObj of filteredPlaylistObjs) {
    filteredPlaylistIds.push(playlistObj["id"]);
  }

  if (checkbox) {
    for (let playlistId of filteredPlaylistIds) {
      if (!checkedPlaylists.value.includes(playlistId)) {
        checkedPlaylists.value.push(playlistId);
      }
    }
  }

  if (!checkbox) {
    checkedPlaylists.value = checkedPlaylists.value.filter(
      (item) => !filteredPlaylistIds.includes(item)
    );
  }
}
const allAlbumsCheck = ref<boolean>(false);
function allAlbumsCheckBoxEvent() {
  const allAlbumIds: string[] = [];
  for (let albumObj of allAlbumObjs.value) {
    allAlbumIds.push(albumObj["id"]);
  }

  if (allAlbumsCheck.value) {
    for (let albumId of allAlbumIds) {
      if (!checkedAlbums.value.includes(albumId)) {
        checkedAlbums.value.push(albumId);
      }
    }
  }
  if (!allAlbumsCheck.value) {
    checkedAlbums.value = [];
  }
}

const likedSongsBox = ref<boolean>(false);
const checkedPlaylists = ref<string[]>([]);
const checkedAlbums = ref<string[]>([]);
const inputValue = ref<string>("");
async function generatePlaylist() {
  const playlistObject = {
    nameOfPlaylist: inputValue.value,
    playlistsToAdd: checkedPlaylists.value,
    addLikedSongs: likedSongsBox.value,
    albumsToAdd: checkedAlbums.value,
    minTempo: tempoRange.value[0],
    maxTempo: tempoRange.value[1]
  };
  playlistUI.value = false;
  playlistSelection.value = false;
  loadingMessage.value = "Generating Playlist...";
  loadingScreen.value = true;
  const newPlaylistId: string = await services.generatePlaylist(
    playlistObject
  );
  router.push({ name: "results-page", params: { newPlaylistId } });
}

async function resetDatabase() {
  await services.deleteUser(user.id);
  localStorage.removeItem("user");
  window.location.reload();
}

async function setUserObject() {
  if (localStorage.getItem("user") == null) {
    await services.getUser();
  }
  const userObj = JSON.parse(localStorage.getItem("user") || "{}");
  user.id = userObj.id;
  user.display_name = userObj.displayName;
}

const allAlbumObjs = ref([]);
const allPlaylistObjs = ref([]);
const user = reactive({ id: String(), display_name: String() });
const maxTempo = ref();
const minTempo = ref();
const tempoRange = ref([]);
onMounted(async () => {
  await setUserObject();
  allPlaylistObjs.value = await services.getPlaylists();
  console.log("Number of playlists returned: " + allPlaylistObjs.value.length);
  console.log(allPlaylistObjs.value);

  allAlbumObjs.value = await services.getAlbums();
  playlistUI.value = true;
  playlistSelection.value = true;
  console.log("Number of albums returned: " + allAlbumObjs.value.length);
  console.log(allAlbumObjs.value);

  loadingMessage.value = "Loading all tracks";
  await services.getTracks();
  loadingScreen.value = false;
  maxTempo.value = await services.getMaxTempoOfTracks();
  minTempo.value = await services.getMinTempoOfTracks();
  tempoRange.value.push(minTempo.value);
  tempoRange.value.push(maxTempo.value)
  console.log(tempoRange.value)
})
</script>
<style scoped>
.checkboxes {
  display: flex;
  justify-content: center;
}

.checkboxes label {
  margin: 0 5px;
}

.slider-section {
  margin-top: 10px;
}

.slider-container {
  display: flex;
  justify-content: center;
  align-items: center;
}

.slider {
  margin: 15px 0 0 0;
  flex-basis: 400px;
}

.button {
  margin: 0 5px 5px 5px;
}

.centerInputText {
  width: 100%;
}

.playlist-album-selection {
  display: flex;
  justify-content: center;
}

.playlist-album-selection label {
  margin-left: 5px;
}

.selectionElement {
  text-align: left;
  min-width: 350px;
  max-width: 500px;
  background: rgba(248, 249, 250, 255);
}

.selectionElement h3 {
  margin: 5px;
  padding: 0;
  text-align: center;
  background: rgba(248, 249, 250, 255);
}
</style>