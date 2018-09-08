package com.interview.SongGathering.Controller;

import com.interview.SongGathering.Exceptions.IllegalQueryException;
import com.interview.SongGathering.Models.WordCount;

import java.util.List;

public class WordCountDaoImpl implements WordCountDao {

    private WordCountDao wordCountDao;

    public WordCountDaoImpl(WordCountDao wordCountDao) {
        this.wordCountDao = wordCountDao;
    }

    @Override
    public List<WordCount> getWordCountByParameters(String word, String startDate, String endDate) throws IllegalQueryException {
        if (word.length() == 0)
            throw new IllegalQueryException("Word cannot be blank");
        if (startDate.length() == 0)
            throw new IllegalQueryException("Start date cannot be blank");
        if (endDate.length() == 0)
            throw new IllegalQueryException("End date cannot be blank");

        // create a white-filter to remove any emojis to simplify searching
        String characterFilter = "[^\\p{L}\\p{M}\\p{N}\\p{P}\\p{Z}\\p{Cf}\\p{Cs}\\s]";
        String wordEmojisRemoved = word.replaceAll(characterFilter, "");

        return wordCountDao.getWordCountByParameters(wordEmojisRemoved, startDate, endDate);
    }
}
