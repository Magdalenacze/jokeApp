package pl.akademiaspecjalistowit.jokeapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.akademiaspecjalistowit.jokeapp.data.FileJokeRepository;
import pl.akademiaspecjalistowit.jokeapp.data.InMemoryJokeRepository;
import pl.akademiaspecjalistowit.jokeapp.data.JokeRepository;
import pl.akademiaspecjalistowit.jokeapp.service.*;
import pl.akademiaspecjalistowit.jokeapp.view.JokeView;

import java.net.http.HttpClient;
import java.util.List;

public class JokeAppApplication {

    public static void main(String[] args) {
        JokeService jokeService = initiateApplicationContext();
        JokeView jokeView = new JokeView(jokeService);
        jokeView.run();
    }

    private static JokeService initiateApplicationContext() {
        List<JokeRepository> jokeRepositories = List.of(
                new InMemoryJokeRepository(),
                new FileJokeRepository());
        List<JokeProvider> jokeProviders = List.of(
                new JokeApiProvider(HttpClient.newHttpClient(), new ObjectMapper()),
                new JokeDataProvider(jokeRepositories));
        JokeService jokeService = new JokeServiceImpl(jokeProviders);
        return jokeService;
    }
}
