package com.interview.SongGathering.Services;

import com.github.arteam.jdit.DBIRunner;
import com.github.arteam.jdit.annotations.DataSet;
import com.interview.Common.Services.FetchSoundcloudApi;
import com.interview.SongGathering.UseTestDb;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import static net.jadler.Jadler.onRequest;

@RunWith(DBIRunner.class)
@DataSet("schema.sql")
public class FetchSoundcloudApiTest extends UseTestDb {

    private String loadJsonFile(String filename) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(filename);
        File file = new File(resource.getPath());
        byte[] encoded = Files.readAllBytes(Paths.get(file.getPath()));
        return new String(encoded, "utf-8");
    }

    @Test
    public void soundcloud_service_success_should_populate_song_table() throws IOException {
        onRequest()
                .havingMethodEqualTo("GET")
                .havingPathEqualTo(FetchSoundcloudApi.URL)
                .respond()
                .withStatus(200)
                .withBody(loadJsonFile("sample_soundcloud_response_20180905.json"))
                .withEncoding(Charset.forName("UTF-8"))
                .withContentType("application/json; charset=UTF-8");
    }

    @Test
    public void soundcloud_service_success_should_not_populate_song_table_already_populated_today() {

    }

    @Test
    public void soundcloud_service_success_should_log_success_to_table() {

    }

    @Test
    public void soundcloud_service_failure_should_log_failure_to_table() {

    }

    @Test
    public void soundcloud_service_failure_should_not_populate_songs_to_table() {

    }
}
