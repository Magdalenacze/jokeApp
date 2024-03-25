package pl.akademiaspecjalistowit.jokeapp.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.akademiaspecjalistowit.jokeapp.data.Joke;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class JokeApiProvider implements JokeProvider {

    private JokeDto jokeDto;

    HttpClient httpClient = HttpClient.newHttpClient();

    @Override
    public Joke getJoke() {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://v2.jokeapi.dev/joke/Any"))
                .build();
        return getResponse(request);
    }

    @Override
    public Joke getJokeByCategory(String category) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://v2.jokeapi.dev/joke/" + category))
                .build();
        return getResponse(request);
    }

    private Joke getResponse(HttpRequest request) {
        try {
            HttpResponse<String> response = httpClient.send(
                    request, HttpResponse.BodyHandlers.ofString());
            
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return JokeDtoMapper.toJoke(objectMapper.readValue(response.body(), JokeDto.class));
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<String> showAvailableCategories() {
        return List.of("Misc", "Programming", "Dark", "Pun", "Spooky", "Christmas");
    }
}
