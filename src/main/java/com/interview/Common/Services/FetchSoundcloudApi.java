package com.interview.Common.Services;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.interview.SongGathering.Exceptions.SoundcloudServerException;
import com.interview.SongGathering.Models.Song;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

import static com.interview.Common.Services.DbHelper.getTimestamp;

public class FetchSoundcloudApi {
    private static final String URL = "https://api.soundcloud.com/tracks?client_id=3a792e628dbaf8054e55f6e134ebe5fa";

    public static ArrayList<Song> fetchSoundcloudSongs() throws SoundcloudServerException {
        Response response = fetchRemoteData();
        if (response.getStatus() != 200)
            throw new SoundcloudServerException();

        JsonElement element = new JsonParser().parse(response.readEntity(String.class));
        JsonArray array = element.getAsJsonArray();
        ArrayList<Song> songs = new ArrayList<>();

        for (int i = 0; i < array.size(); i++) {
            JsonObject o = array.get(i).getAsJsonObject();
            String title = o.get("title").getAsString();
            int songId = o.get("id").getAsInt();
            int timestamp = getTimestamp();
            songs.add(new Song(songId, title, timestamp));
        }

        return songs;
    }

    private static Response fetchRemoteData() {
        Client client = ClientBuilder.newClient();
        return client.target(URL).request(MediaType.APPLICATION_JSON).get(Response.class);
    }
}
