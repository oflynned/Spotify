package com.interview.SongGathering.Models;

public class WordCount {

    private int count, timestamp;

    public WordCount(int count, int timestamp) {
        this.count = count;
        this.timestamp = timestamp;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "WordCount{" +
                "count=" + count +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
