package pl.akademiaspecjalistowit.jokeapp.service;

import pl.akademiaspecjalistowit.jokeapp.data.Joke;

import java.util.List;

public interface JokeService {

    Joke getJoke();

    Joke getJoke(String category);

    List<String> getAvailableJokeCategories();

    void incrementCounter();
}
