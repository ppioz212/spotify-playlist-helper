package com.asuresh.spotifyplaylistcompiler.jdbcdao;

import com.asuresh.spotifyplaylistcompiler.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcUserDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createUser(User user) {
        String sql = "INSERT INTO user_profile (id, display_name, albums_pulled, playlists_pulled, tracks_pulled) " +
                "VALUES (?, ?, ?, ?, ?) ON CONFLICT (id) DO NOTHING;";
        jdbcTemplate.update(sql,
                user.getId(),
                user.getDisplayName(),
                user.isAlbumsPulled(),
                user.isPlaylistsPulled(),
                user.isTracksPulled());
    }

    public void updateDataPulled(TableType tableType, boolean value, String userId) {
        String sql = "UPDATE user_profile " +
                "SET " + tableType.getValue() + "_pulled = ? " +
                "WHERE id = ?";
        jdbcTemplate.update(sql, value, userId);
    }

    public boolean wasDataPreviouslyPulled(TableType tableType, String userId) {
        String sql = "SELECT " + tableType.getValue() + "_pulled " +
                "FROM user_profile " +
                "WHERE id = ?";
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, boolean.class, userId));
    }

    public void updateLastUpdatedTimestamp(String userId) {
        String sql = "update user_profile set last_updated = current_timestamp " +
                "where id = ?;";
        jdbcTemplate.update(sql, userId);
    }

    public void deleteUser(String userId) {
        String sql = "DELETE FROM user_profile WHERE id = ?;";
        jdbcTemplate.update(sql, userId);
    }

    public boolean checkUserExist(User user) {
        String sql = "SELECT * FROM user_profile WHERE id = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, user.getId());
        return result.next();
    }
}
