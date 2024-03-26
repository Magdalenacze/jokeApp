package pl.akademiaspecjalistowit.jokeapp.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import pl.akademiaspecjalistowit.jokeapp.data.Joke;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;

public class JokeApiProvider implements JokeProvider {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public JokeApiProvider(HttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public Joke getJoke() {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://v2.jokeapi.dev/joke/Any"))
                .build();
        return getResponseForJokeRequest(request);
    }

    @Override
    public Joke getJokeByCategory(String category) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://v2.jokeapi.dev/joke/" + category))
                .build();
        return getResponseForJokeRequest(request);
    }

    @Override
    public List<String> getAvailableJokeCategories() {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://v2.jokeapi.dev/categories"))
                .build();
        return getResponseForJokeCategoriesRequest(request);
    }

    private List<String> getResponseForJokeCategoriesRequest(HttpRequest request) {
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            JSONObject jsonObject = new JSONObject(response.body());
            JSONArray jsonArray = jsonObject.getJSONArray("categories");
            return jsonArray
                    .toList()
                    .stream()
                    .map(Object::toString)
                    .collect(Collectors.toList());
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private Joke getResponseForJokeRequest(HttpRequest request) {
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return JokeDtoMapper.toJoke(objectMapper.readValue(response.body(), JokeDto.class));
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
