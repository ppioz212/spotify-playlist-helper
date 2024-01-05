package com.asuresh.spotifyplaylistcompiler.services;

import java.util.List;

public class PlaylistDTO {
    private List<String> playlistsToAdd;
    private List<String> albumsToAdd;

    private String nameOfPlaylist;
    private boolean addLikedSongs;
    private double maxTempo;
    private double minTempo;

    public List<String> getAlbumsToAdd() {
        return albumsToAdd;
    }

    public List<String> getPlaylistsToAdd() {
        return playlistsToAdd;
    }

    public boolean isAddLikedSongs() {
        return addLikedSongs;
    }

    public String getNameOfPlaylist() {
        return nameOfPlaylist;
    }

    public double getMaxTempo() {
        return maxTempo;
    }

    public double getMinTempo() {
        return minTempo;
    }
}
