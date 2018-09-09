package com.interview.SongGathering.Endpoints;

import com.github.arteam.jdit.DBIRunner;
import com.github.arteam.jdit.annotations.DataSet;
import com.interview.SongGathering.Controller.WordCountDao;
import com.interview.SongGathering.Controller.WordCountDaoImpl;
import com.interview.SongGathering.Controller.WordCountMapper;
import com.interview.SongGathering.UseTestDb;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static net.jadler.Jadler.closeJadler;
import static net.jadler.Jadler.initJadler;

@RunWith(DBIRunner.class)
@DataSet("schema.sql")
public class WordQueryResourceTest extends UseTestDb {
    private WordCountDaoImpl wordCountDaoImpl;

    @Before
    public void setupTest() {
        super.setup();
        super.getDbi().registerMapper(new WordCountMapper());
        WordCountDao wordCountDao = super.getDbi().onDemand(WordCountDao.class);
        this.wordCountDaoImpl = new WordCountDaoImpl(wordCountDao);

        initJadler();
    }

    @After
    public void tearDown() {
        closeJadler();
    }

    @Test
    public void word_should_return_valid_json() {

    }

    @Test
    public void bad_word_length_should_throw_exception() {

    }

    @Test
    public void bad_date_should_throw_exception() {

    }

}
