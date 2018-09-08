package com.interview.SongGathering.Controller;

import com.interview.SongGathering.Exceptions.IllegalQueryException;
import com.interview.SongGathering.Models.Song;
import com.interview.SongGathering.Models.WordCount;

import java.util.List;

public class SongDaoImpl implements SongDao {

    private SongDao songDao;

    public SongDaoImpl(SongDao songDao) {
        this.songDao = songDao;
    }

    @Override
    public List<Song> getSongs() {
        return songDao.getSongs();
    }
}
