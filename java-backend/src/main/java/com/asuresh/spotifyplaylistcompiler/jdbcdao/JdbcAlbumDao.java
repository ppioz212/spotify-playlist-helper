package com.asuresh.spotifyplaylistcompiler.jdbcdao;

import com.asuresh.spotifyplaylistcompiler.model.Album;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAlbumDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcAlbumDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createAlbum(Album album) {
        String sql = "INSERT INTO album (id, name, artists, user_id) VALUES (?, ?, ?, ?) ON CONFLICT (id) " +
                "DO NOTHING";
        jdbcTemplate.update(sql,
                album.getId(),
                album.getName(),
                album.getArtists(),
                album.getUserId());
    }

    public List<Album> getAlbums(String userId) {
        List<Album> albums = new ArrayList<>();
        String sql = "SELECT * FROM album where user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        while (results.next()) {
            albums.add(mapRowToAlbum(results));
        }
        return albums;
    }

    public List<String> getAlbumIds(String userId) {
        List<String> albums = new ArrayList<>();
        String sql = "SELECT * FROM album where user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        while (results.next()) {
            albums.add(results.getString("id"));
        }
        return albums;
    }

    private Album mapRowToAlbum(SqlRowSet results) {
        Album album = new Album();
        album.setId(results.getString("id"));
        album.setUserId(results.getString("user_id"));
        album.setName(results.getString("name"));
        album.setArtists(results.getString("artists"));
        return album;
    }

    public void linkTrackToAlbum(String albumId, String trackId, String userId) {
        String sql = "INSERT INTO album_track (album_id, track_id, user_id) VALUES (?, ?, ?);";
        jdbcTemplate.update(sql, albumId, trackId, userId);
    }

    public void deleteAlbums(String userId) {
        String albumUnlinkSql = "DELETE FROM album_track WHERE user_id = ?;";
        String albumSql = "DELETE FROM album WHERE user_id = ?";
        jdbcTemplate.update(albumUnlinkSql, userId);
        jdbcTemplate.update(albumSql, userId);
    }
}
