package com.interview.SongGathering.Controller;

import com.github.arteam.jdit.DBIRunner;
import com.github.arteam.jdit.annotations.DataSet;
import com.interview.SongGathering.Exceptions.IllegalQueryException;
import com.interview.SongGathering.UseTestDb;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(DBIRunner.class)
@DataSet("schema.sql")
public class WordCountDaoImplTest extends UseTestDb {
    private WordCountDaoImpl wordCountDaoImpl;

    @Before
    public void setupTest() {
        super.setup();
        super.getDbi().registerMapper(new WordCountMapper());
        WordCountDao wordCountDao = super.getDbi().onDemand(WordCountDao.class);
        this.wordCountDaoImpl = new WordCountDaoImpl(wordCountDao);
    }

    @Test
    public void expect_relevant_word_query_is_legal() throws IllegalQueryException {
        wordCountDaoImpl.getWordCountByParameters("legal", "12345678", "12345678");
    }

    @Test(expected = IllegalQueryException.class)
    public void expect_relevant_word_query_cannot_be_blank() throws IllegalQueryException {
        wordCountDaoImpl.getWordCountByParameters("", "12345678", "12345678");
    }

    @Test(expected = IllegalQueryException.class)
    public void expect_relevant_start_query_cannot_be_blank() throws IllegalQueryException {
        wordCountDaoImpl.getWordCountByParameters("legal", "", "12345678");
    }

    @Test(expected = IllegalQueryException.class)
    public void expect_relevant_end_query_cannot_be_blank() throws IllegalQueryException {
        wordCountDaoImpl.getWordCountByParameters("legal", "12345678", "");
    }

    @Test(expected = IllegalQueryException.class)
    public void expect_relevant_start_query_cannot_have_length_longer_than_8() throws IllegalQueryException {
        wordCountDaoImpl.getWordCountByParameters("legal", "123456789", "12345678");
    }

    @Test(expected = IllegalQueryException.class)
    public void expect_relevant_end_query_date_cannot_have_length_longer_than_8() throws IllegalQueryException {
        wordCountDaoImpl.getWordCountByParameters("legal", "12345678", "123456789");
    }

    @Test(expected = IllegalQueryException.class)
    public void expect_relevant_start_query_date_must_have_exactly_8_length() throws IllegalQueryException {
        wordCountDaoImpl.getWordCountByParameters("legal", "0", "12345678");
    }

    @Test(expected = IllegalQueryException.class)
    public void expect_relevant_end_query_date_must_have_exactly_8_length() throws IllegalQueryException {
        wordCountDaoImpl.getWordCountByParameters("legal", "12345678", "0");
    }

    @Test(expected = IllegalQueryException.class)
    public void expect_relevant_end_query_date_must_be_greater_than_start_date() throws IllegalQueryException {
        wordCountDaoImpl.getWordCountByParameters("legal", "00000001", "00000000");
    }
}
