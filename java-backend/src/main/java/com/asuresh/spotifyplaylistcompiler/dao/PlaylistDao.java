package com.asuresh.spotifyplaylistcompiler.dao;

import com.asuresh.spotifyplaylistcompiler.model.Playlist;

import java.util.List;

public interface PlaylistDao {
    void createPlaylist(Playlist playlist, String userId);
    void linkTrackToPlaylist(String playlistId, String trackId);
    List<Playlist> getPlaylists();
}
