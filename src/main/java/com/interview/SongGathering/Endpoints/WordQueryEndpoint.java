package com.interview.SongGathering.Endpoints;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

public interface WordQueryEndpoint {
    @GET
    @Path("/songs")
    Response getAllSongs();

    @GET
    @Path("/word")
    Response getWordOccurrences(@QueryParam("word") String word, @QueryParam("from") String from, @QueryParam("to") String to);
}
