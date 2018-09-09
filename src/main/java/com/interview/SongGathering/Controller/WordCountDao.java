package com.interview.SongGathering.Controller;

import com.interview.SongGathering.Exceptions.IllegalQueryException;
import com.interview.SongGathering.Models.WordCount;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

@RegisterMapper(WordCountMapper.class)
public interface WordCountDao {

    // find occurrences of the word fragment by replacing all instances with ''
    // and then work out how much smaller the overall string is with respect to the word length
    // then sum the row counts and group by the timestamp
    @SqlQuery(
            "SELECT timestamp, SUM(count) AS count FROM " +
                    "(SELECT timestamp, ROUND((CHAR_LENGTH(lower(title)) - CHAR_LENGTH(REPLACE(lower(title), :w, ''))) / CHAR_LENGTH(:w)) AS count " +
                    "FROM songs " +
                    "WHERE title LIKE CONCAT('%',:w,'%') AND timestamp >= :start AND timestamp <= :end) AS c " +
                    "GROUP BY timestamp " +
                    "ORDER BY timestamp ASC")
    List<WordCount> getWordCountByParameters(@Bind("w") String word, @Bind("start") String startDate, @Bind("end") String endDate) throws IllegalQueryException;
}
