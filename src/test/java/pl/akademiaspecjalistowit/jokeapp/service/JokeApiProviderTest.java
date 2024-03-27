package pl.akademiaspecjalistowit.jokeapp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import pl.akademiaspecjalistowit.jokeapp.data.Joke;

import java.net.http.HttpClient;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JokeApiProviderTest {

    @Test
    void should_return_joke_on_get_joke_call() {
        //given
        ObjectMapper objectMapper = new ObjectMapper();
        JokeProvider jokeApiProvider = new JokeApiProvider(HttpClient.newHttpClient(), objectMapper);

        //when
        Joke joke = jokeApiProvider.getJoke();

        //then
        assertNotNull(joke);
    }

    @Test
    void should_return_joke_by_category_on_get_joke_by_category_call() {
        //given
        ObjectMapper objectMapper = new ObjectMapper();
        JokeProvider jokeApiProvider = new JokeApiProvider(HttpClient.newHttpClient(), objectMapper);

        //when
        Joke joke = jokeApiProvider.getJokeByCategory("Misc");

        //then
        assertNotNull(joke);
    }

    @Test
    void should_return_available_joke_categories_on_get_joke_by_category_call() {
        //given
        ObjectMapper objectMapper = new ObjectMapper();
        JokeProvider jokeApiProvider = new JokeApiProvider(HttpClient.newHttpClient(), objectMapper);

        //when
        List<String> categories = jokeApiProvider.getAvailableJokeCategories();

        //then
        assertNotNull(categories);
    }
}