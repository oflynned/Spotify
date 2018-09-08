package com.interview.SongGathering.Controller;

import com.interview.SongGathering.Models.Song;
import com.interview.SongGathering.Models.WordCount;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WordCountMapper implements ResultSetMapper<WordCount> {
    @Override
    public WordCount map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new WordCount(r.getInt("count"), r.getInt("timestamp"));
    }
}
