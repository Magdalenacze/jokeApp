package pl.akademiaspecjalistowit.jokeapp.service;

import pl.akademiaspecjalistowit.jokeapp.data.Joke;

import java.util.List;

public interface JokeProvider {

    Joke getJoke();

    Joke getJokeByCategory(String category);

    List<String> getAvailableJokeCategories();
}
