package com.interview.Common.Services;

import com.interview.SongGathering.Models.Song;
import org.skife.jdbi.v2.Handle;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DbHelper {
    static void saveSongs(Handle handle, ArrayList<Song> songs) {
        for (Song song : songs) {
            handle.execute("INSERT INTO songs(song_id, title, timestamp) VALUES(?, ?, ?);",
                    song.getSongId(), song.getTitle(), song.getTimestamp());
        }
    }

    static boolean shouldSyncSongs(Handle handle) {
        return handle.select("SELECT * FROM soundcloud_syncs WHERE timestamp = ? AND success = 1;", getTimestamp()).size() == 0;
    }

    static void recordSyncFailure(Handle handle) {
        handle.execute("INSERT INTO soundcloud_syncs(timestamp, success) VALUES(?, ?);",
                getTimestamp(), 0);
    }

    static void recordSyncSuccess(Handle handle) {
        handle.execute("INSERT INTO soundcloud_syncs(timestamp, success) VALUES(?, ?);",
                getTimestamp(), 1);
    }

    public static int getTimestamp() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date now = Calendar.getInstance().getTime();
        return Integer.parseInt(dateFormat.format(now));
    }
}
