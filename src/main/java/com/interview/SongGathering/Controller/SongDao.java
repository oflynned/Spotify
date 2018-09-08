package com.interview.SongGathering.Controller;

import com.interview.SongGathering.Models.Song;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

@RegisterMapper(SongResultMapper.class)
public interface SongDao {
    @SqlQuery("SELECT * FROM songs")
    List<Song> getSongs();
}