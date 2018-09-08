package com.interview.Common.Services;

import com.interview.SongGathering.Exceptions.SoundcloudServerException;
import com.interview.SongGathering.Models.Song;
import org.knowm.sundial.Job;
import org.knowm.sundial.SundialJobScheduler;
import org.knowm.sundial.annotations.SimpleTrigger;
import org.knowm.sundial.exceptions.JobInterruptException;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@SimpleTrigger(repeatInterval = 1, timeUnit = TimeUnit.HOURS)
public class CronJob extends Job {

    @Override
    public void doRun() throws JobInterruptException {
        DBI dbi = (DBI) SundialJobScheduler.getServletContext().getAttribute("dbi");
        sync(dbi);
    }

    public static void sync(DBI dbi) {
        Handle handle = dbi.open();

        if (DbHelper.shouldSyncSongs(handle)) {
            ArrayList<Song> songs;
            try {
                songs = FetchSoundcloudApi.fetchSoundcloudSongs();
            } catch (SoundcloudServerException e) {
                e.printStackTrace();
                DbHelper.recordSyncFailure(handle);
                return;
            }

            DbHelper.saveSongs(handle, songs);
            DbHelper.recordSyncSuccess(handle);
        }

        handle.close();
    }
}
