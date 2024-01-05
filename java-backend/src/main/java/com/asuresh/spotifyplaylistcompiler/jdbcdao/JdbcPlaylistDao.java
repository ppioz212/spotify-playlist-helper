package com.asuresh.spotifyplaylistcompiler.jdbcdao;

import com.asuresh.spotifyplaylistcompiler.model.Playlist;
import com.asuresh.spotifyplaylistcompiler.model.playlistmodel.Owner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcPlaylistDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcPlaylistDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createPlaylist(Playlist playlist) {
        String sql = "INSERT INTO playlist (id, name, owner_id, owner_display_name, user_id) VALUES (?, ?, ?, ?, ?) ON CONFLICT (id) " +
                "DO NOTHING;";
        jdbcTemplate.update(sql,
                playlist.getId(),
                playlist.getName(),
                playlist.getOwner().getId(),
                playlist.getOwner().getDisplayName(),
                playlist.getUserId());
    }

    public List<Playlist> getPlaylists(String userId) {
        List<Playlist> playlists = new ArrayList<>();
        String sql = "SELECT * FROM playlist WHERE user_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        while (results.next()) {
            playlists.add(mapRowToPlaylist(results));
        }
        return playlists;
    }

    public List<String> getPlaylistIds(String userId) {
        List<String> playlists = new ArrayList<>();
        String sql = "SELECT * FROM playlist where user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        while (results.next()) {
            playlists.add(results.getString("id"));
        }
        return playlists;
    }

    public void linkTrackToPlaylist(String playlistId, String trackId, String userId) {
        String sql = "INSERT INTO playlist_track (playlist_id, track_id, user_id) VALUES (?, ?, ?);";
        jdbcTemplate.update(sql, playlistId, trackId, userId);
    }

    private Playlist mapRowToPlaylist(SqlRowSet results) {
        Playlist playlist = new Playlist();
        playlist.setName(results.getString("name"));
        playlist.setId(results.getString("id"));
        playlist.setOwner(new Owner(
                results.getString("owner_id"),
                results.getString("owner_display_name")));
        playlist.setUserId(results.getString("user_id"));
        return playlist;
    }

    public void deletePlaylists(String userId) {
        String playlistUnlinkSql = "DELETE FROM playlist_track WHERE user_id = ?;";
        String playlistSql = "DELETE FROM playlist WHERE user_id = ?;";
        jdbcTemplate.update(playlistUnlinkSql, userId);
        jdbcTemplate.update(playlistSql, userId);
    }
}
