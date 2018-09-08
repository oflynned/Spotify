package com.interview.SongGathering.Controller;

import com.interview.SongGathering.Models.Song;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SongResultMapper implements ResultSetMapper<Song> {
    @Override
    public Song map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new Song(r.getInt("id"), r.getInt("song_id"),
                r.getString("title"), r.getInt("timestamp"));
    }
}
