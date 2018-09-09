package com.interview.SongGathering.Endpoints;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.interview.SongGathering.Controller.SongDao;
import com.interview.SongGathering.Controller.WordCountDao;
import com.interview.SongGathering.Exceptions.IllegalQueryException;
import com.interview.Common.Services.CronJob;
import org.skife.jdbi.v2.DBI;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WordQueryResource implements WordQueryEndpoint {

    private final SongDao songDao;
    private final WordCountDao wordCountDao;

    public WordQueryResource(SongDao songDao, WordCountDao wordCountDao) {
        this.songDao = songDao;
        this.wordCountDao = wordCountDao;
    }

    // health check endpoint
    @GET
    @Path("/ping")
    @Produces("text/plain")
    public String ping() {
        return "pong";
    }

    @GET
    @Path("/songs")
    @Override
    public Response getAllSongs() {
        return Response.ok().entity(songDao.getSongs()).build();
    }

    @GET
    @Path("/word")
    @Produces("application/json")
    @Override
    public Response getWordOccurrences(@QueryParam("word") String word, @QueryParam("from") String from, @QueryParam("to") String to) {
        Object count;
        try {
            count = wordCountDao.getWordCountByParameters(word, from, to);
        } catch (IllegalQueryException e) {
            e.printStackTrace();
            return Response.status(400).entity(getErrorJson(e)).build();
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.createObjectNode();
        ((ObjectNode) rootNode).put("word", word);
        ((ObjectNode) rootNode).putPOJO("timeSeries", count);

        String jsonString;
        try {
            jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return Response.status(500).entity(getErrorJson(e)).build();
        }
        return Response.status(200).entity(jsonString).build();
    }

    private String getErrorJson(Exception e) {
        return "{\"error\":\"" + e.getMessage() + "\"}";
    }
}
