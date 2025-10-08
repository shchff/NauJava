package ru.shchff.task4;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Task4
{
    private static final String ROOT_URI = "https://httpbin.org";
    private static final String NODE = "origin";

    public void solution()
    {
        try (HttpClient client = HttpClient.newHttpClient())
        {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(ROOT_URI + "/ip"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println(getOriginIPFromJSON(response.body()));
        }
        catch (JsonProcessingException e)
        {
            throw new RuntimeException("Parsing exception", e);
        }
        catch (IOException | InterruptedException e)
        {
            throw new RuntimeException("Connection exception", e);

        }
    }

    private String getOriginIPFromJSON(String json) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(json);
        return node.get(NODE).asText();
    }
}
