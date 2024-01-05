import axios from "axios";

export async function getToken(code: string) {
  const accessTokenObject = (
    await axios.post("http://localhost:8080/getAccessToken", code)
  ).data;
  const date = new Date();
  accessTokenObject.timeGenerated = date.getTime() / 1000;
  const tokenObjParsed = JSON.stringify(accessTokenObject);
  localStorage.setItem("token", tokenObjParsed);
}

export async function getUser() {
  const tokenObject = JSON.parse(localStorage.getItem("token") || "{}");
  const userObj = (
    await axios.get("http://localhost:8080/getUser", {
      headers: { Authorization: tokenObject.access_token },
    })
  ).data;
  const userObjParsed = JSON.stringify(userObj);
  localStorage.setItem("user", userObjParsed);
}

export async function deleteUser(userId: string) {
  await axios.get("http://localhost:8080/deleteUser", {
    headers: { UserId: userId },
  });
}

export async function getPlaylists() {
  const tokenObject = JSON.parse(localStorage.getItem("token") || "{}");
  const userObject = JSON.parse(localStorage.getItem("user") || "{}");
  const allPlaylistObjs = (
    await axios.get("http://localhost:8080/getPlaylists", {
      headers: {
        Authorization: tokenObject.access_token,
        UserId: userObject.id,
      },
    })
  ).data;
  return allPlaylistObjs;
}

export async function getAlbums() {
  const tokenObject = JSON.parse(localStorage.getItem("token") || "{}");
  const userObject = JSON.parse(localStorage.getItem("user") || "{}");
  const allAlbumObjs = (
    await axios.get("http://localhost:8080/getAlbums", {
      headers: {
        Authorization: tokenObject.access_token,
        UserId: userObject.id,
      },
    })
  ).data;
  return allAlbumObjs;
}

export async function getTracks() {
  const tokenObject = JSON.parse(localStorage.getItem("token") || "{}");
  const userObject = JSON.parse(localStorage.getItem("user") || "{}");
  await axios.get("http://localhost:8080/compileTracks", {
    headers: {
      Authorization: tokenObject.access_token,
      UserId: userObject.id,
    },
  });
}

export async function getMaxTempoOfTracks() {
  const userObject = JSON.parse(localStorage.getItem("user") || "{}");
  const maxTempo: number = (
    await axios.get("http://localhost:8080/tracks/max", {
      headers: {
        UserId: userObject.id,
      },
    })
  ).data;
  console.log(userObject.id)
  return maxTempo;
}

export async function getMinTempoOfTracks() {
  const userObject = JSON.parse(localStorage.getItem("user") || "{}");
  const minTempo: number = (
    await axios.get("http://localhost:8080/tracks/min", {
      headers: {
        UserId: userObject.id,
      },
    })
  ).data;
  return minTempo;
}

export async function generatePlaylist(playlistObject: Object) {
  const tokenObject = JSON.parse(localStorage.getItem("token") || "{}");
  const userObject = JSON.parse(localStorage.getItem("user") || "{}");
  console.log(userObject.id);
  const newPlaylistId: string = (
    await axios.post(
      "http://localhost:8080/generateNewPlaylist",
      playlistObject,
      {
        headers: {
          Authorization: tokenObject.access_token,
          UserId: userObject.id,
        },
      }
    )
  ).data;
  return newPlaylistId;
}
