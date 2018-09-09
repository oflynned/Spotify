package com.interview.SongGathering.Controller;

import com.interview.SongGathering.Exceptions.IllegalQueryException;
import com.interview.SongGathering.Models.WordCount;

import javax.annotation.Nonnull;
import java.util.List;

public class WordCountDaoImpl implements WordCountDao {

    private static final String EMOJI_FILTER = "[^\\p{L}\\p{M}\\p{N}\\p{P}\\p{Z}\\p{Cf}\\p{Cs}\\s]";
    private WordCountDao wordCountDao;

    public WordCountDaoImpl(WordCountDao wordCountDao) {
        this.wordCountDao = wordCountDao;
    }

    @Override
    public List<WordCount> getWordCountByParameters(@Nonnull String word, @Nonnull String startDate, @Nonnull String endDate) throws IllegalQueryException {
        // create a white-filter to remove any emojis to simplify searching
        word = word.replaceAll(EMOJI_FILTER, "");

        if (word.length() == 0)
            throw new IllegalQueryException("Word cannot be blank");
        if (startDate.length() == 0)
            throw new IllegalQueryException("Start date cannot be blank");
        if (endDate.length() == 0)
            throw new IllegalQueryException("End date cannot be blank");

        if (startDate.length() != 8)
            throw new IllegalQueryException("Start date must have the format yyyyMMdd");
        if (endDate.length() != 8)
            throw new IllegalQueryException("End date must have the format yyyyMMdd");

        if (Integer.valueOf(startDate) > Integer.valueOf(endDate))
            throw new IllegalQueryException("Start date cannot be greater than end date");

        return wordCountDao.getWordCountByParameters(word, startDate, endDate);
    }
}
