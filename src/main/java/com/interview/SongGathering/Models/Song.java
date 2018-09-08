package com.interview.SongGathering.Models;

public class Song {

    private String title;
    private int id, songId, timestamp;

    public Song(int songId, String title, int timestamp) {
        this.songId = songId;
        this.title = title;
        this.timestamp = timestamp;
    }

    public Song(int id, int songId, String title, int timestamp) {
        this.id = id;
        this.songId = songId;
        this.title = title;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Song{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
